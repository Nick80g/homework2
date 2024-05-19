package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class Author {

    private long id;

    private String name;

    private String surname;

    private Set<Long> booksId;
}
