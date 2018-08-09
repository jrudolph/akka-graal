package net.virtualvoid.akka.graal

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

object TestApp {
  def main(args: Array[String]): Unit = {
    println("Before creation")
    val system = ActorSystem()
    import system.dispatcher
    println("After creation")
    println(system.settings.config)

    class TestActor extends Actor {
      override def receive: Receive = {
        case "ping" =>
          println("Got ping")
          sender() ! "pong"
      }
    }

    val a = system.actorOf(Props(new TestActor))
    a ! "ping"

    println("Sleeping")
    Thread.sleep(1000)
    println(s"Terminating... ${Thread.currentThread().isDaemon}")
    system.terminate().onComplete { s =>
      println(s"Termination completed with $s")
      sys.exit(0)
    }
  }
}
