package com.estudios.virtuales.estudios.virtuales.domain.entities;

import com.estudios.virtuales.estudios.virtuales.utils.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {//usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50,nullable = false)
    private  String userName;
    @Column(nullable = false)
    private String password;
    @Column(length = 100,nullable = false)
    private String email;
    @Column(length = 100,nullable = false)
    private String fullName;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = false,fetch = FetchType.LAZY)
    private List<Submission>submissions;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL,orphanRemoval = false)
    private Set<Enrollment> enrollments=new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "instructor",cascade = CascadeType.ALL,orphanRemoval = false,fetch = FetchType.LAZY)
    private List<Course> courses;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Message> receivedMessages = new HashSet<>();
}
//SIGUE CREAR LOS RESPONSE
//LUEGO LOS REQUEST