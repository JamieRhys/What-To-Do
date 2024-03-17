package com.sycosoft.whattodo.services.todolist

import com.sycosoft.whattodo.database.entities.TodoList
import java.util.*

interface TodoListService {
    fun saveTodoList(todoList: TodoList): TodoList
    fun updateTodoList(todoList: TodoList): TodoList
    fun deleteTodoList(todoList: TodoList)
    fun deleteTodoList(uuid: UUID)
    fun deleteTodoList(name: String)

    fun findAllTodoLists(): List<TodoList>
    fun findTodoListByUUID(uuid: UUID): TodoList
    fun findTodoListByName(name: String): TodoList
}