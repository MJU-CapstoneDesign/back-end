package com.danram.server.service.oauth;

import com.danram.server.dto.response.GoogleAuthResponseDto;
import com.danram.server.oauth.SocialLoginType;
import com.danram.server.oauth.SocialOauth;
import org.springframework.stereotype.Service;

@Service
public interface GoogleOauthService {
    public void request(SocialLoginType socialLoginType);
    public GoogleAuthResponseDto requestAccessToken(SocialLoginType socialLoginType, String code);
}
