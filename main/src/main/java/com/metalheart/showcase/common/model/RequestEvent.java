package com.metalheart.showcase.common.model;

import com.metalheart.model.SampleEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestEvent implements SampleEvent {

    public static String EVENT_TYPE = "request";

    private String type = EVENT_TYPE;
    private Integer data;

    public RequestEvent(Integer data) {
        this.data = data;
    }
}
