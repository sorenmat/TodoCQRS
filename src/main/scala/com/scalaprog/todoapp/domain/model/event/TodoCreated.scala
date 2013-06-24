package com.scalaprog.todoapp.domain.model.event

import com.scalaprog.todoapp.domain.common.AbstractEvent
import java.util.Date

case class TodoCreated(name: String, dueDate: Date) extends AbstractEvent