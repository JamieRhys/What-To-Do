package com.sycosoft.whattodo.services

import com.sycosoft.whattodo.AppStrings
import com.sycosoft.whattodo.database.entities.TodoList
import com.sycosoft.whattodo.database.repositories.TodoListRepository
import com.sycosoft.whattodo.exceptions.TodoListInvalidObjectException
import com.sycosoft.whattodo.exceptions.TodoListNotFoundException
import com.sycosoft.whattodo.services.todolist.TodoListService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
class TodoListServiceImplTest {

    @Autowired
    private lateinit var todoListService: TodoListService

    @MockBean
    private lateinit var  repository: TodoListRepository

    private val testUUID = UUID.randomUUID()
    private lateinit var todoList: TodoList

    @BeforeEach
    fun setUp() {
        todoList = TodoList.Builder().uuid(testUUID).name(AppStrings.Testing.TodoList.TODO_LIST_VALID_NAME).build()

        `when`(repository.findById(testUUID)).thenReturn(Optional.of(todoList))
        `when`(repository.findTodoListByName(todoList.name)).thenReturn(Optional.of(todoList))
    }

    @Test
    fun whenValidTodoListSaved_thenTodoListIsSaved() {
        val test = TodoList.Builder().name(AppStrings.Testing.TodoList.TODO_LIST_VALID_NAME).build()
        `when`(repository.save(test)).thenReturn(todoList)
        val savedTodoList = todoListService.saveTodoList(test)

        verify(repository).save(test)
        assertNotNull(savedTodoList)
        assertEquals(savedTodoList.uuid, testUUID)
    }

    @Test
    fun whenInvalidTodoListSaved_thenThrowException() {
        val invalidTodoList = TodoList.Builder().build()
        val exception = assertThrows<TodoListInvalidObjectException> { todoListService.saveTodoList(invalidTodoList) }

        verify(repository, never()).save(invalidTodoList)
        assertEquals(AppStrings.Exceptions.TODO_LIST_INVALID_OBJECT_EXCEPTION, exception.message)
    }

    @Test
    fun whenValidTodoListUUIDProvided_thenTodoListFound() {
        val found = todoListService.findTodoListByUUID(testUUID)

        assertEquals(found.uuid, testUUID)
    }

    @Test
    fun whenInvalidTodoListUUIDProvided_thenThrowTodoListNotFoundException() {
        val invalidUUID = UUID.randomUUID()

        val exception = assertThrows<TodoListNotFoundException> { todoListService.findTodoListByUUID(invalidUUID) }

        assertEquals(AppStrings.Exceptions.TODO_LIST_NOT_FOUND_EXCEPTION + invalidUUID, exception.message)
    }

    @Test
    fun whenValidTodoListNameProvided_thenTodoListFound() {
        val found = todoListService.findTodoListByName(AppStrings.Testing.TodoList.TODO_LIST_VALID_NAME)

        assertEquals(found.name, AppStrings.Testing.TodoList.TODO_LIST_VALID_NAME)
    }

    @Test
    fun whenInvalidTodoListNameProvided_thenThrowTodoListNotFoundException() {
        val exception = assertThrows<TodoListNotFoundException> { todoListService.findTodoListByName(AppStrings.Testing.TodoList.TODO_LIST_INVALID_NAME) }

        assertEquals(AppStrings.Exceptions.TODO_LIST_NOT_FOUND_EXCEPTION + AppStrings.Testing.TodoList.TODO_LIST_INVALID_NAME, exception.message)
    }
}