package com.danram.server.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.danram.server.dto.response.GoogleAuthResponseDto;
import lombok.RequiredArgsConstructor;
import com.danram.server.oauth.SocialLoginType;
import com.danram.server.oauth.SocialOauth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleOauthServiceImpl implements GoogleOauthService {
    private final List<SocialOauth> socialOauthList;
    private final HttpServletResponse response;
    @Autowired
    private final ObjectMapper objectMapper;

    @Override
    public void request(SocialLoginType socialLoginType) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GoogleAuthResponseDto requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String response = socialOauth.requestAccessToken(code);

        try {
            return objectMapper.readValue(response, GoogleAuthResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
}