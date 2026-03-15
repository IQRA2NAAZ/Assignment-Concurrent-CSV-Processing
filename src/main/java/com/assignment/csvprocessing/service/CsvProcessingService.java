package com.assignment.csvprocessing.service;

import com.assignment.csvprocessing.dto.UploadResponse;
import com.assignment.csvprocessing.entity.FileProcessing;
import com.assignment.csvprocessing.enums.FileProcessingStatus;
import com.assignment.csvprocessing.repository.FileStatusRepository;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvProcessingService {

    private final RecordProcessingService recordProcessingService;
    private final ProcessingStatsService statsService;
    private final FileStatusRepository fileStatusRepository;

    @Qualifier("fileExecutor")
    private final Executor fileExecutor;

    public UploadResponse processFiles(MultipartFile[] files) {

        if (files == null || files.length == 0) {
            throw new RuntimeException("No files provided for upload");
        }

        log.info("Received {} files for processing", files.length);

        for (MultipartFile file : files) {

            try {

                String fileName = file.getOriginalFilename() != null
                        ? file.getOriginalFilename()
                        : "unknown-file";

                log.info("Submitting file {} for processing", fileName);

                byte[] fileBytes = file.getBytes();

                fileExecutor.execute(() ->
                        processFile(fileBytes, fileName)
                );

            } catch (Exception e) {
                log.error("Error reading uploaded file {}", file.getOriginalFilename(), e);
            }
        }

        return new UploadResponse(
                "Files submitted for processing",
                files.length
        );
    }

    public void processFile(byte[] fileBytes, String fileName) {

        try {

            FileProcessing status = FileProcessing.builder()
                    .fileName(fileName)
                    .status(FileProcessingStatus.PROCESSING)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            fileStatusRepository.save(status);

            log.info(
                    "File {} processing started on {}",
                    fileName,
                    Thread.currentThread().getName()
            );

            try (CSVReader reader = new CSVReader(
                    new InputStreamReader(new ByteArrayInputStream(fileBytes)))) {

                String[] header = reader.readNext();

                if (header == null || header.length < 4) {
                    throw new RuntimeException("Invalid CSV header for file: " + fileName);
                }

                String[] line;

                while ((line = reader.readNext()) != null) {

                    if (line.length < 4) {
                        log.warn("Skipping malformed row in file {}", fileName);
                        continue;
                    }

                    Long csvId = Long.parseLong(line[0]);
                    String name = line[1];
                    String email = line[2];
                    Double amount = Double.parseDouble(line[3]);

                    recordProcessingService.processRecord(
                            csvId,
                            name,
                            email,
                            amount
                    );
                }
            }

            status.setStatus(FileProcessingStatus.COMPLETED);
            status.setUpdatedAt(LocalDateTime.now());

            fileStatusRepository.save(status);

            statsService.incrementFilesProcessed();

            log.info(
                    "File {} processed by {}",
                    fileName,
                    Thread.currentThread().getName()
            );

        } catch (Exception e) {

            log.error("Error processing file {}", fileName, e);

            FileProcessing failed = fileStatusRepository
                    .findByFileName(fileName)
                    .orElseThrow();

            failed.setStatus(FileProcessingStatus.FAILED);
            failed.setUpdatedAt(LocalDateTime.now());

            fileStatusRepository.save(failed);
        }
    }
}