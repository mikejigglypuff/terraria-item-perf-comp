package com.terraria_item_perf_comp.models;

import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_user", columnNames = {"email","password","session_key"})
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private int id;

    private String email;

    @Column(length = 15)
    private String nickname;

    private String sessionKey;

    @Column(columnDefinition = "text")
    private String profileUrl;

    private String password;

    @Column(length = 7)
    private String authCode;

    private LocalDateTime authCodeExpiresAt;

    @Column(columnDefinition = "text")
    private String refreshToken;

    private String oauthProvider;

    private String oauthAuthCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",
            foreignKey = @ForeignKey(name = "fk_users_role"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserRole role;

    @Column(length = 255)
    private String ipHash;
}