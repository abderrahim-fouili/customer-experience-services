package com.foundever.technicaltest.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Message {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "message_id")
        private Long messageId;

        @ManyToOne
        @JsonIgnoreProperties({"messages"})
        @JoinColumn(name = "client_case_id")
        private ClientCase clientCase;

        @Column(name = "sender")
        private String sender;

        @Column(name = "content")
        private String content;

        @Column(name = "flag")
        private String flag;

        @Column(name = "created_at")
        @Temporal(TemporalType.TIMESTAMP)
        private Date createdAt;

}

