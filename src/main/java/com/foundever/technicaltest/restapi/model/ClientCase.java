package com.foundever.technicaltest.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "client_case")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class ClientCase {
                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                @Column(name = "client_case_id")
                private Long clientCaseId;

                @Column(name = "client_name")
                private String name;

                @JsonIgnoreProperties({"clientCase"})
                @OneToMany(mappedBy = "clientCase", fetch = FetchType.EAGER)
                private List<Message> messages;

                @Column(name = "client_reference")
                private String clientReference;

}
