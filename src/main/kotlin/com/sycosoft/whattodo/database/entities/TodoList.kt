package com.sycosoft.whattodo.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import java.util.*

@Entity(name = "table_todo_lists")
data class TodoList(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    val uuid: UUID? = null,

    @Column(name = "name")
    @NotBlank(message = "Please provide a non-blank Todo List name.")
    var name: String
) {
    constructor(builder: Builder) : this(
        uuid = builder.uuid,
        name = builder.name
    )

    data class Builder(
        var uuid: UUID? = null,
        var name: String = "",
    ) {
        fun uuid(uuid: UUID?) = apply { this.uuid = uuid }
        fun name(name: String) = apply { this.name = name }
        fun build() = TodoList(this)
    }
}