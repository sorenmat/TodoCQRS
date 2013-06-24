package com.scalaprog.todoapp.domain.model

import java.util.UUID

trait TodoRepository {
  type TodoId = UUID

  def findTodoById(id: TodoId): Todo

  def save(todo: Todo)

  def nextIdentity(): TodoId

}