package repositories;

import entity.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BookRepository {

    public Set<Long> getBookReaders(Long bookId){
        Set<Long> idSet = new HashSet<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT reader_id FROM reader_book LEFT JOIN books ON books.id = reader_book.book_id WHERE reader_book.book_id = ?");
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet  = preparedStatement.executeQuery();

            while (resultSet.next()){
                long id = resultSet.getLong("reader_id");
                idSet.add(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  idSet;
    }

    public  List<Book> getBookData() {
        List<Book> books = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                books.add(new Book(id, title, author));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public void addBookToReader (long readerId, long bookId){
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reader_book(reader_id, book_id) VALUES (?, ?)");
            preparedStatement.setLong(1, readerId);
            preparedStatement.setLong(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Book getBookById(long id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            return new Book(id, title, author, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBook(Book book){
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books(title, author) VALUES (?, ?)");
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBook(long id, String readers){
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET readers = ? WHERE id = ?");
            preparedStatement.setLong(2, id);
            preparedStatement.setString(1, readers);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteBook(long id){
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM books WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
