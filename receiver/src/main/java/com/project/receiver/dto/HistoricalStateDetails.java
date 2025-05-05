package com.project.receiver.dto;

import com.project.common.constant.HistoricalSensors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalStateDetails {
    private HistoricalSensors sensor;
    private String reading;
    private String timestamp;

}
