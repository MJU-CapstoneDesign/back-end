package com.danram.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FCMDto {
    private String topic;
    private String title;
    private String body;
}