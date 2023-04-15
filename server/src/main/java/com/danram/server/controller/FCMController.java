package com.danram.server.controller;

import com.danram.server.dto.FCMDto;
import com.danram.server.service.fcm.FCMService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FCMController {
    private final FCMService fcmService;

    @PostMapping("/alarm")
    @ApiOperation("fcm 예제 api")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity pushAlarm(@RequestBody FCMDto fcmDto) throws IOException {
        System.out.println(fcmDto.getTopic() + " "
                + fcmDto.getTitle() + " " + fcmDto.getBody());

        fcmService.sendMessageTo(
                fcmDto.getTopic(),
                fcmDto.getTitle(),
                fcmDto.getBody());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/alarm/{partyId}")
    @ApiOperation("fcm 알람 api")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity pushAlarm(@PathVariable(name = "partyId") Long partyId) throws IOException {
        fcmService.sendAlarm(partyId);

        return ResponseEntity.ok().build();
    }
}
