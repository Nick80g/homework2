package mappers;

import DTO.BookDTO;
import entity.Book;

public class BookMapper {
public BookDTO bookToBookDTO(Book book) {
    BookDTO bookDTO = new BookDTO();
    bookDTO.setTitle(book.getTitle());
    bookDTO.setAuthor(book.getAuthor());
    return bookDTO;
}
}
