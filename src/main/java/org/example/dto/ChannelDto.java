package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDto {
    private Long id;
    private String name;
    private String description;
    private Long adminId;
    private int memberCount;
    private int postCount;
}
