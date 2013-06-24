package com.scalaprog.todoapp.domain.common

import java.util.UUID

abstract class AggregateRoot(val id: UUID) {

  // The current version of this domain root.
  // This is used to ensure concurrent modifications
  var version = 0

  var unCommitedEvent = List[AbstractEvent]()

  def applyEvent(event: AbstractEvent, storeEvent: Boolean)


  def loadFromHistory(events: List[AbstractEvent]) = {
    for (abstractEvent <- events) {
      applyEvent(abstractEvent, false)
      version = version + 1
    }
    this
  }
}