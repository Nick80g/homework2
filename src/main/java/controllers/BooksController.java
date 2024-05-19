package controllers;


import entity.Book;
import repositories.BookRepository;
import services.BookService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



@WebServlet(urlPatterns = "/books")
public class BooksController extends HttpServlet {
    BookService bookService = new BookService(new BookRepository());

@Override
    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        List<Book> books = bookService.getAllBooks();
        bookService.sendAsJson(response, books);
    }

@Override
    public void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String title = request.getParameter("title");
        String author = request.getParameter("author");

        if (title != null || !title.isEmpty() || author != null || !author.isEmpty()) {
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            bookService.save(book);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
@Override
    public void doPut(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
       String book = request.getParameter("bookId");
       String reader = request.getParameter("readerId");
       if (book != null || !book.isEmpty() || reader != null || !reader.isEmpty()){
           long bookId = Long.parseLong(book);
           long readerId = Long.parseLong(reader);
           bookService.addBookToReader(bookId, readerId);
           response.setStatus(HttpServletResponse.SC_OK);
       }else
       {
           response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
       }
    }

@Override
    public void doDelete(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        String id = request.getParameter("id");
           if(id != null || !id.isEmpty()){
               long bookId = Long.parseLong(id);
               bookService.delete(bookId);
               response.setStatus(HttpServletResponse.SC_OK);
           } else {
               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
           }
    }
}

