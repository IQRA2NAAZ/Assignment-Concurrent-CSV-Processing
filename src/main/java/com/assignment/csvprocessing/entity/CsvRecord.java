package com.assignment.csvprocessing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "csv_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CsvRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "csv_id")
    private Long csvId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Double amount;
}