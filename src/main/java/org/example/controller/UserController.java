package org.example.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") @Min(1) Long id, @RequestParam("idRequester") @Min(1) Long idRequester) {
        return userService.getUser(id);
    }


    @PostMapping("api/v1/online")
    public ResponseEntity<Void> markOnline(
            @AuthenticationPrincipal Jwt jwt
    ) {
        String nickname = jwt.getSubject();
        Long userId = jwt.getClaim("userId");

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/profile")
    public String profile() {
        return "Authenticated user";
    }

//
//    @PostMapping()
//    public List<UserDto> getUsersByIds(@RequestBody List<Long> ids) {
//        return userService.getUsersByIds(ids);
//    }
//
//    @PutMapping(UrlUtils.ID + UrlUtils.AVATAR)
//    public void updateUserAvatar(@PathVariable("id") @Min(1) Long id, @RequestBody MultipartFile avatar) {
//        userService.updateUserAvatar(id, avatar);
//    }
//
//    @GetMapping(value = UrlUtils.ID + UrlUtils.AVATAR + UrlUtils.SMALL)
//    public ResponseEntity<byte[]> getSmallAvatar(@PathVariable("id") @Min(1) Long id) {
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
//                .body(userService.getUserAvatar(id, UserAvatarSize.SMALL));
//    }
//
//    @GetMapping(value = UrlUtils.ID + UrlUtils.AVATAR + UrlUtils.LARGE)
//    public ResponseEntity<byte[]> getLargeAvatar(@PathVariable("id") @Min(1) Long id) {
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
//                .body(userService.getUserAvatar(id, UserAvatarSize.LARGE));
//    }
//
//    @DeleteMapping(UrlUtils.ID + UrlUtils.AVATAR)
//    public void deleteUserAvatar(@PathVariable("id") @Min(1) Long id) {
//        userService.deleteUserAvatar(id);
//    }
}


