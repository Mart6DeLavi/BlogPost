package org.martix.blogpost.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetail implements UserDetails {
    private UserEntity userEntity;

    public UserDetail(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(userEntity.getRoles().split(", "))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    /***
     * указывает не истёк ли срок действия учётной записи пользователя.
     * Возвращает true = не истёк.
     * Возвращает false = истёк.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /***
     * указывает не заблокирована ли учётная запись пользователя.
     * Возвращает true = не заблокирована.
     * Возвращает false = заблокирована.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /***
     * указывает не истёк ли срок действия учётных данных пользователя.
     * Возвращает true = не истёк.
     * Возвращает false = истёк.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /***
     * указывает включена или нет учётная запись пользователя.
     * Возвращает true = включена.
     * Возвращает false = не включена.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
