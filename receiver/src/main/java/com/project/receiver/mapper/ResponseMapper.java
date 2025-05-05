package com.project.receiver.mapper;

import com.project.common.dto.SensorDataDto;
import com.project.receiver.dto.HistoricalStateDetails;
import com.project.receiver.dto.InstantaneousStateDetails;
import com.project.receiver.model.SensorData;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ResponseMapper {
    SensorData mapToSensorData(SensorDataDto data);

    void updateFromDto(SensorDataDto dto, @MappingTarget SensorData data);

    void updateHistoricalStateDetailsFromDto(SensorDataDto dto, @MappingTarget HistoricalStateDetails data);

    void updateInstantaneousStateDetailsFromDto(SensorDataDto dto, @MappingTarget InstantaneousStateDetails data);

    HistoricalStateDetails mapToHistoricalStateDetails(SensorDataDto dto);

    InstantaneousStateDetails mapToInstantaneousStateDetails(SensorDataDto dto);
}
