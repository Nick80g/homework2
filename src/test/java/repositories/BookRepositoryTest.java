package repositories;

import controllers.BooksController;
import controllers.ContextListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import services.BookService;

import javax.servlet.ServletContextEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
class BookRepositoryTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    private final BookRepository bookRepository = new BookRepository();

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
    void getBookReaders() {
        Set<Long> setId = bookRepository.getBookReaders(1L);
        List<Long> listId = new ArrayList<>(setId);
        assertEquals(1,listId.get(0));
        assertEquals(2,listId.get(1));
        assertEquals(3,listId.get(2));
        assertEquals(3, listId.size());
    }
}