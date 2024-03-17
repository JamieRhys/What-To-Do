package com.sycosoft.whattodo.controllers

import com.sycosoft.whattodo.database.entities.TodoList
import com.sycosoft.whattodo.services.todolist.TodoListService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController()
@RequestMapping("/api")
class TodoListController(
    private val todoListService: TodoListService
) {
    private val logger: Logger = LoggerFactory.getLogger(TodoListController::class.java.simpleName)

    @GetMapping("/todo-lists")
    fun fetchAllTodoLists() : List<TodoList> = todoListService.findAllTodoLists()

    @GetMapping("/todo-lists/{id}")
    fun fetchTodoList(@PathVariable("id") uuid: UUID) : TodoList = todoListService.findTodoListByUUID(uuid)

    @PostMapping("/todo-lists")
    fun saveTodoList(@Valid @RequestBody todoList: TodoList): TodoList {
        logger.info("Received request to save Todo List ${todoList.name}")
        return todoListService.saveTodoList(todoList)
    }
}