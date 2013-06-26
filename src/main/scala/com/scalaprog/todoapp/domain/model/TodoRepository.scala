package com.scalaprog.todoapp.domain.model

import java.util.UUID

/**
 * Todo Repository for handling all persistence related operations on a Todo.
 * The follows the Repository pattern see:
 * http://blog.8thlight.com/mike-ebert/2013/03/23/the-repository-pattern.html or
 * http://martinfowler.com/eaaCatalog/repository.html
 */
trait TodoRepository {
  type TodoId = UUID

  def findTodoById(id: TodoId): Todo

  def save(todo: Todo)

  def nextIdentity(): TodoId

}