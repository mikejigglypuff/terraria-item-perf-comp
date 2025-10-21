package com.terraria_item_perf_comp.models.embeddable;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCompStatsId implements Serializable {
    @Column(name = "situation_id")
    private int situationId;

    @Column(name = "item_id")
    private int itemId;
}