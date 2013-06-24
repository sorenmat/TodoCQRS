package com.scalaprog.todoapp.domain.model

import java.util.Date

class Task(name: String, var dueDate: Date) {
  var completed = false

  def changeDueDate(aNewDate: Date) {
    dueDate = aNewDate;
  }

  def markTaskAsCompleted() {
    completed = true
  }

  def markTaskAsNotCompleted() {
    completed = false
  }

}