package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_attachments")
public class PostAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private String originalFileName;
    private String storedFileName;
    private String contentType;
    private Long size;
    private String url;
    private LocalDateTime createdAt;
}