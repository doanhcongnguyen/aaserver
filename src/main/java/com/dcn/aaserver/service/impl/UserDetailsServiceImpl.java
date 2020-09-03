package com.dcn.aaserver.service.impl;


import com.dcn.aaserver.domain.entity.UserEntity;
import com.dcn.aaserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserName(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        this.setPermission(grantedAuthorities);
        return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), grantedAuthorities);
    }

    private void setPermission(Set<GrantedAuthority> grantedAuthorities) {
        // TODO: get permission here..
        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
    }

}
