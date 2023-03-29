package com.danram.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoogleAuthResponseDto {
    private String access_token;
    private String refresh_token;
    private String expires_in;
    private String scope;
    private String token_type;
    private String id_token;

    @Override
    public String toString() {
        return new String("access_token: " + access_token + "\nrefresh_token: " + refresh_token + "\nexpires_in: " + expires_in + "\nscope: " + scope +
                "\ntoken_type: " + token_type + "\nid_token: " + id_token);
    }
}
