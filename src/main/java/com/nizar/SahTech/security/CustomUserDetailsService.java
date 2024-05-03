package com.nizar.SahTech.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.nizar.SahTech.role.dto.Role;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;




import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private DoctorRepo doctorRepo;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, DoctorRepo doctorRepo) {
        this.userRepository = userRepository;
        this.doctorRepo = doctorRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(email).orElse(null);
        if (user != null) {
            return buildUserDetails(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        }

        DoctorEntity doctor = doctorRepo.findByUsername(email).orElse(null);
        if (doctor != null) {
            return buildUserDetails(doctor.getUsername(), doctor.getPassword(), mapRolesToAuthorities(doctor.getRoles()));
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    private UserDetails buildUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        return new User(username, password, authorities);
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
