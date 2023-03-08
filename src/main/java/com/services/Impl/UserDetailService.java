package com.services.Impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author：Charles
 * @Package：com.services.Impl
 * @Project：EpidemicPreventionAndControl
 * @name：UserDetailService
 * @Date：2023/3/8 18:39
 * @Filename：UserDetailService
 */
public interface UserDetailService {
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException;
}
