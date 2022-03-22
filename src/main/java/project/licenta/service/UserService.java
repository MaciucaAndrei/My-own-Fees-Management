package project.licenta.service;

import org.apache.deltaspike.data.api.Repository;
import project.licenta.entity.User;
import project.licenta.repository.UserRepository;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class UserService {
    @Inject
    private UserRepository userRepository;
    public void save(String name, String lastname) {
        User user = new User(name, lastname, LocalDate.now());
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
