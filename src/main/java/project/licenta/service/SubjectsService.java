package project.licenta.service;

import project.licenta.entity.Semester;
import project.licenta.entity.Subjects;
import project.licenta.repository.SubjectsRepository;

import javax.inject.Inject;
import java.util.List;

public class SubjectsService {

    @Inject
    private SubjectsRepository subjectsRepository;

    public Subjects save(Subjects subjects) {return  subjectsRepository.save(subjects);}

    public List<Subjects> findAll() {return  subjectsRepository.findAll();}

    public List<Subjects>  findBy(Subjects subjects) { return  subjectsRepository.findBy(subjects);}
}
