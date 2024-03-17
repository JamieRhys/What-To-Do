package com.sycosoft.whattodo.database.repositories

import com.sycosoft.whattodo.database.entities.TodoList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TodoListRepository : JpaRepository<TodoList, UUID> {
    @Query("SELECT t FROM table_todo_lists t WHERE t.name = :name")
    fun findTodoListByName(name: String): Optional<TodoList>

    fun save(todoList: TodoList): TodoList
}