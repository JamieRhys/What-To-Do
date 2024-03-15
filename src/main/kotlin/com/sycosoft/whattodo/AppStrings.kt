package com.sycosoft.whattodo

class AppStrings {
    class Exceptions {
        companion object {
            const val TODO_LIST_NOT_FOUND_EXCEPTION: String = "Todo List not available with UUID of "
            const val TODO_LIST_INVALID_OBJECT_EXCEPTION: String = "Invalid Todo List Object"
        }
    }
    class Testing {
        class TodoList {
            companion object {
                const val TODO_LIST_VALID_NAME: String = "Todo List"
                const val TODO_LIST_INVALID_NAME: String = "Tdo List"
            }
        }
    }
}