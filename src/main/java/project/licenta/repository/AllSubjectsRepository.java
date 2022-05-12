package project.licenta.repository;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import project.licenta.entity.AllSubjects;

@Repository(forEntity = AllSubjects.class)
public abstract class AllSubjectsRepository extends AbstractEntityRepository<AllSubjects, Long> {
}
