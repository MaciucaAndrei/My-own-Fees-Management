package project.licenta.service;


import project.licenta.entity.AllSubjects;
import project.licenta.entity.Semester;
import project.licenta.repository.AllSubjectsRepository;


import javax.inject.Inject;
import java.util.List;

public class AllSubjectsService {
    @Inject
    private AllSubjectsRepository allSubjectsRepository;

    public AllSubjects save(AllSubjects subject) {return  allSubjectsRepository.save(subject);}

    public List<AllSubjects> findAll() {return  allSubjectsRepository.findAll();}

    public List<AllSubjects>  findBy(AllSubjects subject) { return  allSubjectsRepository.findBy(subject);}
}
