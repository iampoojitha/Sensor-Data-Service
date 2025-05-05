package com.project.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class SensorDataDto {
    private String vinId;
    private String sensor;
    private String reading;
    private String timestamp;
}
