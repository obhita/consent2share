package gov.samhsa.consent2share.showcase.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;

import gov.samhsa.consent2share.showcase.domain.Author;
import gov.samhsa.consent2share.showcase.domain.Book;
import gov.samhsa.consent2share.showcase.infrastructure.DummyBookRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BookControllerTest {

  private BookController controller;
  private DummyBookRepository repository;
  private Author author;

  @Before
  public void before() {
    this.author = new Author("Rob", "Harrop");
    this.repository = new DummyBookRepository();

    this.repository.save(new Book("Pro Spring", this.author));
    this.repository.save(new Book("Pro Jakarta Velocity", this.author));

    this.controller = new BookController();
    this.controller.bookRepository = this.repository;
  }

  @Test
  public void list() {
    List<Book> books = this.controller.list();
    assertNotNull(books);
    assertEquals(2, books.size());
  }

  @Test
  public void find() {
    Book book = this.controller.find(1);
    assertNotNull(book);
  }

  @Test(expected = BookController.BookNotFoundException.class)
  public void findUnknown() {
    this.controller.find(100);
  }

  @Test
  public void create() {
    HttpEntity<?> httpEntity = this.controller.create(new Book("Pro Struts", this.author), new StringBuffer("/books"));
    assertNotNull(httpEntity);
    assertEquals(3, this.repository.findAll().size());
    assertEquals("/books/3", httpEntity.getHeaders().getLocation().toASCIIString());
  }

  @Test
  public void delete() {
    this.controller.delete(2);
    assertEquals(1, this.repository.findAll().size());
  }

  @Test
  public void update() {
    Book book = new Book("AngularJS", this.author);
    this.controller.update(2, book);
    assertEquals("AngularJS", this.repository.findById(2).getTitle());
  }
}
