package net.virtualvoid.akka.graal

import java.util.concurrent.ThreadFactory

import akka.actor.Cancellable
import akka.actor.Scheduler
import akka.event.LoggingAdapter
import com.typesafe.config.Config

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

class NoScheduler(
    config:        Config,
    log:           LoggingAdapter,
    threadFactory: ThreadFactory
) extends Scheduler {
  override def schedule(initialDelay: FiniteDuration, interval: FiniteDuration, runnable: Runnable)(implicit executor: ExecutionContext): Cancellable = new Cancellable {
    override def cancel(): Boolean = false

    override def isCancelled: Boolean = false
  }
  override def scheduleOnce(delay: FiniteDuration, runnable: Runnable)(implicit executor: ExecutionContext): Cancellable = new Cancellable {
    override def cancel(): Boolean = false

    override def isCancelled: Boolean = false
  }
  override def maxFrequency: Double = 1
}
