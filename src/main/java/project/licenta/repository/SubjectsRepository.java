package project.licenta.repository;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import project.licenta.entity.Semester;
import project.licenta.entity.Subjects;

@Repository(forEntity = Subjects.class)
public abstract class SubjectsRepository extends AbstractEntityRepository<Subjects, Long> {
}
