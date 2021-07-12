package com.anikan.homework.dao;

import com.anikan.homework.domain.Author;
import com.anikan.homework.domain.Book;
import com.anikan.homework.domain.Genre;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbc;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Book getById(Long id) {
        Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcOperations.queryForObject("select b.id, b.title, b.authorId as author_id, b.genreId as genre_id, g.name as genre_name, " +
                "a.fullName as author_fullName, a.shortName as author_shortName, a.BIRTHDATE as author_birthDate " +
                "from books b " +
                "left join genres g on b.genreId = g.id " +
                "left join authors a on b.authorId = a.id " +
                "where b.id = :id", params, new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id, b.title, b.authorId as author_id, b.genreId as genre_id, g.name as genre_name, " +
                "a.fullName as author_fullName, a.shortName as author_shortName, a.birthDate as author_birthDate " +
                "from books b " +
                "left join genres g on b.genreId = g.id " +
                "left join authors a on b.authorId = a.id ", new BookMapper());
    }

    @Override
    public Long insert(Book book) {
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        //params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());

        namedParameterJdbcOperations.update("insert into books (title, authorId, genreId) values (:title, :authorId, :genreId)",
                params, kh);
        return kh.getKey().longValue();
    }


    @Override
    public boolean update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of("id", book.getId(), "title", book.getTitle(), "authorId", book.getAuthor().getId(),
                "genreId", book.getGenre().getId()));
        int updatedRows = namedParameterJdbcOperations.update("update books set title = :title, authorId = :authorId, genreId = :genreId where id = :id",
                params);

        if (updatedRows == 1)
            return true;
        return false;
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcOperations.update("delete from books where id = :id", params);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            Genre g = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name"));
            Author a = new Author(resultSet.getLong("author_id"), resultSet.getString("author_fullName"),
                    resultSet.getString("author_shortName"), resultSet.getDate("author_birthDate").toLocalDate());
            return new Book(id,title,a,g);
        }
    }
}
