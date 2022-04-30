package project.licenta.service;

import project.licenta.entity.User;
import project.licenta.repository.UserRepository;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.List;

public class UserService {
    @Inject
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User>  findBy(User user) { return userRepository.findBy(user);}

}
