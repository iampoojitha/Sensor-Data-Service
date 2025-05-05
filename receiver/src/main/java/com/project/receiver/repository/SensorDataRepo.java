package com.project.receiver.repository;

import com.project.receiver.model.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepo extends MongoRepository<SensorData, String> {
    SensorData findByVinIdAndSensor(String vinId, String sensor);
}
