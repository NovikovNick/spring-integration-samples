package com.metalheart.showcase.common.model;

import com.metalheart.model.SampleEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AcceptedEvent implements SampleEvent {

    public static String EVENT_TYPE = "accepted";

    private String type = EVENT_TYPE;
    private Integer data;

    public AcceptedEvent(Integer data) {
        this.data = data;
    }
}
