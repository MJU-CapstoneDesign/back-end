package com.danram.server.service.fcm;

import com.danram.server.dto.FcmMessage;
import com.danram.server.service.member.MemberService;
import com.danram.server.util.JwtUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FCMServiceImpl implements FCMService {

    private final String API_URL;
    private final ObjectMapper objectMapper;
    private final String fcmConfigPath;
    private final MemberService memberService;

    @Autowired
    public FCMServiceImpl(ObjectMapper objectMapper, @Value("${fcm.url}") String API_URL, @Value("${fcm.config}") String fcmConfigPath, MemberService memberService) {
        this.API_URL = API_URL;
        this.objectMapper = objectMapper;
        this.fcmConfigPath = fcmConfigPath;
        this.memberService = memberService;
    }

    @Override
    public void sendMessageTo(String topic, String title, String body) throws IOException {
        String message = makeMessage(topic, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    @Override
    public void sendAlarm(final Long partyId) throws IOException {
        System.out.println("party id: " + partyId.toString());

        sendMessageTo(
                partyId.toString(),
                partyId + "번 방 알람",
                memberService.getInfo(JwtUtil.getAccessToken()).getName() + "이 알람을 껐음");
    }

    private String makeMessage(String topic, String title, String body) throws JsonParseException, JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                    .topic(topic)
                    .notification(FcmMessage.Notification.builder()
                            .title(title)
                            .body(body)
                            .image(null)
                            .build()
                    ).build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = fcmConfigPath;

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}