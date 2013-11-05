package gov.samhsa.consent2share.showcase.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import gov.samhsa.consent2share.showcase.domain.Book;
import gov.samhsa.consent2share.showcase.domain.BookRepository;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

  @Autowired
  BookRepository bookRepository;

  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody List<Book> list() {
    return this.bookRepository.findAll();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public @ResponseBody Book find(@PathVariable("id") Integer id) {
    Book book = this.bookRepository.findById(id);
    if (book == null) {
      throw new BookNotFoundException(id);
    }
    return book;
  }

  @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
  @ResponseStatus(HttpStatus.CREATED)
  public HttpEntity<?> create(@RequestBody Book book, @Value("#{request.requestURL}") StringBuffer parentUri) {
    book = this.bookRepository.save(book);
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(childLocation(parentUri, book.getId()));
    return new HttpEntity<Object>(headers);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Integer id) {
    this.bookRepository.delete(id);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable("id") Integer id, @RequestBody Book book) {
    book.setId(id);
    this.bookRepository.save(book);
  }


  private URI childLocation(StringBuffer parentUri, Object childId) {
    UriTemplate uri = new UriTemplate(parentUri.append("/{childId}").toString());
    return uri.expand(childId);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Integer id) {
      super("Book '" + id + "' not found.");
    }
  }
}
