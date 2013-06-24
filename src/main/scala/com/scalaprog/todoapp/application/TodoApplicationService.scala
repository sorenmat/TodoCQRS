package com.scalaprog.todoapp.application

import com.scalaprog.todoapp.domain.model.TodoRepository
import java.util.Date
import com.scalaprog.todoapp.domain.model.Todo
import java.util.UUID
import com.scalaprog.todoapp.domain.model.command.{CreateTask, CreateTodo}

class TodoApplicationService(repository: TodoRepository) {

  type TodoId = UUID

  def createTodo(cmd: CreateTodo) {
    val todo = new Todo(cmd.id)
    todo.createTodo(cmd.name, cmd.dueDate)
    repository.save(todo)
  }

  def createTask(cmd: CreateTask) {
    val todo: Todo = findTodo(cmd.todoId)
    todo.createTask(cmd.name, cmd.dueDate)
    repository.save(todo)
  }

  def findTodo(id: TodoId) = {
    repository.findTodoById(id)
  }
}