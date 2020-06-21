package com.metalheart.showcase.common.model;

import com.metalheart.model.SampleEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SuccessEvent implements SampleEvent {

    public static String EVENT_TYPE = "success";

    private String type = EVENT_TYPE;
    private String payload;

    public SuccessEvent(String payload) {
        this.payload = payload;
    }
}
