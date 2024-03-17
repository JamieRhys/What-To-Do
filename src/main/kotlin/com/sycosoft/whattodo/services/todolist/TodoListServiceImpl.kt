package com.sycosoft.whattodo.services.todolist

import com.sycosoft.whattodo.AppStrings
import com.sycosoft.whattodo.database.entities.TodoList
import com.sycosoft.whattodo.database.repositories.TodoListRepository
import com.sycosoft.whattodo.exceptions.TodoListDeletionException
import com.sycosoft.whattodo.exceptions.TodoListInvalidObjectException
import com.sycosoft.whattodo.exceptions.TodoListNotFoundException
import com.sycosoft.whattodo.exceptions.TodoListUpdateException
import org.springframework.stereotype.Service
import java.util.*

@Service
class TodoListServiceImpl(
    private val todoListRepository: TodoListRepository
) : TodoListService {

    override fun saveTodoList(todoList: TodoList): TodoList {
        if (!(todoList.name.isNotBlank() && todoList.name.length <= 50)) {
            throw TodoListInvalidObjectException(AppStrings.Exceptions.TODO_LIST_INVALID_OBJECT_EXCEPTION + "Must have a name.")
        }

        val savedTodoList: TodoList = todoListRepository.save(todoList)
        return savedTodoList
    }

    override fun updateTodoList(todoList: TodoList): TodoList {
        return try {
            val found = todoList.uuid?.let { findTodoListByUUID(it) }
            var hasChanged = false

            if(Objects.nonNull(todoList.name) && !todoList.name.equals("", ignoreCase = true) &&
                !found?.name?.equals(todoList.name)!!
            ) {
                found.name = todoList.name
                hasChanged = true
            }

            if(hasChanged) { todoListRepository.save(found!!) }
            else throw TodoListUpdateException(AppStrings.Exceptions.TODO_LIST_UPDATE_EXCEPTION + "Nothing to update")
        } catch(exception: TodoListNotFoundException) {
            throw TodoListUpdateException(AppStrings.Exceptions.TODO_LIST_UPDATE_EXCEPTION + exception.message)
        }
    }

    override fun deleteTodoList(todoList: TodoList) {
        todoListRepository.delete(todoList)
    }

    override fun deleteTodoList(uuid: UUID) {
        try {
            val found = findTodoListByUUID(uuid)

            deleteTodoList(found)
        } catch(exception: TodoListNotFoundException) {
            throw TodoListDeletionException(AppStrings.Exceptions.TODO_LIST_DELETION_EXCEPTION + exception.message)
        }
    }

    override fun deleteTodoList(name: String) {
        try {
            val found = findTodoListByName(name)

            deleteTodoList(found)
        } catch(exception: TodoListNotFoundException) {
            throw TodoListDeletionException(AppStrings.Exceptions.TODO_LIST_DELETION_EXCEPTION + exception.message)
        }
    }

    override fun findAllTodoLists(): List<TodoList> = todoListRepository.findAll()

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