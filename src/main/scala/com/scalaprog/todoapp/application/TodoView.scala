package com.scalaprog.todoapp.application

import com.scalaprog.todoapp.port.adapter.eventbus.EventListener
import com.scalaprog.todoapp.domain.common.AbstractEvent
import com.scalaprog.todoapp.domain.model.event.TodoCreated
import java.util.Date

/**
 * User: soren
 */
class TodoView extends EventListener {
  var todos = List[Todo]()

  def eventOccurred(event: AbstractEvent) {
    event match {
      case t: TodoCreated => todos = Todo(t.name, t.dueDate) :: todos
    }
  }
}

case class Todo(name: String, dueDate: Date)