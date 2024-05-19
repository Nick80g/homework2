package mappers;

import DTO.AuthorDTO;
import entity.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperTest {

    @Test
    void authorToAuthorDTO() {
        AuthorMapper mapper = new AuthorMapper();
        Author author = new Author(1, "Nick", "Sergeev", null);
        AuthorDTO authorDTO = mapper.authorToAuthorDTO(author);
        assertEquals(authorDTO.getAuthorName(), author.getName());
        assertEquals(authorDTO.getAuthorSurname(), author.getSurname());
    }
}