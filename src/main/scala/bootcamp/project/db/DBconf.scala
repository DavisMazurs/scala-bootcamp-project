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

class DBconf(config: Config) {
  val transactor: Resource[Task, Transactor[Task]] = {
    for {
      ce <- ExecutionContexts.fixedThreadPool[Task](config.db.threadPoolSize)
      be <- ExecutionContexts.cachedThreadPool[Task]
      xa <- HikariTransactor.newHikariTransactor[Task](
        config.db.driver,
        config.db.url,
        config.db.username,
        config.db.password,
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
}
