package gov.samhsa.consent2share.showcase.domain;

import java.util.List;

/**
 * Basic repository for CRUD operations on {@see Book books}.
 */
public interface BookRepository {

  /**
   * Find a <code>Book</code> by ID.
   *
   * @param id the ID of the book.
   * @return the book, or <code>null</code> if no book is found.
   */
  Book findById(Integer id);

  List<Book> findAll();

  Book save(Book book);

  void delete(Integer id);
}
