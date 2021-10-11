package com.example.algostudy.domain.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    private LocalDateTime sendDateTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
