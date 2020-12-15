package m2.cms

import java.util.UUID

import akka.actor.{Actor, ActorLogging}
import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akka.event.Logging.LogLevel
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.RouteResult.Complete
import akka.http.scaladsl.server.directives.{DebuggingDirectives, LogEntry, LoggingMagnet}
import akka.stream.scaladsl.Framing.delimiter
import akka.stream.{ActorMaterializer, Materializer}
import akka.util.ByteString
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.Json
import io.circe.syntax.EncoderOps
import io.circe.generic.auto._

import scala.concurrent.ExecutionContext

case class User(username: String, password: String)
case class UserResponse(UUID: String, timestamp: Long)

object Application extends FailFastCirceSupport {

  implicit val system = ActorSystem(Behaviors.empty, "AkkaHttpJson")

  def route(actorSystem: ActorSystem[Something]): Route = (path("api" / "new_user") & post) {
    val response: UserResponse = UserResponse(UUID.randomUUID().toString, System.currentTimeMillis())

    extractRequestEntity { entity =>
      onComplete {
        entity
          .dataBytes
//          .via(delimiter(ByteString("\n"), 256))
          .map(_.utf8String)
          .runForeach(x => {
            actorSystem ! RequestBody(x)
            actorSystem ! ResponseBody(response.asJson.toString())
          })
      } { _ =>
        complete(response)
      }
    }
//    entity(as[User]) {
//      actorSystem ! TestingLogs("")
////      val logger: Behavior[Something] = Behaviors.receive { (c, m) =>
////
////      }
//      _ => complete(UserResponse(UUID.randomUUID().toString, System.currentTimeMillis()))
//    }
  }

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem(functionalActor(), "LogSys")
    Http().newServerAt("localhost", 8081).bind(route(actorSystem))
  }

  //Logging
  trait Something
  case class TestingLogs(msg: String) extends Something
  case class RequestBody(msg: String) extends Something
  case class ResponseBody(msg: String) extends Something

  def functionalActor(state: Int = 0): Behavior[Something] = Behaviors.receive { (context, message) =>
    message match {
      case TestingLogs(str) =>
        context.log.info(s"($state) This is a test of logs, using actors apparently: " + str)
        functionalActor(state + 1)
      case RequestBody(str) =>
        context.log.info("Request body:\n" + str)
        functionalActor(state)
      case ResponseBody(str) =>
        context.log.info("Response:\n" + str)
        functionalActor(state)
      case _ =>
        context.log.info(s"($state) This is kind of an error message")
        Behaviors.same
    }
  }
}
