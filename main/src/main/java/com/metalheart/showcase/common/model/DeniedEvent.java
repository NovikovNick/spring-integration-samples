package com.metalheart.showcase.common.model;

import com.metalheart.model.SampleEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeniedEvent implements SampleEvent {

    public static String EVENT_TYPE = "denied";

    private String type = EVENT_TYPE;
    private String payload;

    public DeniedEvent(String payload) {
        this.payload = payload;
    }
}
