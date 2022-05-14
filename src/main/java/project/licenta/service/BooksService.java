package project.licenta.service;

import project.licenta.entity.Books;
import project.licenta.repository.BooksRepository;

import javax.inject.Inject;
import java.util.List;

public class BooksService {
    @Inject
    private BooksRepository booksRepository;

    public Books save(Books book) {return  booksRepository.save(book);}

    public List<Books> findAll() {return  booksRepository.findAll();}

    public List<Books>  findBy(Books book) { return  booksRepository.findBy(book);}
}
