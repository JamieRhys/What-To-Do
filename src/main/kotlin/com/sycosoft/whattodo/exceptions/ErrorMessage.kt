package com.sycosoft.whattodo.exceptions

import org.springframework.http.HttpStatus

data class ErrorMessage(
    val status: HttpStatus,
    val message: String
)