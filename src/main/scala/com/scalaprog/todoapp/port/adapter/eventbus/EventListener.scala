package com.scalaprog.todoapp.port.adapter.eventbus

import com.scalaprog.todoapp.domain.common.AbstractEvent

/**
 * User: soren
 */
trait EventListener {

  def eventOccurred(event: AbstractEvent)

}
