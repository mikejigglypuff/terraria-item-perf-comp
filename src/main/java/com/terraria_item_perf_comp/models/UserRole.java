package com.terraria_item_perf_comp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_roles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {
    @Id
    private int id;

    private String roleName;
}