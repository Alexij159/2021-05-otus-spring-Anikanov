package com.anikan.homework.dao;

import com.anikan.homework.domain.Author;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao{
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }


    @Override
    public Author getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, fullName, shortName, birthDate from authors where id = :id", params, new AuthorMapper()
        );
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query(
                "select id, fullName, shortName, birthDate from authors", new AuthorMapper());
    }

    @Override
    public Long insert(Author author) {
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of("fullName", author.getFullName(), "shortName", author.getShortName(), "birthDate", author.getBirthDate()));
        namedParameterJdbcOperations.update("insert into authors (fullName, shortName, birthDate) values (:fullName, :shortName, :birthDate)",
                params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public boolean updateById(Long id, Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of("id", id,"fullName", author.getFullName(), "shortName", author.getShortName(), "birthDate", author.getBirthDate()));
        int updatedRows = namedParameterJdbcOperations.update("update authors set fullName = :fullName, shortName = :shortName, " +
                        "birthDate = :birthDate where id = :id",
                params);

        if (updatedRows == 1)
            return true;
        return false;
    }

    @Override
    public boolean update(Author author) {
        return updateById(author.getId(),author);
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcOperations.update("delete from authors where id = :id", params);
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String fullName = resultSet.getString("fullName");
            String shortName = resultSet.getString("shortName");
            LocalDate birthDate = resultSet.getDate("birthDate").toLocalDate();
            return new Author(id, fullName, shortName, birthDate);
        }
    }
}
