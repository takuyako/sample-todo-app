package com.sample.todo.service;

import java.util.List;

import com.sample.todo.dao.TodoAppDao;
import com.sample.todo.entity.TodoApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ロジックを記述するクラス<br>
 *
 * @Componentと書いておくと、他からはは@Autowiredと記述すれば利用できる。Spring Beanという概念。
 */
@Component
public class TodoAppService {

    /**
     * TodoAppDaoは@Componentを持っているので、@Autowiredで利用できる（裏でSpringがこっそりセットしています）
     */
    @Autowired
    private TodoAppDao dao;

    public List<TodoApp> getTodoAppList() {
        return dao.getTodoAppList();
    }

    public void register(TodoApp todoApp) {
        int nextId = dao.getNextId();
        todoApp.setTodoId(nextId);
        dao.insert(todoApp);
    }

    public void delete(int todoId) {
        dao.delete(todoId);
    }

    public TodoApp edit(int todoId) {
        return dao.edit(todoId);
    }

    public void update(TodoApp todoApp) {
        dao.update(todoApp);
    }
}
