package com.gmail.roadtojob2019.onlinestore.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "ITEMS")
public class Item {
    @Id
    @GeneratedValue
    @Setter(value = AccessLevel.NONE)
    private UUID id;
    private String name;
    @Column(name = "BRIEF_DESCRIPTION")
    private String briefDescription;
    private Price price;
}
