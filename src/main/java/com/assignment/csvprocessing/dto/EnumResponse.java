package com.assignment.csvprocessing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnumResponse {


    @JsonProperty("enum")
    private String enumName;

    @JsonProperty("label")
    private String enumLabel;

}
