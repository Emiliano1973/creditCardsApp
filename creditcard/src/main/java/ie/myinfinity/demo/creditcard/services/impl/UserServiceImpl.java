package ie.myinfinity.demo.creditcard.services.impl;

import ie.myinfinity.demo.creditcard.dao.repositories.RoleRepository;
import ie.myinfinity.demo.creditcard.dao.repositories.UserRepository;
import ie.myinfinity.demo.creditcard.dtos.UserDto;
import ie.myinfinity.demo.creditcard.entities.Role;
import ie.myinfinity.demo.creditcard.entities.User;
import ie.myinfinity.demo.creditcard.exceptions.UserAlreadyinsertedException;
import ie.myinfinity.demo.creditcard.exceptions.UserException;
import ie.myinfinity.demo.creditcard.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;



    @Override
    @Transactional
    public void save(UserDto userDto) throws UserAlreadyinsertedException,UserException {
        //check if the user is set
        if (StringUtils.isBlank(userDto.getUsername())){
            throw new UserException("Username cannot be empty or null");
        }
        // check if the password is set
        if (StringUtils.isBlank(userDto.getPassword())){
            throw new UserException("Password cannot be empty or null");
        }


        //check if the user is already present
        long countUserName=this.userRepository.countByUsernameIgnoreCase(userDto.getUsername().trim());
        if (countUserName>0l){
            throw new UserAlreadyinsertedException("User with username "+userDto.getUsername()+" is already present in db");
        }
        //the users alre all normal users for default
        Role  role=roleRepository.findByDescription("USER");
        User user=new User();
        user.setUsername(userDto.getUsername().trim());
        user.setPassword(userDto.getPassword());
        user.setRole(role);
        //insert the user
        this.userRepository.save(user);
    }


    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public boolean matchUserAndPassword(String username, String password) {
        return this.userRepository.countByUsernameIgnoreCaseAndPassword(username, password)==1;
    }
}
