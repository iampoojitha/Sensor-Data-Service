package com.project.receiver.model;

import com.project.receiver.dto.HistoricalStateDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Data
@Document(collection = "historical_data")
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(name = "vinId_sensorName_idx", def = "{'vinId': 1, 'sensorName': 1}", unique = true)
public class HistoricalData {
    @Id
    private String id;
    private String vinId;
    private Map<String, HistoricalStateDetails> historicalData;

    @Indexed(expireAfter = "30d")

    @CreatedDate
    private Date createdDate;


    @Version
    private Long version;
}
