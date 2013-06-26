package com.scalaprog.todoapp.port.adapter.eventbus

import com.scalaprog.todoapp.domain.common.AbstractEvent

/**
 * Simple implementation of an EventBus
 * User: soren
 */
object EventBus {
  private var listeners = List[EventListener]()

  /**
   * Make the eventListener listen to events from this bus
   */
  def subscribe(eventListener: EventListener) {
    listeners = eventListener :: listeners
  }

  def publishEvent(event: AbstractEvent) {
    // send event to all listeners
    listeners.foreach( listener => listener.eventOccurred(event))
  }
}
