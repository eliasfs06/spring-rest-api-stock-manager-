package com.eliasfs06.spring.restapi.stock.manager.service;

import com.eliasfs06.spring.restapi.stock.manager.model.Person;
import com.eliasfs06.spring.restapi.stock.manager.model.User;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.RegisterDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import com.eliasfs06.spring.restapi.stock.manager.repository.UserRepository;
import com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User>{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonService personService;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void registerUser(RegisterDTO registerData) throws BusinessException {
        Person person = registerData.toPerson();
        User user = registerData.toUser();
        user.setPerson(person);
        encodePassword(user);

        if(user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getPerson().getEmail().isEmpty()
                || user.getPerson().getName().isEmpty()){
            throw new BusinessException(MessageCode.DEFAULT_EMPTY_FIELD_MSG);
        }

        if(userRepository.findByUsername(registerData.getUsername()) != null) {
            throw new BusinessException(MessageCode.USER_ALREADY_EXIST);
        }

        personService.create(person);
        create(user);
    }

    public void encodePassword(User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
