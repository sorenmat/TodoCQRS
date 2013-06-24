package com.scalaprog.todoapp.domain.model.event

import com.scalaprog.todoapp.domain.common.AbstractEvent
import java.util.Date

case class TaskCreated(val name: String, val dueDate: Date) extends AbstractEvent {

}