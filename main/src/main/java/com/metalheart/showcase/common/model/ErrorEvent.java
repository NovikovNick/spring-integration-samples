package com.metalheart.showcase.common.model;

import com.metalheart.model.SampleEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorEvent implements SampleEvent {

    public static String EVENT_TYPE = "error";

    private String type = EVENT_TYPE;
    private String payload;

    public ErrorEvent(String payload) {
        this.payload = payload;
    }
}
