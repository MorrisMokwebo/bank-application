package com.mainstream.banking.model.security;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleId;

    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles = new HashSet<>();

}
