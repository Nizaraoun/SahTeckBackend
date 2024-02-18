package com.nizar.SahTech.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.nizar.SahTech.dto.users.SignupDTO;
import com.nizar.SahTech.dto.users.UserDTO;
import com.nizar.SahTech.entities.User.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findFirstByEmail(String email);

    

    //Creat User in db
     default UserDTO createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setCin(signupDTO.getCin());
        user.setPhone(signupDTO.getPhone());
        user.setActive(true);
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        User createdUser = save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());
        userDTO.setCin(createdUser.getCin());
        userDTO.setPhone(createdUser.getPhone());
        userDTO.setActive(createdUser.isActive());
        return userDTO;


        // Get User by email
      

    }


    default UserDTO getUserByEmail(String email) {
        User user = findFirstByEmail(email);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setCin(user.getCin());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }


    // isActive


    default Boolean isActiveSesion(String email) {
        User user = findFirstByEmail(email);
        user.setActive(!user.isActive());
        return user.isActive();
    }



    default UserDTO  authenticateUser(String email, String password){

        User user = findFirstByEmail(email);
        if(user != null && new BCryptPasswordEncoder().matches(password, user.getPassword())){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setName(user.getName());
            userDTO.setCin(user.getCin());
            userDTO.setPhone(user.getPhone());
            userDTO.setActive(user.isActive());
            return userDTO;
        }
        return null;

    };





    

}
