package com.gmail.roadtojob2019.onlinestore.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "REVIEWS")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;
    private String content;
    @Column(name = "CREATION_TIME")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationTime;
    @Column(name = "DISPLAYED")
    private Boolean isDisplayed;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
}
