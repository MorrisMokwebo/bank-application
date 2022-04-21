package com.mainstream.banking.model.security;

import com.mainstream.banking.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userRoleId;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;


}
