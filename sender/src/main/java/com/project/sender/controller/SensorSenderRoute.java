package com.project.sender.controller;

import com.project.common.config.AppConfig;
import com.project.common.dto.SensorDataDto;
import com.project.sender.service.SensorDataSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConfig.API_SEND)
@RequiredArgsConstructor
@Slf4j
public class SensorSenderRoute {
    private final SensorDataSenderService sensorDataSenderService;

    @PostMapping(AppConfig.API_SEND_DATA)
    public void sendSensorData(@RequestBody SensorDataDto sensorDataRequest) {
        log.info("Data sent to kafka");
        sensorDataSenderService.sendData(sensorDataRequest);
    }
}
