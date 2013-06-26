package com.scalaprog.todoapp.application

import com.scalaprog.todoapp.domain.model.TodoRepository
import java.util.Date
import com.scalaprog.todoapp.domain.model.Todo
import java.util.UUID
import com.scalaprog.todoapp.domain.model.command.{CreateTask, CreateTodo}

/**
 * Application service for handling all things related to the Todo aggregate
 * @param repository
 */
class TodoApplicationService(repository: TodoRepository) {

  type TodoId = UUID

  /**
   * Create a new Todo based on the command
   * @param cmd
   */
  def createTodo(cmd: CreateTodo) {
    val todo = new Todo(cmd.id)
    todo.createTodo(cmd.name, cmd.dueDate)
    repository.save(todo)
  }

  /**
   * Create a task on a given Todo
   * @param cmd
   */
  def createTask(cmd: CreateTask) {
    val todo: Todo = findTodo(cmd.todoId)
    todo.createTask(cmd.name, cmd.dueDate)
    repository.save(todo)
  }

  /**
   * Find a Todo in the repository using an Id
   * @param id
   * @return
   */
  def findTodo(id: TodoId) = {
    repository.findTodoById(id)
  }
}