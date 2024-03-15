package com.sycosoft.whattodo.database.repositories

import com.sycosoft.whattodo.database.entities.TodoList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TodoListRepository : JpaRepository<TodoList, UUID> {
    @Query("SELECT c FROM TodoList t WHERE t.name = :name")
    fun findTodoListByName(name: String): Optional<TodoList>

    fun save(todoList: TodoList): TodoList
}