package com.example.employeemanagement.dto;

import com.example.employeemanagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthPayload {
    private String token;
    private User user;
}
