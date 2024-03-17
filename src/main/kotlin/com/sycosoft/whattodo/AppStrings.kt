package com.sycosoft.whattodo

class AppStrings {
    class Exceptions {
        companion object {
            const val TODO_LIST_NOT_FOUND_EXCEPTION: String = "Todo List not available with UUID of "
            const val TODO_LIST_INVALID_OBJECT_EXCEPTION: String = "Invalid Todo List Object. Reason: "
            const val TODO_LIST_UPDATE_EXCEPTION: String = "Unable to update Todo List. Reason: "
            const val TODO_LIST_DELETION_EXCEPTION: String = "Unable to delete Todo List. Reason: "
        }
    }
    class Testing {
        class TodoList {
            companion object {
                const val TODO_LIST_VALID_NAME: String = "Todo List"
                const val TODO_LIST_INVALID_NAME: String = "Tdo List"
                const val TODO_LIST_UPDATED_NAME: String = "Test Todo List"
                const val TODO_LIST_UPDATE_JSON: String = "{\"name\":\"Test List\"}"
            }
        }
    }
}