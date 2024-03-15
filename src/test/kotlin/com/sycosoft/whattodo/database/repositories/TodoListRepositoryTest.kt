package com.sycosoft.whattodo.database.repositories

import com.sycosoft.whattodo.AppStrings
import com.sycosoft.whattodo.database.entities.TodoList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles
import java.util.*

@DataJpaTest
class TodoListRepositoryTest {
    @Autowired
    private lateinit var repository: TodoListRepository

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var environment: Environment

    private val testUUID = UUID.randomUUID()

    @BeforeEach
    fun setUp() {
        val todoList = TodoList.Builder().uuid(testUUID).name(AppStrings.Testing.TodoList.TODO_LIST_VALID_NAME).build()

        entityManager.merge(todoList)

        println("Active Profiles: ${environment.activeProfiles.joinToString()}")
    }

    @Test
    fun whenGivenValidTodoListUUID_thenReturnTodoList() {
        val todoList = repository.findById(testUUID).get()

        assertEquals(todoList.uuid, testUUID)
    }

    @Test
    fun whenGivenInvalidTodoListUUID_thenEntityIsNotPresent() {
        val invalidUUID = UUID.randomUUID()

        val todoList = repository.findById(invalidUUID)

        assertEquals(todoList.isPresent, false)
    }
}