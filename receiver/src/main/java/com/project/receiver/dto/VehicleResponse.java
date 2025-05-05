package com.project.receiver.dto;

import lombok.Data;

import java.util.Map;

@Data
public class VehicleResponse {
    private Map<String, HistoricalStateDetails> historicalData;
    private Map<String, InstantaneousStateDetails> instantaneousData;
}
