package com.assignment.csvprocessing.controller;

import com.assignment.csvprocessing.dto.FileStatusResponse;
import com.assignment.csvprocessing.dto.StatsResponse;
import com.assignment.csvprocessing.dto.UploadResponse;
import com.assignment.csvprocessing.service.CsvProcessingService;
import com.assignment.csvprocessing.service.ProcessingStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/csv")
public class CsvUploadController {

    private final CsvProcessingService csvProcessingService;
    private final ProcessingStatsService statsService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UploadResponse> uploadCsvFiles(
            @RequestParam("files") MultipartFile[] files) {

        UploadResponse response = csvProcessingService.processFiles(files);

        return ResponseEntity.accepted().body(response);
    }

    @GetMapping(
            value = "/stats",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<StatsResponse> getStats() {

        return ResponseEntity.ok(
                statsService.getStats()
        );
    }

    @GetMapping(
            value = "/files/status",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<FileStatusResponse>> getFileStatus() {

        return ResponseEntity.ok(
                statsService.getFileStatuses()
        );
    }
}