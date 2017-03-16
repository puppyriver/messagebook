package com.infox.messagebook.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Author: Ronnie.Chen
 * Date: 2017/1/20
 * Time: 15:58
 * rongrong.chen@alcatel-sbell.com.cn
 */
public class UserService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
