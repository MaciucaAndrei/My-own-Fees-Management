package project.licenta.service;

import project.licenta.entity.Semester;
import project.licenta.repository.SemesterRepository;

import javax.inject.Inject;
import java.util.List;

public class SemesterService {
        @Inject
        private SemesterRepository semesterRepository;

        public Semester save(Semester subject) {return  semesterRepository.save(subject);}

        public List<Semester> findAll() {return  semesterRepository.findAll();}

        public List<Semester>  findBy(Semester semester) { return  semesterRepository.findBy(semester);}

}
