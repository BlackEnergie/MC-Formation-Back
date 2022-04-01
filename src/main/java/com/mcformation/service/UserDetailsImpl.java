package com.mcformation.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.mcformation.model.database.Utilisateur;
import com.mcformation.model.database.Role;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String nomUtilisateur;
    @JsonIgnore
    private String password;

    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String nomUtilisateur, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Utilisateur utilisateur) {
        List<GrantedAuthority> authorities = utilisateur.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNom().name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                utilisateur.getId(),
                utilisateur.getNomUtilisateur(),
                utilisateur.getEmail(),
                utilisateur.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }


    @Override
    public String getUsername() {
        return nomUtilisateur;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
