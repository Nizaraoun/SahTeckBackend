// package com.nizar.SahTech.users.service;

// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.List;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import com.nizar.SahTech.users.entite.User;


// public class AppUserDetails  implements UserDetails{

//     private final String username;
//     private final String password;
//     private  final List<GrantedAuthority> roles;
    

//     // public AppUserDetails( String username , String password , List<GrantedAuthority> roles) {
//     //     this.username = email;
//     //     this.password = password;
//     //     this.roles = roles;   

//     // }   
// public AppUserDetails(String email, String password, List<GrantedAuthority> roles) {
//     this.username = email;
//     this.password = password;
//     this.roles = roles;       }

    
//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         // TODO Auto-generated method stub
//         return this.roles;
//     }

//     @Override
//     public String getPassword() {
//         // TODO Auto-generated method stub
//         return this.password;
//     }

//     @Override
//     public String getUsername() {
//         // TODO Auto-generated method stub
//        return this.username;
//     }


  
//     @Override
//     public boolean isAccountNonExpired() {
//         // TODO Auto-generated method stub
//         return true;
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         // TODO Auto-generated method stub
//         return true;
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         // TODO Auto-generated method stub
//         return true;
//     }

//     @Override
//     public boolean isEnabled() {
//         // TODO Auto-generated method stub
//         return true;
//     }
    
// }
