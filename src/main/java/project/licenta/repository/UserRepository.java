package project.licenta.repository;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import project.licenta.entity.User;

@Repository(forEntity = User.class)
public abstract class UserRepository extends AbstractEntityRepository<User, Long> {
}