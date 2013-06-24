package com.scalaprog.todoapp.domain.model

import java.util.Date
import com.scalaprog.todoapp.domain.model.event.TaskCreated
import com.scalaprog.todoapp.domain.common.AggregateRoot
import java.util.UUID
import com.scalaprog.todoapp.domain.common.AbstractEvent
import com.scalaprog.todoapp.domain.model.event.TodoCreated

class Todo(id: UUID) extends AggregateRoot(id) {

  var tasks = List[Task]()
  var name: String = null
  var dueDate: Date = null

  def this(name: String, dueDate: Date) {
    this(UUID.randomUUID())
    this.name = name
    this.dueDate = dueDate
  }

  def createTodo(name: String, dueDate: Date) {
    require(name != null);
    applyEvent(TodoCreated(name, dueDate), true)
  }

  def createTask(name: String, dueDate: Date) {
    require(dueDate.before(this.dueDate) || dueDate.equals(this.dueDate))
    applyEvent(TaskCreated(name, dueDate), true)
  }

  protected def isTodoCompleted = tasks.filter(task => task.completed).isEmpty

  override def applyEvent(event: AbstractEvent, storeEvents: Boolean) {
    event match {
      case TodoCreated(name, dueDate) =>
        this.name = name; this.dueDate = dueDate
      case TaskCreated(name, dueDate) => tasks = new Task(name, dueDate) :: tasks
    }
    if (storeEvents)
      unCommitedEvent = event :: unCommitedEvent // append event to the list of uncommited events
  }

}