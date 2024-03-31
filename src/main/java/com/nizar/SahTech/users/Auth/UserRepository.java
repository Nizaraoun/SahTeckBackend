package com.nizar.SahTech.users.Auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    


    //Creat User in db
    //  default UserDTO createUser(SignupDTO signupDTO) {
    //     UserEntity user = new UserEntity();
    //     user.setUsername(signupDTO.getUsername());
    //     user.setEmail(signupDTO.getEmail());
    //     user.setCin(signupDTO.getCin());
    //     user.setPhone(signupDTO.getPhone());
    //     user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
    //     UserEntity createdUser = save(user);
    //     UserDTO userDTO = new UserDTO();
    //     userDTO.setId(createdUser.getId());
    //     userDTO.setEmail(createdUser.getEmail());
    //     userDTO.setName(createdUser.getUsername());
    //     userDTO.setCin(createdUser.getCin());
    //     userDTO.setPhone(createdUser.getPhone());
    //     return userDTO;


        // Get User by email
      

    }


    // default UserDTO getUserByEmail(String email) {
    //     User user = findFirstByEmail(email);
    //     UserDTO userDTO = new UserDTO();
    //     userDTO.setId(user.getId());
    //     userDTO.setEmail(user.getEmail());
    //     userDTO.setName(user.getName());
    //     userDTO.setCin(user.getCin());
    //     userDTO.setPhone(user.getPhone());
    //     return userDTO;
    // }


    // isActive





    // default UserDTO  authenticateUser(String email, String password){

    //     User user = findFirstByEmail(email);
    //     if(user != null && new BCryptPasswordEncoder().matches(password, user.getPassword())){
    //         UserDTO userDTO = new UserDTO();
    //         userDTO.setId(user.getId());
    //         userDTO.setEmail(user.getEmail());
    //         userDTO.setName(user.getName());
    //         userDTO.setCin(user.getCin());
    //         userDTO.setPhone(user.getPhone());
    //         return userDTO;
    //     }
    //     return null;

    // };





    


