package com.sycosoft.whattodo.services.todolist

import com.sycosoft.whattodo.database.entities.TodoList
import com.sycosoft.whattodo.exceptions.TodoListDeletionException
import com.sycosoft.whattodo.exceptions.TodoListInvalidObjectException
import com.sycosoft.whattodo.exceptions.TodoListNotFoundException
import com.sycosoft.whattodo.exceptions.TodoListUpdateException
import java.util.*

interface TodoListService {
    /** Saves a Todo List to the database.
     * @param todoList The Todo List to be saved to the database.
     * @return If successful, return the entity saved in the database.
     * @throws TodoListInvalidObjectException
     */
    fun saveTodoList(todoList: TodoList): TodoList

    /** Updates the given Todo List.
     * @param todoList The Todo List that needs to be updated in the database.
     * @return If successful, returns the entity that was updated in the database which also houses the new information.
     * @throws TodoListUpdateException
     */
    fun updateTodoList(todoList: TodoList): TodoList

    /** Deletes the given Todo List from the database.
     * @param todoList The Todo List to be deleted.
     */
    fun deleteTodoList(todoList: TodoList)

    /** Deletes the given Todo List from the database.
     * @param uuid The UUID of the Todo List to be deleted.
     * @throws TodoListDeletionException
     */
    fun deleteTodoList(uuid: UUID)

    /** Deletes the given Todo List from the database.
     * @param name THe name of the Todo List to be deleted.
     * @throws TodoListDeletionException
     */
    fun deleteTodoList(name: String)

    /** Finds and returns all lists held in the database.
     * @return List of all Todo Lists.
     */
    fun findAllTodoLists(): List<TodoList>

    /** Finds the Todo List with the given UUID.
     * @param uuid The UUID of the list that needs to be retrieved.
     * @return If found, this will be the Todo List.
     * @throws TodoListNotFoundException
     */
    fun findTodoListByUUID(uuid: UUID): TodoList

    /** Finds the Todo List with the given name.
     * @param name The name of the list that needs to be retrieved.
     * @return If found, this will be the Todo List.
     * @throws TodoListNotFoundException
     */
    fun findTodoListByName(name: String): TodoList
}