package com.nizar.SahTech.services.users.jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.nizar.SahTech.entities.User.User;
import com.nizar.SahTech.repositories.users.UserRepository;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //Write Logic to get the user from the DB
        User user = userRepository.findFirstByEmail(email);
        
        if(user == null){
            throw new UsernameNotFoundException("User not found    ",null);
        }
        userRepository.isActiveSesion(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList< >());
    }

    @Autowired
    public void getUsername(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
