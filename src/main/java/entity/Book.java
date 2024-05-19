package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class Book {

    public Long id;

    public String title;

    public String author;

    public Set<Long> readersId;

    public Book(){

    }

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
