package com.assignment.csvprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileStatusResponse {

    private String fileName;
    private EnumResponse status;
}