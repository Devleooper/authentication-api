package co.com.jwtapp.authenticationapi.service;

import co.com.jwtapp.authenticationapi.entities.AuthUser;
import co.com.jwtapp.authenticationapi.repository.AuthUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("custom-auth")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthUserRespository authUserRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = authUserRespository.findByName(username);

        if(user == null) throw  new UsernameNotFoundException(username);

        return  new User(user.getName() , user.getPassword() , Collections.emptyList());
    }
}
