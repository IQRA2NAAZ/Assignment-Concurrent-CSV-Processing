package com.assignment.csvprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResponse {

    private String message;
    private int filesReceived;
}