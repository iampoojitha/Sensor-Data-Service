package com.project.receiver.service;

import com.project.common.config.AppConfig;
import com.project.common.constant.HistoricalSensors;
import com.project.common.constant.InstantaneousSensors;
import com.project.common.dto.SensorDataDto;
import com.project.receiver.dto.HistoricalStateDetails;
import com.project.receiver.dto.InstantaneousStateDetails;
import com.project.receiver.dto.VehicleResponse;
import com.project.receiver.mapper.ResponseMapper;
import com.project.receiver.model.HistoricalData;
import com.project.receiver.model.InstantaneousData;
import com.project.receiver.model.SensorData;
import com.project.receiver.repository.HistoricalDataRepo;
import com.project.receiver.repository.InstantaneousDataRepo;
import com.project.receiver.repository.SensorDataRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SensorDataReceiver {
    private final ResponseMapper responseMapper;
    private final SensorDataRepo sensorDataRepo;
    private final InstantaneousDataRepo instantaneousRepo;
    private final HistoricalDataRepo historicalDataRepo;
    private final NotificationService notificationService;

    private static final List<HistoricalSensors> historicalSensors = List.of(HistoricalSensors.values());
    private static final List<InstantaneousSensors> instantaneousSensors = List.of(InstantaneousSensors.values());

    @KafkaListener(topics = AppConfig.TOPIC, groupId = AppConfig.GROUP_ID)
    public void receiveData(SensorDataDto dataDto) throws IOException {
        SensorData oldData = sensorDataRepo.findByVinIdAndSensor(dataDto.getVinId(), dataDto.getSensor());

        try {
            if (oldData != null) {
                responseMapper.updateFromDto(dataDto, oldData);
                sensorDataRepo.save(oldData);
            } else {
                SensorData newData = responseMapper.mapToSensorData(dataDto);
                sensorDataRepo.save(newData);
            }
            processHistoricalData(dataDto);
            processInstantaneousData(dataDto);
        } catch(OptimisticLockingFailureException e) {
            throw new OptimisticLockingFailureException("Optimistic Locking Failure. Please try again.");
        }
    }

    public void processHistoricalData(SensorDataDto data) throws IOException {
        if (historicalSensors.stream().anyMatch(sensor -> sensor.name().equals(data.getSensor()))) {
            try {
                var oldData = historicalDataRepo.findByVinId(data.getVinId());
                if (oldData != null) {
                    Map<String, HistoricalStateDetails> stateDetails = oldData.getHistoricalData();
                    var matchingState = stateDetails.get(data.getSensor());
                    if (matchingState != null && !matchingState.equals(responseMapper.mapToHistoricalStateDetails(data))) {
                        sendNotification("Hey user your current %s is %s".formatted(data.getSensor(), data.getReading()));
                        responseMapper.updateHistoricalStateDetailsFromDto(data, matchingState);
                    } else if (matchingState == null && !stateDetails.containsKey(data.getSensor())) {
                        HistoricalStateDetails newState = responseMapper.mapToHistoricalStateDetails(data);
                        stateDetails.put(data.getSensor(), newState);
                    }
                    historicalDataRepo.save(oldData);
                } else {
                    HistoricalData newData = new HistoricalData();
                    newData.setVinId(data.getVinId());

                    HistoricalStateDetails newState = responseMapper.mapToHistoricalStateDetails(data);
                    Map<String, HistoricalStateDetails> stateDetails = Map.of(data.getSensor(), newState);
                    newData.setHistoricalData(stateDetails);
                    historicalDataRepo.save(newData);
                }
            } catch (OptimisticLockingFailureException e) {
                throw new OptimisticLockingFailureException("Optimistic Locking Failure. Please try again.");
            }
        }
    }

    public void processInstantaneousData(SensorDataDto data) throws IOException {
        if (instantaneousSensors.stream().anyMatch(sensor -> sensor.name().equals(data.getSensor()))) {
            var oldData = instantaneousRepo.findByVinId(data.getVinId());
            if (oldData != null) {
                Map<String, InstantaneousStateDetails> stateDetails = oldData.getInstantaneousData();
                InstantaneousStateDetails matchingState = stateDetails.get(data.getSensor());
                if (matchingState != null && !matchingState.equals(responseMapper.mapToInstantaneousStateDetails(data))) {
                    sendNotification("Hey user your current %s is %s".formatted(data.getSensor(), data.getReading()));
                    responseMapper.updateInstantaneousStateDetailsFromDto(data, matchingState);
                } else if(matchingState == null && !stateDetails.containsKey(data.getSensor())) {
                    InstantaneousStateDetails newState = responseMapper.mapToInstantaneousStateDetails(data);
                    stateDetails.put(data.getSensor(), newState);
                }
                instantaneousRepo.save(oldData);
            } else{
                InstantaneousData newData = new InstantaneousData();
                newData.setVinId(data.getVinId());

                InstantaneousStateDetails newState = responseMapper.mapToInstantaneousStateDetails(data);
                Map<String, InstantaneousStateDetails> stateDetails = Map.of(data.getSensor(), newState);
                newData.setInstantaneousData(stateDetails);
                instantaneousRepo.save(newData);
            }
        }
    }

    public VehicleResponse getVehicleDataByVinId(String vinId) throws IOException {
        VehicleResponse response = new VehicleResponse();
        var historicalData = historicalDataRepo.findByVinId(vinId);
        var instantaneousData = instantaneousRepo.findByVinId(vinId);
        response.setHistoricalData(historicalData!=null ? historicalData.getHistoricalData() : Map.of() );
        response.setInstantaneousData(instantaneousData!=null ? instantaneousData.getInstantaneousData() : Map.of() );
        return response;
    }

    public void sendNotification(String message) {
        if (message != null) {
            notificationService.sendSMS(message);
        }
    }
}
