package gov.samhsa.consent2share.showcase.domain;

public class Book {
  private Integer id;
  private String title;
  private Author author;

  public Book() {
  }

  public Book(String title, Author author) {
    this.title = title;
    this.author = author;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }
}
