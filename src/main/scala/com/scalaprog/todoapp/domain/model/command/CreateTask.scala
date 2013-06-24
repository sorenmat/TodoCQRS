package com.scalaprog.todoapp.domain.model.command

import java.util.{Date, UUID}

/**
 * User: soren
 */
case class CreateTask(todoId: UUID, name: String, dueDate: Date)
