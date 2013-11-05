package gov.samhsa.consent2share.showcase.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import gov.samhsa.consent2share.showcase.domain.Author;
import gov.samhsa.consent2share.showcase.domain.Book;
import gov.samhsa.consent2share.showcase.domain.BookRepository;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/api-servlet.xml")
public class BookControllerRequestTest {
  private MockMvc mockMvc;

  @Autowired
  protected WebApplicationContext wac;

  @Autowired
  protected BookRepository repository;

  @Before
  public void setup() {
    this.mockMvc = webAppContextSetup(this.wac).build();
    this.repository.save(new Book("Pro Spring", new Author("Rob", "Harrop")));
  }

  @Test
  public void listBooks() throws Exception {
    mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("Pro Spring"))
        .andExpect(jsonPath("$[0].author.firstName").value("Rob"));
  }

  @Test
  public void getBook() throws Exception {
    mockMvc.perform(get("/books/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Pro Spring"));
  }

  @Test
  public void getUnknownBook() throws Exception {
    mockMvc.perform(get("/books/{id}", 100))
        .andExpect(status().isNotFound());
  }

  @Test @DirtiesContext
  public void deleteBook() throws Exception {
    mockMvc.perform(delete("/books/{id}", 1))
        .andExpect(status().isNoContent());
    assertNull(this.repository.findById(1));
  }

  @Test @DirtiesContext
  public void createBook() throws Exception {
    Book book = new Book("Pro Struts", new Author("Rob", "Harrop"));
    ObjectMapper mapper = new ObjectMapper();
    String content = mapper.writeValueAsString(book);
    mockMvc.perform(post("/books")
        .content(content)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    assertNotNull(this.repository.findById(2));
  }

  @Test @DirtiesContext
  public void updateBook() throws Exception {
    Book book = new Book("Pro Struts", new Author("Rob", "Harrop"));
    ObjectMapper mapper = new ObjectMapper();
    String content = mapper.writeValueAsString(book);
    mockMvc.perform(put("/books/{id}", 1)
        .content(content)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    assertEquals("Pro Struts", this.repository.findById(1).getTitle());
  }
}
