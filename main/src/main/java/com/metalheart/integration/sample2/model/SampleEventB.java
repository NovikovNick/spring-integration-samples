package com.metalheart.integration.sample2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleEventB implements SampleEvent {

    public static String EVENT_TYPE = "type_B";

    private String type = EVENT_TYPE;
    private Integer payload;
}
