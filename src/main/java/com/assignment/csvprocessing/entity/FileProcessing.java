package com.assignment.csvprocessing.entity;

import com.assignment.csvprocessing.enums.FileProcessingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "file_processing_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileProcessing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Enumerated(EnumType.STRING)
    private FileProcessingStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}