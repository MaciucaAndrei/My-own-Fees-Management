package project.licenta.repository;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import project.licenta.entity.Semester;

@Repository(forEntity = Semester.class)
public abstract class SemesterRepository extends AbstractEntityRepository<Semester, Long> {
}
