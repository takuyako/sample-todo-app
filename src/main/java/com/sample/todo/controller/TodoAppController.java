package com.sample.todo.controller;

import java.util.List;

import com.sample.todo.entity.TodoApp;
import com.sample.todo.service.TodoAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ブラウザからのリクエストはここにくる
 */
@Controller
public class TodoAppController {

    @Autowired
    private TodoAppService service;

    /**
     * valueの部分がURL<br>
     * POSTを許可しているのは、{@code #register(TodoApp, Model)} からredirectしてくるため
     */
    @RequestMapping(value = { "/", "index" }, method = { RequestMethod.GET, RequestMethod.POST })
    String index(Model model) {
        List<TodoApp> todoList = service.getTodoAppList();
        model.addAttribute("todoList", todoList);// ここの"todoList"というキーがindex.htmlで参照されている
        return "index";// resources/index.htmlを指している
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    String add(@ModelAttribute TodoApp todoApp, Model model) {
        return "detail";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    String register(@Validated @ModelAttribute TodoApp todoApp, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "detail";// 登録失敗したらdetailに戻る
        }
        service.register(todoApp);
        return "redirect:index";// 登録成功したらindexに移る
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    String delete(@RequestParam int todoId, Model model) {
        service.delete(todoId);
        return "redirect:index";// 登録したらindexに移る
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    String edit(@RequestParam int todoId, Model model) {
        TodoApp todoApp = service.edit(todoId);
        model.addAttribute("todoApp", todoApp);
        return "edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    String update(@Validated @ModelAttribute TodoApp todoApp, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";// 更新失敗したらeditに戻る
        }
        service.update(todoApp);
        return "redirect:index";// 更新成功したらindexに移る
    }
}
