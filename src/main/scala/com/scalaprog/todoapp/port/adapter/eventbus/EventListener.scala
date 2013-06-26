package com.scalaprog.todoapp.port.adapter.eventbus

import com.scalaprog.todoapp.domain.common.AbstractEvent

/**
 * User: soren
 */
trait EventListener {

  /**
   * A new event has been published, the implementation should handle this
   * @param event
   */
  def eventOccurred(event: AbstractEvent)

}
