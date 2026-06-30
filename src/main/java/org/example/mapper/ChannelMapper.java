package org.example.mapper;

import org.example.dto.ChannelDto;
import org.example.dto.ChannelListDto;
import org.example.entity.Channel;
import org.example.entity.User;

import java.util.ArrayList;

public class ChannelMapper {

    public static ChannelListDto toListDto(Channel channel) {
        return new ChannelListDto(
                channel.getId(),
                channel.getName()
        );
    }

    public static ChannelDto toDto(Channel channel) {
        ChannelDto dto = new ChannelDto();
        dto.setId(channel.getId());
        dto.setName(channel.getName());
        dto.setDescription(channel.getDescription());
        dto.setAdminId(channel.getAdmin().getId());
        dto.setMemberCount(channel.getMembers() != null ? channel.getMembers().size() : 0);
        dto.setPostCount(channel.getPosts() != null ? channel.getMembers().size() : 0);
        return dto;
    }

    public static Channel toEntity(ChannelDto dto, User admin) {
        Channel channel = new Channel();
        channel.setName(dto.getName());
        channel.setDescription(dto.getDescription());
        channel.setAdmin(admin);
        channel.setMembers(new ArrayList<>());
        channel.setPosts(new ArrayList<>());
        return channel;
    }
}
