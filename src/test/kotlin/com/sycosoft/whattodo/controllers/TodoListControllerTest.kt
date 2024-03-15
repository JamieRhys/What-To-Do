package com.sycosoft.whattodo.controllers

import com.sycosoft.whattodo.AppStrings
import com.sycosoft.whattodo.database.entities.TodoList
import com.sycosoft.whattodo.services.todolist.TodoListService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
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
        val todoList = TodoList.Builder().uuid(testUUID).name(AppStrings.Testing.TodoList.TODO_LIST_VALID_NAME).build()
    }

    @Test
    fun saveDepartment() {
        val testList = TodoList.Builder().name(AppStrings.Testing.TodoList.TODO_LIST_VALID_NAME).build()
        //Mockito.`when`(todoListService.saveTodoList())
    }
}