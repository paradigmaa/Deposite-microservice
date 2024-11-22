package ru.ar.account.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    private String name;

    private String email;

    private String phone;

    private OffsetDateTime createdAt;

    public Account(String name, String email, String phone, OffsetDateTime createdAt, List<Long> bills) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.bills = bills;
    }

    @ElementCollection
    private List<Long> bills;

}
