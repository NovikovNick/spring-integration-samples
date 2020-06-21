package com.metalheart.showcase.common.model;

import com.metalheart.model.SampleEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultEvent implements SampleEvent {

    public static String EVENT_TYPE = "result";

    private String type = EVENT_TYPE;
    private Integer data;

    public ResultEvent(Integer data) {
        this.data = data;
    }
}
