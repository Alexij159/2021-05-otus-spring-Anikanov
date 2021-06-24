package com.anikan.homework.dao;

import com.anikan.homework.domain.Book;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        return namedParameterJdbcOperations.queryForObject("select id,title, authorId, genreId from books where id = :id", params, new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select id,title, authorId, genreId from books", new BookMapper());
    }

    @Override
    public Long insert(Book book) {
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of("title", book.getTitle(), "authorId", book.getAuthorId(),
                "genreId", book.getGenreId()));
        namedParameterJdbcOperations.update("insert into books (title, authorId, genreId) values (:title, :authorId, :genreId)",
                params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public boolean updateById(Long id, Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of("id", id, "title", book.getTitle(), "authorId", book.getAuthorId(),
                "genreId", book.getGenreId()));
        int updatedRows = namedParameterJdbcOperations.update("update books set title = :title, authorId = :authorId, genreId = :genreId where id = :id",
                params);

        if (updatedRows == 1)
            return true;
        return false;
    }

    @Override
    public boolean update(Book book) {
        return updateById(book.getId(), book);
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
            long authorId = resultSet.getLong("AUTHORID");
            long genreId = resultSet.getLong("genreid");
            return new Book(id,title,authorId,genreId);
        }
    }
}
