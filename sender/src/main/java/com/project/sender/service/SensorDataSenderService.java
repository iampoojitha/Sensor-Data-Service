package com.project.sender.service;

import com.project.common.config.AppConfig;
import com.project.common.dto.SensorDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorDataSenderService {
    @Autowired
    private KafkaTemplate<String, SensorDataDto> kafkaTemplate;

    public void sendData(SensorDataDto data) {
        kafkaTemplate.send(AppConfig.TOPIC, data);
    }
}
