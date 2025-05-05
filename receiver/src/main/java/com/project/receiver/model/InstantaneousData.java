package com.project.receiver.model;

import com.project.receiver.dto.InstantaneousStateDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "instantaneous_data")
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(name = "vinId_sensorName_idx", def = "{'vinId': 1, 'sensorName': 1}", unique = true)
public class InstantaneousData {
    @Id
    private String id;
    private String vinId;
    private Map<String, InstantaneousStateDetails> instantaneousData;

    @Version
    private Long version;
}
