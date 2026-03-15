package com.assignment.csvprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsResponse {

    private long totalFilesProcessed;
    private long totalRecordsProcessed;
}