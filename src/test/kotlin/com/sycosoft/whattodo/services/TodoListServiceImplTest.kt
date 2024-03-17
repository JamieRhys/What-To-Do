package com.sycosoft.whattodo.services

import com.sycosoft.whattodo.AppStrings
import com.sycosoft.whattodo.database.entities.TodoList
import com.sycosoft.whattodo.database.repositories.TodoListRepository
import com.sycosoft.whattodo.exceptions.TodoListDeletionException
import com.sycosoft.whattodo.exceptions.TodoListInvalidObjectException
import com.sycosoft.whattodo.exceptions.TodoListNotFoundException
import com.sycosoft.whattodo.exceptions.TodoListUpdateException
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
        assertEquals(AppStrings.Exceptions.TODO_LIST_INVALID_OBJECT_EXCEPTION + "Must have a name.", exception.message)
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

    @Test
    fun whenValidNameChangeGiven_thenUpdateTodoList() {
        val test = todoList
        test.name = AppStrings.Testing.TodoList.TODO_LIST_UPDATED_NAME
        `when`(repository.save(test)).thenReturn(test)

        val updatedTodoList = todoListService.updateTodoList(test)

        verify(repository, atLeastOnce()).save(updatedTodoList)
        assertEquals(AppStrings.Testing.TodoList.TODO_LIST_UPDATED_NAME, updatedTodoList.name)
    }

    @Test
    fun whenInvalidUUIDGivenToUpdateTodoList_thenThrowException() {
        val invalidUUID = UUID.randomUUID()
        val testTodoList = TodoList.Builder()
            .uuid(invalidUUID)
            .name(AppStrings.Testing.TodoList.TODO_LIST_UPDATED_NAME)
            .build()

        val exception = assertThrows<TodoListUpdateException> { todoListService.updateTodoList(testTodoList) }

        verify(repository, never()).save(testTodoList)
        assertEquals(AppStrings.Exceptions.TODO_LIST_UPDATE_EXCEPTION + AppStrings.Exceptions.TODO_LIST_NOT_FOUND_EXCEPTION + invalidUUID, exception.message)
    }

    @Test
    fun whenNoChangeGiven_thenTodoListNotUpdated() {
        `when`(repository.save(todoList)).thenReturn(todoList)

        val exception = assertThrows<TodoListUpdateException> { todoListService.updateTodoList(todoList) }

        verify(repository, never()).save(todoList)
        assertEquals(AppStrings.Exceptions.TODO_LIST_UPDATE_EXCEPTION + "Nothing to update", exception.message)
    }

    @Test
    fun whenValidTodoListUUIDGiven_thenDeleteTodoList() {
        todoList.uuid?.let { todoListService.deleteTodoList(it) }

        verify(repository, atLeastOnce()).delete(todoList)
    }

    @Test
    fun whenInvalidTodoListUUIDGiven_thenThrowException() {
        val invalidUUID = UUID.randomUUID()

        val exception = assertThrows<TodoListDeletionException> { todoListService.deleteTodoList(invalidUUID) }

        verify(repository, never()).deleteById(invalidUUID)
        assertEquals(AppStrings.Exceptions.TODO_LIST_DELETION_EXCEPTION + AppStrings.Exceptions.TODO_LIST_NOT_FOUND_EXCEPTION + invalidUUID, exception.message)
    }

    @Test
    fun whenValidTodoListNameGiven_thenDeleteTodoList() {
        todoListService.deleteTodoList(todoList.name)

        verify(repository, atLeastOnce()).delete(todoList)
    }

    @Test
    fun whenInvalidTodoListNameGiven_thenThrowException() {
        val exception = assertThrows<TodoListDeletionException> { todoListService.deleteTodoList(AppStrings.Testing.TodoList.TODO_LIST_INVALID_NAME) }

        verify(repository, never()).delete(todoList)
        assertEquals(AppStrings.Exceptions.TODO_LIST_DELETION_EXCEPTION + AppStrings.Exceptions.TODO_LIST_NOT_FOUND_EXCEPTION + AppStrings.Testing.TodoList.TODO_LIST_INVALID_NAME, exception.message)
    }
}