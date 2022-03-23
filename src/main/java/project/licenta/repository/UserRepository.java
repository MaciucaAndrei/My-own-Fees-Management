package project.licenta.repository;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import project.licenta.entity.User;

import java.util.List;

@Repository(forEntity = User.class)
public abstract class UserRepository extends AbstractEntityRepository<User, Long> {
}