package com.project.receiver.controller;

import com.project.common.config.AppConfig;
import com.project.receiver.dto.VehicleResponse;
import com.project.receiver.service.SensorDataReceiver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(AppConfig.API_RECEIVE)
public class ReceiverRoute {

    private final SensorDataReceiver sensorDataReceiver;

    public ReceiverRoute(SensorDataReceiver sensorDataReceiver) {
        this.sensorDataReceiver = sensorDataReceiver;
    }

    @GetMapping(AppConfig.GET_DATA)
    public VehicleResponse getSensorDataById(@PathVariable String id) throws IOException {
        return sensorDataReceiver.getVehicleDataByVinId(id);
    }
}
