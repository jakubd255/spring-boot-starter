package com.example.springbootstarter.service;

import com.example.springbootstarter.util.csv.UserCsvExporter;
import com.example.springbootstarter.dto.DtoConverter;
import com.example.springbootstarter.dto.response.UserDto;
import com.example.springbootstarter.dto.response.UserExtendedDto;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(DtoConverter::convertUserToDto)
                .toList();
    }

    public List<UserExtendedDto> findAllExtended() {
        return userRepository
                .findAll()
                .stream()
                .map(DtoConverter::convertUserToExtendedDto)
                .toList();
    }

    public void exportToCsv(HttpServletResponse response) throws IOException {
        List<User> users = userRepository.findAll();

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.csv");

        UserCsvExporter usersToCsv = new UserCsvExporter(response.getWriter());
        usersToCsv.generateCsv(users);
    }

    public UserDto findById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        return DtoConverter.convertUserToDto(user);
    }
}
