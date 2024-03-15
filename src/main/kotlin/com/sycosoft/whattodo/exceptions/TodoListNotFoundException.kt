package com.sycosoft.whattodo.exceptions

class TodoListNotFoundException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}