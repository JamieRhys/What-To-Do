package com.sycosoft.whattodo.controllers

import com.sycosoft.whattodo.AppStrings
import com.sycosoft.whattodo.database.entities.TodoList
import com.sycosoft.whattodo.exceptions.TodoListInvalidObjectException
import com.sycosoft.whattodo.services.todolist.TodoListService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.bind.annotation.RestController
import java.util.*

@WebMvcTest(TodoListController::class)
class TodoListControllerTest {
    @Autowired
    private lateinit var mock: MockMvc

    @MockBean
    private lateinit var todoListService: TodoListService

    private lateinit var todoList: TodoList
    private val testUUID = UUID.randomUUID()

    @BeforeEach
    fun setUp() {
        todoList = TodoList.Builder().uuid(testUUID).name(AppStrings.Testing.TodoList.TODO_LIST_VALID_NAME).build()
    }

    @Test
    fun whenValidObjectGivenSaveTodoList_thenReturnOkStatus() {
        val testList = TodoList.Builder().name(AppStrings.Testing.TodoList.TODO_LIST_VALID_NAME).build()
        `when`(todoListService.saveTodoList(testList)).thenReturn(todoList)

        mock.perform(MockMvcRequestBuilders.post("/api/todo-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(AppStrings.Testing.TodoList.TODO_LIST_UPDATE_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}