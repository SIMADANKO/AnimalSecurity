package com.animalSecurity.service.impl;

import com.animalSecurity.config.CustomUserDetails;
import com.animalSecurity.entity.Users;
import com.animalSecurity.entity.Vendors;
import com.animalSecurity.mapper.UsersMapper;
import com.animalSecurity.mapper.VendorsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetailsService implements UserDetailsService {
    // 实现 UserDetailsService 接口
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private VendorsMapper vendorsMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Vendors vendors=vendorsMapper.selectByVendorname(username);



        if(vendors==null){
            // 从数据库中获取用户信息
            Users user = usersMapper.selectByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }

            // 将 Users 对象转为 Spring Security 的 User 对象
            return new CustomUserDetails(
                    user.getUserId(),
                    user.getUsername(),
                    user.getPassword(),
                    AuthorityUtils.createAuthorityList("ROLE_USER")
            );
        }else{
            return new CustomUserDetails(
                    vendors.getVendorId(),
                    vendors.getVendorName(),
                    vendors.getPassword(),
                    AuthorityUtils.createAuthorityList("ROLE_ADMIN")
            );
        }


    }
}
