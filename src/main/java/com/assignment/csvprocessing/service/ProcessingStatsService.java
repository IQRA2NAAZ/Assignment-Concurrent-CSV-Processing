package com.assignment.csvprocessing.service;

import com.assignment.csvprocessing.dto.EnumResponse;
import com.assignment.csvprocessing.dto.FileStatusResponse;
import com.assignment.csvprocessing.dto.StatsResponse;
import com.assignment.csvprocessing.entity.FileProcessing;
import com.assignment.csvprocessing.repository.FileStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessingStatsService {

    private final AtomicLong totalFilesProcessed = new AtomicLong(0);
    private final AtomicLong totalRecordsProcessed = new AtomicLong(0);

    private final FileStatusRepository fileStatusRepository;

    public void incrementFilesProcessed() {
        totalFilesProcessed.incrementAndGet();
    }

    public void incrementRecordsProcessed() {
        totalRecordsProcessed.incrementAndGet();
    }

    public StatsResponse getStats() {
        return new StatsResponse(
                totalFilesProcessed.get(),
                totalRecordsProcessed.get()
        );
    }

    public List<FileStatusResponse> getFileStatuses() {

        List<FileProcessing> statuses = fileStatusRepository.findAll();

        return statuses.stream()
                .map(status -> new FileStatusResponse(
                        status.getFileName(),
                        new EnumResponse(
                                status.getStatus().name(),
                                status.getStatus().getLabel()
                        )
                ))
                .collect(Collectors.toList());
    }
}