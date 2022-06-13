package project.licenta.service;

import project.licenta.entity.Reminder;
import project.licenta.repository.ReminderRepository;

import javax.inject.Inject;
import java.util.List;

public class ReminderService {
    @Inject
    private ReminderRepository reminderRepository;

    public Reminder save(Reminder reminder) {return  reminderRepository.save(reminder);}

    public List<Reminder> findAll() {return  reminderRepository.findAll();}

}
