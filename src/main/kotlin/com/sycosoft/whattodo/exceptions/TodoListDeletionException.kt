package com.sycosoft.whattodo.exceptions

class TodoListDeletionException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}