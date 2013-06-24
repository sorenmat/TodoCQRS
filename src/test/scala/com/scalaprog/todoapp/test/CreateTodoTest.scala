package com.scalaprog.todoapp.test

import org.scalatest.FunSuite
import com.scalaprog.todoapp.application.TodoApplicationService
import com.scalaprog.todoapp.port.adapter.persistence.EventSourcedTodoRepository
import java.util.{UUID, Date}
import java.io._
import com.google.gson.GsonBuilder
import com.scalaprog.todoapp.domain.model.command.{CreateTask, CreateTodo}

class CreateTodoTest extends FunSuite {

  test("Creating a todo via TodoApplicationService") {

    val service: TodoApplicationService = new TodoApplicationService(new EventSourcedTodoRepository)

    val todoUUID: UUID = UUID.randomUUID()
    val dueDate: Date = new Date()
    service.createTodo(CreateTodo(todoUUID, "test", dueDate))

    val foundTodo = service.findTodo(todoUUID)
    assert(foundTodo.id === todoUUID)
    assert(foundTodo.name === "test")
    assert(foundTodo.dueDate.toString === dueDate.toString)
  }

  test("Testing dates") {
    val date = new Date()

    val store = new ObjectOutputStream(new FileOutputStream(new File("foo")))
    store.writeObject(date)
    store.close
    val aDate = new ObjectInputStream(new FileInputStream(new File("foo"))).readObject().asInstanceOf[Date]
    require(date.equals(aDate))

    val gson = new GsonBuilder()
      .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
      .create();
    val anotherDate = gson.fromJson(gson.toJson(date), classOf[Date])
    require(date.equals(anotherDate))
  }

  test("Creating a task on a todo via TodoApplicationService") {

    val service: TodoApplicationService = new TodoApplicationService(new EventSourcedTodoRepository)

    val todoUUID: UUID = UUID.randomUUID()
    val dueDate: Date = new Date()

    service.createTodo(CreateTodo(todoUUID, "test", dueDate))
    val start = System.currentTimeMillis()
    for (i <- 0 to 10)
      service.createTask(CreateTask(todoUUID, "simple task " + 1, dueDate))
    val stop = System.currentTimeMillis()
    println("took: " + (stop - start))
    val foundTodo = service.findTodo(todoUUID)

    //assert(foundTodo.tasks.size === 1)
  }

}