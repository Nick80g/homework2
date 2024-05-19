package mappers;

import DTO.AuthorDTO;
import entity.Author;

public class AuthorMapper {
    public AuthorDTO authorToAuthorDTO(Author author) {
    AuthorDTO authorDTO = new AuthorDTO();
    authorDTO.setAuthorName(author.getName());
    authorDTO.setAuthorSurname(author.getSurname());
    return authorDTO;
}
}
