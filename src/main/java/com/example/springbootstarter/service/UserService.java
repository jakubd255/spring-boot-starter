package com.example.springbootstarter.service;

import com.example.springbootstarter.csv.UserCsvExporter;
import com.example.springbootstarter.model.User;
import com.example.springbootstarter.repository.UserRepository;
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

    public void exportToCsv(HttpServletResponse response) throws IOException {
        List<User> users = userRepository.findAll();
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.csv");

        UserCsvExporter usersToCsv = new UserCsvExporter(response.getWriter());
        usersToCsv.generateCsv(users);
    }
}
