package bootcamp.project

import cats._
import cats.effect._
import cats.implicits._
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import doobie._
import doobie.hikari.HikariTransactor
import doobie.implicits._
//import doobie.util.transactor.Transactor
import doobie.free.Types
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.ExecutionContext

object DoobieDBConfig {
//  implicit val cs:ContextShift[IO] = IO.contextShift(ExecutionContexts.synchronous)
//  val xa = Transactor.fromDriverManager[IO](
//    "org.postgresql.Driver",
//    "jdbc:postgresql://192.168.99.100:5432/course-project",
//    "postgres",
//    "000000"
//  )

//  val mxa = Transactor.fromDriverManager[Task](
//    "org.postgresql.Driver",
//    "jdbc:postgresql://192.168.99.100:5432/course-project",
//    "postgres",
//    "000000"
//  )

  val testSql: ConnectionIO[Unit] =
    sql"insert into users (username, password, email) values ('admin', 'admin', 'admin@m2.net')".stripMargin.update.run.void




  val transactor: Resource[Task, Transactor[Task]] = {
    for {
      ce <- ExecutionContexts.fixedThreadPool[Task](32)
      be <- ExecutionContexts.cachedThreadPool[Task]
      xa <- HikariTransactor.newHikariTransactor[Task](
        "org.postgresql.Driver",
        "jdbc:postgresql://192.168.99.100:5432/course-project",
        "postgres",
        "000000",
        ce,
        Blocker.liftExecutionContext(be)
      )
      //      _ <-Resource.liftF(conn)
    } yield xa
  }




  private def testConnection(xa: Transactor[Task]): Task[Unit] =
    Task {
      sql"select 1".query[Int].unique.transact(xa)
    }.void


//  private def connectTest(xa: Transactor[Task]): Task[Unit] = {
//
//  }

  def main(args: Array[String]): Unit = {
//    testSql.transact(mxa).runAsyncAndForget
    transactor.use(testSql.transact[Task]).runSyncUnsafe()
  }
}
