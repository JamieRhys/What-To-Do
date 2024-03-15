package com.sycosoft.whattodo.exceptions

class TodoListInvalidObjectException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}