package gov.samhsa.consent2share.showcase.infrastructure;

import org.springframework.stereotype.Component;

import gov.samhsa.consent2share.showcase.domain.Book;
import gov.samhsa.consent2share.showcase.domain.BookRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A very noddy implementation of the {@see BookRepository} interface.
 */
@Component
public class DummyBookRepository implements BookRepository {
  private final Map<Integer, Book> books = new ConcurrentHashMap<Integer, Book>();

  @Override
  public Book findById(Integer id) {
    return this.books.get(id);
  }

  @Override
  public List<Book> findAll() {
    List<Book> books = new ArrayList<Book>(this.books.values());
    Collections.sort(books, new Comparator<Book>() {
      @Override
      public int compare(Book o1, Book o2) {
        return o1.getId() - o2.getId();
      }
    });
    return books;
  }

  @Override
  public Book save(Book book) {
    if (book.getId() == null) {
      book.setId(nextId());
    }
    this.books.put(book.getId(), book);
    return book;
  }

  @Override
  public void delete(Integer id) {
    this.books.remove(id);
  }

  private Integer nextId() {
    if (this.books.isEmpty()) {
      return 1;
    } else {
      return Collections.max(this.books.keySet()) + 1;
    }
  }
}
