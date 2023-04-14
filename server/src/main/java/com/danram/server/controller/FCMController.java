package com.danram.server.controller;

import com.danram.server.dto.FCMDto;
import com.danram.server.service.fcm.FCMService;
import com.danram.server.service.fcm.FCMServiceImpl;
import com.danram.server.service.member.MemberService;
import com.danram.server.util.JwtUtil;
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
    public ResponseEntity pushAlarm(@PathVariable(name = "partyId") Long partyId) throws IOException {
        fcmService.sendAlarm(partyId);

        return ResponseEntity.ok().build();
    }
}
