package org.example.mapper;

import org.example.dto.UserDto;
import org.example.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getId(),
                user.getNickname(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                extractPostIds(user.getPosts()),
                extractChannelIds(user.getChannels())
        );
    }

    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();

        user.setId(dto.id());
        user.setNickname(dto.nickname());
        user.setName(dto.name());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setRole(dto.role());

        // Posts and channels should normally be loaded from repositories
        // using dto.postIds() and dto.channelIds().
        user.setPosts(Collections.emptyList());
        user.setChannels(Collections.emptyList());

        return user;
    }

    public List<UserDto> toDtoList(List<User> users) {
        if (users == null) {
            return Collections.emptyList();
        }

        return users.stream()
                .map(this::toDto)
                .toList();
    }

    private List<Long> extractPostIds(List<Post> posts) {
        if (posts == null) {
            return Collections.emptyList();
        }

        return posts.stream()
                .map(Post::getId)
                .toList();
    }

    private List<Long> extractChannelIds(List<Channel> channels) {
        if (channels == null) {
            return Collections.emptyList();
        }

        return channels.stream()
                .map(Channel::getId)
                .toList();
    }
}