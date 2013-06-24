package com.scalaprog.todoapp.port.adapter.persistence

import java.util.UUID
import com.scalaprog.todoapp.domain.model.TodoRepository
import com.scalaprog.todoapp.domain.model.Todo
import com.scalaprog.todoapp.domain.common.AbstractEvent

class EventSourcedTodoRepository extends EventStoreProvider with TodoRepository {

  def findTodoById(id: TodoId) = {
    val todo: Todo = new Todo(id)
    val events: List[AbstractEvent] = getEvents(id)

    todo.loadFromHistory(events)
    todo
  }

  def save(todo: Todo) {
    append(EventId(todo.id, todo.version), todo.unCommitedEvent)
    todo.unCommitedEvent = Nil // clear the uncommited events, if we reuse the Todo
  }

  def nextIdentity(): TodoId = {
    UUID.randomUUID()
  }
}