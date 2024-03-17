package com.sycosoft.whattodo.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@ResponseStatus
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(TodoListNotFoundException::class)
    fun todoListNotFoundException(exception: TodoListNotFoundException, request: WebRequest) : ResponseEntity<ErrorMessage> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage(HttpStatus.NOT_FOUND, exception.message!!))

    @ExceptionHandler(TodoListInvalidObjectException::class)
    fun todoListInvalidObjectException(exception: TodoListInvalidObjectException, request: WebRequest) : ResponseEntity<ErrorMessage> =
        ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ErrorMessage(HttpStatus.NOT_ACCEPTABLE, exception.message!!))
}