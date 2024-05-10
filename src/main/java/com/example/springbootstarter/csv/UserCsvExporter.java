package com.example.springbootstarter.csv;

import com.example.springbootstarter.model.User;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class UserCsvExporter {
    private final CSVWriter csvWriter;

    private final String[] headerFields = {"Id", "fullName", "email", "role", "isAccountNonExpired", "isAccountNonLocked", "isCredentialsNonExpired", "isEnabled"};

    public UserCsvExporter(Writer writer) {
        csvWriter = new CSVWriter(writer);
    }

    public void generateCsv(List<User> users) throws IOException {
        csvWriter.writeNext(headerFields);

        for(User user : users) {
            csvWriter.writeNext(new String[] {
                    Long.toString(user.getId()),
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole().getAuthority(),
                    Boolean.toString(user.isAccountNonExpired()),
                    Boolean.toString(user.isAccountNonLocked()),
                    Boolean.toString(user.isCredentialsNonExpired()),
                    Boolean.toString(user.isEnabled())
            });
        }

        csvWriter.close();
    }
}
