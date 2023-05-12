package com.danram.server.controller;

import com.danram.server.domain.post.Feed;
import com.danram.server.domain.post.Post;
import com.danram.server.dto.request.PostDto;
import com.danram.server.dto.request.PostDtoWithId;
import com.danram.server.service.firebase.FirestoreService;
import com.danram.server.service.post.FeedService;
import com.danram.server.service.post.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {
    private final FeedService feedService;
    private final PostService postService;
    private final FirestoreService firestoreService;

    @PostMapping("/create/post")
    @ApiOperation("feed 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<Post> createPost(Long partyId, PostDto postDto, @RequestParam(value = "file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is not exist");
        }

        final String img = firestoreService.uploadFiles(file, file.getOriginalFilename());

        postDto.setImg(img);

        log.info("content: {}", postDto.getContent());
        log.info("img: {}", postDto.getImg());

        return ResponseEntity.ok(postService.createPost(partyId, postDto));
    }

    @PostMapping("/create/withoutImg/post")
    @ApiOperation("feed 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<Post> createPostWithoutImg(@RequestBody PostDtoWithId postDtoWithId) {

        return ResponseEntity.ok(postService.createPost(postDtoWithId.getPartyId(), new PostDto(postDtoWithId.getImg(), postDtoWithId.getContent())));
    }

    @GetMapping("/find/{feedId}")
    @ApiOperation("feed 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<Feed> findFeed(@PathVariable Long feedId) {
        return ResponseEntity.ok(feedService.findFeed(feedId));
    }

    @GetMapping("/find/post/{postId}")
    @ApiOperation("post 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity<Post> findPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findPost(postId));
    }

    @DeleteMapping("/delete/{feedId}")
    @ApiOperation("feed 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "해당 정보를 가진 Member가 없음"),
            @ApiResponse(responseCode = "403", description = "해당 사용자가 Member 권한이 아님"),
            @ApiResponse(responseCode = "401", description = "해당 사용자가 인증되지 않음 | 토큰 만료")
    })
    public ResponseEntity deleteFeed(@PathVariable Long feedId) {
        feedService.deleteFeed(feedId);
        return ResponseEntity.ok().build();
    }
}
