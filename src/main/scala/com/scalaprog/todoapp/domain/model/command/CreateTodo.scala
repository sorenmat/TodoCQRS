package com.scalaprog.todoapp.domain.model.command

import java.util.{UUID, Date}

/**
 * User: soren
 */
case class CreateTodo(id: UUID, name: String, dueDate: Date)
