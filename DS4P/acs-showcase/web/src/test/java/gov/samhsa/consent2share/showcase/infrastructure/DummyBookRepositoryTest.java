package gov.samhsa.consent2share.showcase.infrastructure;

import org.junit.Before;
import org.junit.Test;

import gov.samhsa.consent2share.showcase.domain.Author;
import gov.samhsa.consent2share.showcase.domain.Book;

import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DummyBookRepositoryTest {
  private DummyBookRepository repository;
  private Author testAuthor;

  @Before
  public void before() {
    this.repository = new DummyBookRepository();
    this.testAuthor = new Author("Rob", "Harrop");
  }

  @Test
  public void saveAndGet() {
    Book book = new Book();
    book.setAuthor(this.testAuthor);
    book.setTitle("Pro Spring");

    book = this.repository.save(book);
    assertNotNull(book.getId());

    Book book2 = this.repository.findById(book.getId());
    assertEquals(book, book2);
  }

  @Test
  public void saveAndDelete() {
    Book book = new Book();
    book.setAuthor(this.testAuthor);
    book.setTitle("Pro Spring");

    book = this.repository.save(book);
    assertNotNull(book.getId());

    this.repository.delete(book.getId());
    assertNull(this.repository.findById(book.getId()));
  }

  @Test
  public void saveAndUpdate() {
    Book book = new Book();
    book.setAuthor(this.testAuthor);
    book.setTitle("Pro Spring");

    book = this.repository.save(book);
    Integer id = book.getId();

    book = this.repository.save(book);
    assertEquals(id, book.getId());

  }

  @Test
  public void listAll() {
    Book book = new Book();
    book.setAuthor(this.testAuthor);
    book.setTitle("Pro Spring");

    book = this.repository.save(book);
    book.setId(null);
    this.repository.save(book);

    List<Book> books = this.repository.findAll();
    assertNotNull(books);
    assertEquals(2, books.size());
  }
}
