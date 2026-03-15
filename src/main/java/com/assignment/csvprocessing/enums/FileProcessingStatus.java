package com.assignment.csvprocessing.enums;

import lombok.Getter;

@Getter
public enum FileProcessingStatus {

    UPLOADED("Uploaded"),
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    FAILED("Failed");

    private final String label;

    FileProcessingStatus(String label) {
        this.label = label;
    }

}
