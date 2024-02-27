// package com.nizar.SahTech.users.service;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.nizar.SahTech.users.entite.User;
// import com.nizar.SahTech.users.repository.UserRepository;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;


// @Service
// @RequiredArgsConstructor
// public class UserDetailsServiceImpl implements UserDetailsService {

//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;



//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//         //Write Logic to get the user from the DB
//         Optional<User> user = userRepository.findFirstByEmail(email);
        
//         if(user == null){
//             throw new UsernameNotFoundException("User not found    ",null);
//         }
//      String password = passwordEncoder.encode(user.get().getPassword());
//        String role= user.get().getRole();
//         List<GrantedAuthority> roles = new ArrayList<>();
//         roles.add(new SimpleGrantedAuthority(role));
//         return new AppUserDetails( email, password, roles);
//     }

    
// }
