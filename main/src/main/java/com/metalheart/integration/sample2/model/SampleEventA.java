package com.metalheart.integration.sample2.model;

import com.metalheart.model.SampleEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleEventA implements SampleEvent {

    public static String EVENT_TYPE = "type_A";

    private String type = EVENT_TYPE;
    private String payload;
}
