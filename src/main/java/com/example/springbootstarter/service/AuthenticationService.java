package com.example.springbootstarter.service;

import com.example.springbootstarter.dto.DtoConverter;
import com.example.springbootstarter.dto.request.*;
import com.example.springbootstarter.dto.response.UserDto;
import com.example.springbootstarter.exception.EmailAlreadyExistsException;
import com.example.springbootstarter.exception.EmailNotFoundException;
import com.example.springbootstarter.exception.EmailUnverifiedException;
import com.example.springbootstarter.exception.InvalidPasswordException;
import com.example.springbootstarter.factory.TokenFactory;
import com.example.springbootstarter.model.*;
import com.example.springbootstarter.repository.SessionRepository;
import com.example.springbootstarter.repository.TokenRepository;
import com.example.springbootstarter.util.EmailSender;
import com.example.springbootstarter.util.TokenCodeGenerator;
import com.example.springbootstarter.util.UserAuthenticationManager;
import com.example.springbootstarter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthenticationManager authenticationManager;
    private final EmailSender emailSender;

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public void register(RegisterRequest request) {
        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());
        User user;

        if(existingUserOpt.isEmpty()) {
            user = User.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ROLE_USER)
                    .build();
            user = userRepository.save(user);
        }
        else {
            user = existingUserOpt.get();
            if(user.isVerified()) {
                throw new EmailAlreadyExistsException("This email is taken");
            }

            user.setFullName(request.getFullName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setActive(true);
            userRepository.save(user);
        }

        tokenRepository.deleteAllByUserId(user.getId());
        Token token = tokenRepository.save(TokenFactory.createEmailToken(user));
        emailSender.sendVerificationEmail(user.getEmail(), token.getCode());
    }

    public String logIn(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Invalid email"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        else if(!user.isVerified()) {
            Token token = tokenRepository.save(TokenFactory.createEmailToken(user));
            emailSender.sendVerificationEmail(user.getEmail(), token.getCode());
            throw new EmailUnverifiedException("Email not verified");
        }

        Session session = Session.builder()
                .user(user)
                .build();
        return sessionRepository.save(session).getId().toString();
    }

    public UserDto authenticate() {
        return DtoConverter.convertUserToDto(authenticationManager.getAuthenticatedUser());
    }

    public String verifyEmail(String code) {
        Token token = tokenRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Invalid token"));

        if(token.getExpiresAt().before(new Date())) {
            throw new BadCredentialsException("Token expired");
        }
        if(token.getType() != TokenType.EMAIL_VERIFICATION) {
            throw new BadCredentialsException("Invalid token type");
        }

        User user = token.getUser();

        if(!user.isVerified()) {
            user.setVerified(true);
        }
        else if(user.getPendingEmail() != null) {
            user.setEmail(user.getPendingEmail());
            user.setPendingEmail(null);
        }

        userRepository.save(user);
        tokenRepository.delete(token);

        Session session = Session.builder()
                .user(user)
                .build();
        return sessionRepository.save(session).getId().toString();
    }

    @Transactional
    public void updateEmail(EmailRequest request) {
        User authUser = authenticationManager.getAuthenticatedUser();
        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());

        if(existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            //Restore old email or no change
            if(existingUser.getId().equals(authUser.getId())) {
                authUser.setPendingEmail(null);
                userRepository.save(authUser);
                tokenRepository.deleteAllByUserId(authUser.getId());
                return;
            }

            // Email is taken by verified user
            if(existingUser.isVerified()) {
                throw new EmailAlreadyExistsException("This email is taken");
            }

            //Delete unverified user
            tokenRepository.deleteAllByUserId(existingUser.getId());
            userRepository.delete(existingUser);
        }

        //Set pendingEmail and send token
        authUser.setPendingEmail(request.getEmail());
        userRepository.save(authUser);

        tokenRepository.deleteAllByUserId(authUser.getId());
        Token token = tokenRepository.save(TokenFactory.createEmailToken(authUser));
        emailSender.sendVerificationEmail(request.getEmail(), token.getCode());
    }

    @Transactional
    public void forgotPassword(EmailRequest request) {
        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());
        if(existingUserOpt.isEmpty()) {
           return;
        }
        User user = existingUserOpt.get();

        tokenRepository.deleteAllByUserId(user.getId());
        sessionRepository.deleteSessionsByUserId(user.getId());

        Token token = tokenRepository.save(Token.builder()
                .user(user)
                .code(TokenCodeGenerator.generateTokenCode())
                .type(TokenType.RESET_PASSWORD)
                .build());
        emailSender.sendResetPassword(user.getEmail(), token.getCode());
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        Token token = tokenRepository.findByCode(request.getToken())
                .orElseThrow(() -> new EntityNotFoundException("Invalid token"));

        if(token.getExpiresAt().before(new Date())) {
            throw new BadCredentialsException("Token expired");
        }
        if(token.getType() != TokenType.RESET_PASSWORD) {
            throw new BadCredentialsException("Invalid token type");
        }

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        tokenRepository.delete(token);
    }

    public void updatePassword(UpdatePasswordRequest request) {
        User authUser = authenticationManager.getAuthenticatedUser();

        if(!passwordEncoder.matches(request.getCurrentPassword(), authUser.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        authUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(authUser);
    }
}
