package com.scalaprog.todoapp.test

import org.scalatest.FunSuite
import com.scalaprog.todoapp.application.TodoApplicationService
import com.scalaprog.todoapp.port.adapter.persistence.EventSourcedTodoRepository
import java.util.{UUID, Date}
import java.io._
import com.google.gson.GsonBuilder
import com.scalaprog.todoapp.domain.model.command.{CreateTask, CreateTodo}

class TodoVersionTestTest extends FunSuite {


  test("Creating events on todo should increment the version field") {

    val service: TodoApplicationService = new TodoApplicationService(new EventSourcedTodoRepository)

    val todoUUID: UUID = UUID.randomUUID()
    val dueDate: Date = new Date()
    service.createTodo(CreateTodo(todoUUID, "test", dueDate))

    for (i <- 0 until 10)
      service.createTask(CreateTask(todoUUID, "simple task " + i, dueDate))

    val foundTodo = service.findTodo(todoUUID)

    assert(foundTodo.version === 11) // create todo event + 1000 tasks events
  }

}