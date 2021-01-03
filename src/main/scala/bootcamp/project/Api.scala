//package bootcamp.project
//
//import akka.actor.typed.ActorSystem
//import akka.actor.typed.scaladsl.Behaviors
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
//import akka.http.scaladsl.server.Route
//import akka.http.scaladsl.server.Directives._
//import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
//import doobie.implicits._
//
//case class Userr(username: String, password: String, email: String)
//
//object Api extends FailFastCirceSupport {
//  import io.circe.generic.auto._
//
//  implicit val system = ActorSystem(Behaviors.empty, "AkkaHttpJson")
//
//  val route: Route = (path("api" / "user" / "create") & post) {
//    entity(as[Userr]) { Userr =>
//      DoobieDBConfig.testSql
//      complete("Yes?")
//    }
//  }
//
//  def main(args: Array[String]): Unit = {
//    Http().newServerAt("localhost", 8081).bind(route)
//  }
//}
