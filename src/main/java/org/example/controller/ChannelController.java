package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ChannelDto;
import org.example.dto.ChannelListDto;
import org.example.service.ChannelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/new")
    public ResponseEntity<ChannelDto> createChannel(@RequestBody ChannelDto dto, @AuthenticationPrincipal Jwt jwt) {
        Long adminId = jwt.getClaim("userId");
        ChannelDto created = channelService.createChannel(dto, adminId);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<ChannelDto> getChannel(@PathVariable Long channelId) {
        return ResponseEntity.ok(channelService.getChannel(channelId));
    }

    @GetMapping("/member")
    public ResponseEntity<List<ChannelListDto>> getChannelsByMember(@AuthenticationPrincipal Jwt jwt) {

        Long userId = jwt.getClaim("userId");
        return ResponseEntity.ok(channelService.getChannelsByMembers(userId));
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long channelId, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        channelService.deleteChannel(channelId, userId);

        return ResponseEntity.noContent().build();
    }
}
