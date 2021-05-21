package com.sample.todo.dao;

import java.util.List;

import com.sample.todo.entity.TodoApp;
import com.sample.todo.entity.TodoAppRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

/**
 * データアクセスオブジェクト（DataAccessObject=Dao）<br>
 * データアクセス関連を記述するクラス
 */
@Component
public class TodoAppDao {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public List<TodoApp> getTodoAppList() {
        List<TodoApp> resultList = jdbcTemplate.query("SELECT * FROM TODO_APP", new MapSqlParameterSource(null),
                new TodoAppRowMapper());
        return resultList;
    }

    public int getNextId() {
        int maxTodoId = jdbcTemplate.queryForObject("SELECT ISNULL(MAX(TODO_ID)+1, 1) FROM TODO_APP;",
                new MapSqlParameterSource(null), Integer.class);
        return maxTodoId;
    }

    public void insert(TodoApp todoApp) {
        SqlParameterSource paramMap = new BeanPropertySqlParameterSource(todoApp);
        jdbcTemplate.update("INSERT INTO TODO_APP VALUES(:todoId, :category, :title, :detail)", paramMap);
    }

    public void delete(int todoId) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("todoId", todoId);
        jdbcTemplate.update("DELETE FROM TODO_APP WHERE TODO_ID = :todoId;" ,paramMap);
    }

    public TodoApp edit(int todoId) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("todoId", todoId);
        TodoApp todoApp = jdbcTemplate.queryForObject("SELECT * FROM TODO_APP WHERE TODO_ID = :todoId;", paramMap, new TodoAppRowMapper());
        return todoApp;
    }

    public void update(TodoApp todoApp) {
        SqlParameterSource paramMap = new BeanPropertySqlParameterSource(todoApp);
        jdbcTemplate.update("UPDATE TODO_APP SET CATEGORY = :category, TITLE = :title, DETAIL = :detail WHERE TODO_ID = :todoId;", paramMap);
    }
}
