package com.scalaprog.todoapp.test

import org.scalatest.FunSuite
import com.scalaprog.todoapp.application.{TodoView, TodoApplicationService}
import com.scalaprog.todoapp.port.adapter.persistence.EventSourcedTodoRepository
import java.util.{UUID, Date}
import java.io._
import com.google.gson.GsonBuilder
import com.scalaprog.todoapp.domain.model.command.{CreateTask, CreateTodo}
import com.scalaprog.todoapp.port.adapter.eventbus.EventBus

class TodoViewTest extends FunSuite {

  test("Creating a todo should update the todo view") {
    val view = new TodoView()
    EventBus.subscribe(view)

    val service: TodoApplicationService = new TodoApplicationService(new EventSourcedTodoRepository)

    val todoUUID: UUID = UUID.randomUUID()
    val dueDate: Date = new Date()
    service.createTodo(CreateTodo(todoUUID, "test", dueDate))

    assert(view.todos.size === 1)
    println(view.todos.mkString("\n"))
  }
}