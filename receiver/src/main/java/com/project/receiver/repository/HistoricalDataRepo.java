package com.project.receiver.repository;

import com.project.receiver.model.HistoricalData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalDataRepo extends MongoRepository<HistoricalData, String> {
    HistoricalData findByVinId(String vinId);
}
