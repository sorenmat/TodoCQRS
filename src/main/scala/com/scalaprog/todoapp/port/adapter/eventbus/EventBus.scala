package com.scalaprog.todoapp.port.adapter.eventbus

import com.scalaprog.todoapp.domain.common.AbstractEvent

/**
 * User: soren
 */
object EventBus {
  var listeners = List[EventListener]()

  def subscribe(eventListener: EventListener) {
    listeners = eventListener :: listeners
  }

  def publishEvent(event: AbstractEvent) {
    // send event to all listeners
    listeners.foreach( listener => listener.eventOccurred(event))
  }
}
