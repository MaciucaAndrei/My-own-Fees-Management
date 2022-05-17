package project.licenta.repository;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import project.licenta.entity.Reminder;

@Repository(forEntity = Reminder.class)
public abstract class ReminderRepository extends AbstractEntityRepository<Reminder, Long> {
}
