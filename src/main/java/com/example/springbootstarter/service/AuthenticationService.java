package com.example.springbootstarter.service;

import com.example.springbootstarter.dto.request.*;
import com.example.springbootstarter.exception.EmailAlreadyExistsException;
import com.example.springbootstarter.exception.EmailUnverifiedException;
import com.example.springbootstarter.exception.InvalidPasswordException;
import com.example.springbootstarter.factory.UserFactory;
import com.example.springbootstarter.model.*;
import com.example.springbootstarter.util.EmailSender;
import com.example.springbootstarter.util.auth.UserAuthenticationManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final TokenService tokenService;
    private final SessionService sessionService;

    private final PasswordEncoder passwordEncoder;
    private final UserAuthenticationManager authenticationManager;
    private final UserFactory userFactory;
    private final EmailSender emailSender;

    public UserDetailsService userDetailsService() {
        return userService::getByEmail;
    }

    @Transactional
    public void register(RegisterRequest request) {
        Optional<User> existingOpt = userService.findByEmail(request.getEmail());
        User user;

        if(existingOpt.isEmpty()) {
            user = userService.save(userFactory.createUser(request));
        }
        else {
            user = existingOpt.get();
            if(user.isVerified()) {
                throw new EmailAlreadyExistsException("This email is taken");
            }

            user.setFullName(request.getFullName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setActive(true);
            userService.save(user);
            tokenService.deleteByUserId(user.getId());
        }

        Token token = tokenService.createEmailToken(user);

        emailSender.sendVerificationEmail(user.getEmail(), token.getCode());
    }

    public String logIn(LoginRequest request, String userAgent) {
        User user = userService.getByEmail(request.getEmail());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        else if(!user.isVerified()) {
            Token token = tokenService.createEmailToken(user);
            emailSender.sendVerificationEmail(user.getEmail(), token.getCode());
            throw new EmailUnverifiedException("Email not verified");
        }

        return sessionService.create(user, userAgent).getToken();
    }

    public Optional<String> verifyEmail(String code, String userAgent) {
        Token token = tokenService.getValidToken(code, TokenType.EMAIL_VERIFICATION);

        User user = token.getUser();
        boolean firstVerification = !user.isVerified();

        if(firstVerification) {
            user.setVerified(true);
        }
        else if(user.getPendingEmail() != null) {
            user.setEmail(user.getPendingEmail());
            user.setPendingEmail(null);
        }

        userService.save(user);
        tokenService.delete(token);

        if(firstVerification) {
            Session session = sessionService.create(user, userAgent);
            return Optional.of(session.getToken());
        }

        return Optional.empty();
    }

    public User authenticate() {
        return authenticationManager.getAuthenticatedUser();
    }

    public void logOut() {
        User user = authenticationManager.getAuthenticatedUser();
        sessionService.delete(user.getAuthSession());
    }

    @Transactional
    public void updateEmail(EmailRequest request) {
        User authUser = authenticationManager.getAuthenticatedUser();
        Optional<User> existingOpt = userService.findByEmail(request.getEmail());

        if(existingOpt.isPresent()) {
            User existingUser = existingOpt.get();

            //Restore old email or no change
            if(existingUser.getId().equals(authUser.getId())) {
                authUser.setPendingEmail(null);
                userService.save(authUser);
                tokenService.deleteByUserId(authUser.getId());
                return;
            }

            // Email is taken by verified user
            if(existingUser.isVerified()) {
                throw new EmailAlreadyExistsException("This email is taken");
            }

            //Delete unverified user
            tokenService.deleteByUserId(existingUser.getId());
            userService.delete(existingUser);
        }

        //Set pendingEmail and send token
        authUser.setPendingEmail(request.getEmail());
        userService.save(authUser);

        tokenService.deleteByUserId(authUser.getId());
        Token token = tokenService.createEmailToken(authUser);

        emailSender.sendVerificationEmail(request.getEmail(), token.getCode());
    }

    @Transactional
    public void forgotPassword(EmailRequest request) {
        Optional<User> existingUserOpt = userService.findByEmail(request.getEmail());
        if(existingUserOpt.isEmpty()) return;

        User user = existingUserOpt.get();

        tokenService.deleteByUserId(user.getId());
        sessionService.deleteByUserId(user.getId());

        Token token = tokenService.createPasswordToken(user);
        emailSender.sendResetPassword(user.getEmail(), token.getCode());
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        Token token = tokenService.getValidToken(request.getToken(), TokenType.RESET_PASSWORD);

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.save(user);

        tokenService.delete(token);
    }

    public void updatePassword(UpdatePasswordRequest request) {
        User authUser = authenticationManager.getAuthenticatedUser();

        if(!passwordEncoder.matches(request.getCurrentPassword(), authUser.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        authUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(authUser);
    }
}
