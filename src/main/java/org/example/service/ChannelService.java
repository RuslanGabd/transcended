package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ChannelDto;
import org.example.dto.ChannelListDto;
import org.example.entity.Channel;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.entity.UserRoles;
import org.example.mapper.ChannelMapper;
import org.example.repo.ChannelRepository;
import org.example.repo.PostRepository;
import org.example.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public ChannelDto createChannel(ChannelDto dto, Long adminId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Channel channel = ChannelMapper.toEntity(dto, admin);
        Channel saved = channelRepository.save(channel);

        return ChannelMapper.toDto(saved);
    }

    public ChannelDto getChannel(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        return ChannelMapper.toDto(channel);
    }

    public List<ChannelListDto> getChannelsByMembers(Long userId) {
        return channelRepository.findByMembersId(userId).stream()
                .map(ChannelMapper::toListDto).toList();
    }

    public void deleteChannel(Long channelId, Long userId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        User user = userRepository.findById((channelId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!channel.getAdmin().getId().equals(userId) && !user.getRole().equals(UserRoles.ADMIN))
            throw new RuntimeException("You cannot delete this channel");

        if (channel.getPosts() != null) {
            for (Post post : channel.getPosts()) {
                post.setDataDeleted(LocalDateTime.now());
                postRepository.save(post);
            }
        }
        channelRepository.delete(channel);
    }
}
