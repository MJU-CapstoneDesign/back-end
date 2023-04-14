package com.danram.server.service.fcm;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FCMService {
    void sendMessageTo(String topic, String title, String body) throws IOException;
    void sendAlarm(Long partyId)throws IOException;
}
