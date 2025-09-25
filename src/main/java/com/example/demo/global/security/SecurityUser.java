package com.example.demo.global.security;


import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/*스프링시큐리티에서 사용하는 사용자 정보를 담는 개체*/

public class SecurityUser extends User {

    @Getter
    private long id;

    public SecurityUser (long id, String username, String password, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.id = id;
    }

    public Authentication genAuthentication() {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                this, //prinsipal (securityUser 자기자신
                this.getPassword(),
                this.getAuthorities()
        );
        return auth;
    }
}
