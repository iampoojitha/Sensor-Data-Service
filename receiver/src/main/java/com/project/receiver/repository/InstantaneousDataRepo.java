package com.project.receiver.repository;

import com.project.receiver.model.InstantaneousData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstantaneousDataRepo extends MongoRepository<InstantaneousData, String> {
    InstantaneousData findByVinId(String vinId);
}
