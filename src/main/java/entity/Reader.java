package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;



@Data
@AllArgsConstructor
public class Reader {

    private long id;

    private String name;

    private String surname;

    private Set<Long> booksId;
}
