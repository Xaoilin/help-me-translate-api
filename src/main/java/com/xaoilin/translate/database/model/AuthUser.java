package com.xaoilin.translate.database.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "auth_user")
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column
    private String email;

    @NotNull
    @Column
    private String password;

    @Column
    private String status;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "auth_user_role")
//    private Set<Role> roles;

    @OneToMany(mappedBy = "authUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SavedTranslations> savedTranslations;

    public AuthUser(String email, String password, String status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public AuthUser() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }

    public List<SavedTranslations> getSavedTranslations() {
        return savedTranslations;
    }

    public void setSavedTranslations(List<SavedTranslations> savedTranslations) {
        this.savedTranslations = savedTranslations;
    }
}
