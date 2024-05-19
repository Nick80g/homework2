package controllers;

import entity.Book;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import repositories.BookRepository;
import repositories.DatabaseConnection;
import services.BookService;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Testcontainers
public class BooksControllerTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    private BooksController booksController = new BooksController();

    private final BookService bookService = new BookService(new BookRepository());

    private Connection testConnection;
    private MockedStatic<DatabaseConnection> connectionMock;

    @BeforeEach
    void beforeEach() throws SQLException {
        postgres.start();
        testConnection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        connectionMock = Mockito.mockStatic(DatabaseConnection.class);
        connectionMock.when(DatabaseConnection::getConnection).thenReturn(testConnection);
        ServletContextEvent sce = Mockito.mock(ServletContextEvent.class);
        ContextListener listener = new ContextListener();
        listener.contextInitialized(sce);
        listener.contextDestroyed(sce);

    }


    @AfterEach
    void afterEach() throws SQLException {
        if (testConnection != null && !testConnection.isClosed()) {
            testConnection.close();
        }
        postgres.stop();
        connectionMock.close();
    }

    @Test
    public void testGetBooks() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        booksController.doGet(request, response);

        printWriter.flush();

        assertTrue(stringWriter.toString().contains("[{\"id\":1,\"title\":\"book\",\"author\":\"author\"}]"));
        verify(response).setContentType("application/json");

    }

    @Test
    public void testPostBook() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("title")).thenReturn("Book");
        when(request.getParameter("author")).thenReturn("Author");

        booksController.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        Book book = bookService.getBookById(2);
        assertEquals("Book", book.getTitle());
        assertEquals("Author", book.getAuthor());
    }

    @Test
    public void testPutBook() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("bookId")).thenReturn("1");
        when(request.getParameter("readerId")).thenReturn("1");

        booksController.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDeleteBook() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("1");
        booksController = new BooksController();
        booksController.doDelete(request, response);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
