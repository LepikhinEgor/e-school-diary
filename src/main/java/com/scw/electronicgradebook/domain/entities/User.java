package com.scw.electronicgradebook.domain.entities;

import com.scw.electronicgradebook.domain.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq_gen")
    @SequenceGenerator(name = "users_id_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Column(name = "security_answer")
    private String securityAnswer;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;

    @ManyToMany
    @JoinTable(name = "roles_users",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
}
