package mappers;

import DTO.AuthorDTO;
import DTO.BookDTO;
import entity.Author;
import entity.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    @Test
    void bookToBookDTO() {
        BookMapper mapper = new BookMapper();
        Book book = new Book( 1L, "War and Peace", "Tolstoy");
        BookDTO bookDTO = mapper.bookToBookDTO(book);
        assertEquals(bookDTO.getTitle(), book.getTitle());
        assertEquals(bookDTO.getAuthor(), book.getAuthor());
    }
}