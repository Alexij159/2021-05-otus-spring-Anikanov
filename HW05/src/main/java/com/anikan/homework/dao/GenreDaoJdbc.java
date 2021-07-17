package com.anikan.homework.dao;

import com.anikan.homework.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Genre getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, `name` from genres where id = :id", params, new GenreMapper()
        );

    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query(
                "select id, `name` from genres", new GenreMapper()
        );
    }

    @Override
    public Long insert(Genre genre) {
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of("name", genre.getName()));
        namedParameterJdbcOperations.update("insert into genres (`name`) values (:name)",
                params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public boolean updateById(Long id, Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource(Map.of("id", id,"name", genre.getName()));
        int updatedRows = namedParameterJdbcOperations.update("update genres set `name` =:name " +
                        " where id = :id",
                params);

        if (updatedRows == 1)
            return true;
        return false;
    }

    @Override
    public boolean update(Genre genre) {
        return updateById(genre.getId(),genre);
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcOperations.update("delete from genres where id = :id", params);
    }

    private static class GenreMapper implements RowMapper<Genre>{

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
