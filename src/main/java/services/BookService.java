package services;

import com.google.gson.Gson;
import entity.Book;
import repositories.BookRepository;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class BookService {
    private final Gson gson = new Gson();
    private final BookRepository bookRepository;

    public BookService(BookRepository repository) {
        this.bookRepository = repository;

    }

    public List<Book> getAllBooks() {
        return bookRepository.getBookData();
    }

    public void addBookToReader(long readerId, long bookId){
        bookRepository.addBookToReader(readerId, bookId);
    }

    public Book getBookById(long id){
        return bookRepository.getBookById(id);
    }

    public void save(Book book) {
        bookRepository.saveBook(book);
    }

    public void delete(long id) {
        bookRepository.deleteBook(id);
    }

    public void upDate(long id, String readers){
        bookRepository.updateBook(id, readers);
    }


    public void sendAsJson(
            HttpServletResponse response,
            Object obj) throws IOException {

        response.setContentType("application/json");
        String res = gson.toJson(obj);
        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
    }

}

