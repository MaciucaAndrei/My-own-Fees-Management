package project.licenta.repository;

import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import project.licenta.entity.Books;

@Repository(forEntity = Books.class)
public abstract class BooksRepository extends AbstractEntityRepository<Books, Long> {
}
