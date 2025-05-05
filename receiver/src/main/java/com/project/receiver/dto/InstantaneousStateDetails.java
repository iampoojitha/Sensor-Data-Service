package com.project.receiver.dto;

import com.project.common.constant.InstantaneousSensors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstantaneousStateDetails {
    private InstantaneousSensors sensor;
    private String reading;
    private String timestamp;
}
