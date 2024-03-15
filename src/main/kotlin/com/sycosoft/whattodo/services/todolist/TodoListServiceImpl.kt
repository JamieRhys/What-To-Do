package com.sycosoft.whattodo.services.todolist

import com.sycosoft.whattodo.AppStrings
import com.sycosoft.whattodo.database.entities.TodoList
import com.sycosoft.whattodo.database.repositories.TodoListRepository
import com.sycosoft.whattodo.exceptions.TodoListInvalidObjectException
import com.sycosoft.whattodo.exceptions.TodoListNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class TodoListServiceImpl(
    private val todoListRepository: TodoListRepository
) : TodoListService {
    override fun saveTodoList(todoList: TodoList): TodoList {
        if (!(todoList.name.isNotBlank() && todoList.name.length <= 50)) {
            throw TodoListInvalidObjectException(AppStrings.Exceptions.TODO_LIST_INVALID_OBJECT_EXCEPTION)
        }

        val savedTodoList: TodoList = todoListRepository.save(todoList)
        return savedTodoList
    }

    override fun updateTodoList(todoList: TodoList): TodoList {
        TODO("Not yet implemented")
    }

    override fun deleteTodoList(todoList: TodoList) {
        TODO("Not yet implemented")
    }

    override fun deleteTodoList(uuid: UUID) {
        TODO("Not yet implemented")
    }

    override fun findTodoListByUUID(uuid: UUID): TodoList {
        val todoList: Optional<TodoList> = todoListRepository.findById(uuid)

        return if(!todoList.isPresent)
            throw TodoListNotFoundException(AppStrings.Exceptions.TODO_LIST_NOT_FOUND_EXCEPTION + uuid)
        else todoList.get()
    }

    override fun findTodoListByName(name: String): TodoList {
        val todoList: Optional<TodoList> = todoListRepository.findTodoListByName(name)

        return if(!todoList.isPresent)
            throw TodoListNotFoundException(AppStrings.Exceptions.TODO_LIST_NOT_FOUND_EXCEPTION + name)
        else todoList.get()
    }
}