package project.licenta.service;

import org.apache.deltaspike.core.api.resourceloader.InjectableResource;
import org.hibernate.service.spi.InjectService;
import project.licenta.entity.User;
import project.licenta.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
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
}
