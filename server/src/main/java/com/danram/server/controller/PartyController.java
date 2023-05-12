package com.danram.server.controller;

import com.danram.server.domain.party.Party;
import com.danram.server.domain.party.PartyInfo;
import com.danram.server.domain.party.PartyMembers;
import com.danram.server.dto.request.AlarmDto;
import com.danram.server.dto.request.MemberIdDto;
import com.danram.server.dto.request.PartyIdDto;
import com.danram.server.dto.request.PartyInfoDto;
import com.danram.server.service.firebase.FirestoreService;
import com.danram.server.service.party.PartyService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;
    private final FirestoreService firestoreService;

    @PostMapping("/create")
    @ApiModelProperty("파티 생성 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<PartyMembers> createParty(@RequestBody PartyInfoDto partyInfoDto, @RequestParam(value = "file")MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is not exist");
        }

        final String img = firestoreService.uploadFiles(file, file.getOriginalFilename());

        partyInfoDto.setPartyImg(img);

        return ResponseEntity.ok(partyService.createParty(partyInfoDto));
    }

    @GetMapping("/info")
    @ApiModelProperty("모든 파티 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 관리자 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<List<Party>> findAll() {
        return ResponseEntity.ok(partyService.findAll());
    }

    @GetMapping("/info/user/{id}")
    @ApiModelProperty("특정 인물이 속한 파티 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 관리자 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<List<Party>> findPartyByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(partyService.findPartyById(new MemberIdDto(id)));
    }

    @GetMapping("/myInfo")
    @ApiModelProperty("자신이 속한 파티 리스트 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<List<Party>> findParty() {
        return ResponseEntity.ok(partyService.findParty());
    }

    @GetMapping("/info/{partyId}")
    @ApiModelProperty("특정 파티 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 관리자 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<Party> findPartyByPartyId(@PathVariable(name = "partyId") Long id) {
        return ResponseEntity.ok(partyService.findPartyById(new PartyIdDto(id)));
    }

    @DeleteMapping("/remove/member/{partyId}")
    @ApiModelProperty("파티에서 탈퇴")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public void removeMemberInParty(@PathVariable Long partyId) {
        partyService.removeMember(partyId);
    }

    @DeleteMapping("/remove/{partyId}")
    @ApiModelProperty("파티 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public void removeParty(@PathVariable(name = "partyId") Long id) {
        partyService.deleteParty(new PartyIdDto(id));
    }

    @GetMapping("/add/{partyId}")
    @ApiModelProperty("파티에 멤버 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<PartyMembers> addMemberInParty(@PathVariable(name = "partyId") Long id) {
        return ResponseEntity.ok(partyService.addMember(new PartyIdDto(id)));
    }

    @PostMapping("/modify/alarm")
    @ApiModelProperty("모임 알람 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<Party> addMemberInParty(@RequestBody AlarmDto alarmDto) {
        return ResponseEntity.ok(partyService.changeAlarmTime(alarmDto));
    }
}
