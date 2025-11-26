package com.example.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortInput {
    private String field = "id";
    private SortDirection direction = SortDirection.ASC;
    
    public enum SortDirection {
        ASC, DESC
    }
}
