package net.virtualvoid.akka.graal

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

import scala.util.Failure
import scala.util.Success

object TestApp {
  def main(args: Array[String]): Unit = {
    println("Before creation")
    implicit val system = ActorSystem()
    import system.dispatcher
    println("After creation")
    //println(system.settings.config)

    class TestActor extends Actor {
      override def receive: Receive = {
        case "ping" =>
          println("Got ping")
          sender() ! "pong"
      }
    }

    val a = system.actorOf(Props(new TestActor))
    a ! "ping"

    implicit val mat = ActorMaterializer()
    Source.fromIterator(() => Iterator.from(1))
      .take(1000)
      .runFold(0)(_ + _)
      .onComplete { res =>
        println(s"Fold result is $res")
      }

    // server
    import akka.http.scaladsl.server.Directives._
    val route = get {
      complete("Hello World")
    }

    Http().bindAndHandle(route, "0.0.0.0", 8080)
      .onComplete { res =>
        println(s"Binding result is $res")
      }

    // client
    Http().singleRequest(HttpRequest(uri = "http://google.com"))
      .onComplete {
        case Success(res) =>
          println(s"Result of request is $res")
        case Failure(e) =>
          println(s"Request failed with [$e]")
          e.printStackTrace()
      }

    /*println("Sleeping")
    Thread.sleep(5000)
    println(s"Terminating... ${Thread.currentThread().isDaemon}")
    system.terminate().onComplete { s =>
      println(s"Termination completed with $s")
      sys.exit(0)
    }*/
  }
}
