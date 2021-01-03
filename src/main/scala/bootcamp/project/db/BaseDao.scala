package bootcamp.project.db

import bootcamp.project.DBconf
import cats._
import cats.implicits._
import doobie._
import doobie.implicits._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

class BaseDao(dbConfig: DBconf) {

  protected def selectSingle[T](sqlQuery: Fragment)(implicit read: Read[T]): Option[T] = {
    dbConfig.transactor.use(sqlQuery.query[T].option.transact[Task]).runSyncUnsafe()
  }

  protected def selectList[T](sqlQuery: Fragment)(implicit read: Read[T]) = {
    dbConfig.transactor.use(sqlQuery.query[T].to[List].transact[Task]).runSyncUnsafe()
  }

  protected def insert(sqlQuery: Fragment) = {
    noReturnValue(sqlQuery)
  }

  protected def insertAndReturn[T](sqlQuery: Fragment)(implicit read: Read[T]) = {
    dbConfig.transactor.use(
      (for {
        _ <- sqlQuery.update.run
        id <- sql"SELECT lastval()".query[Int].unique
        e <- sql"SELECT * FROM users WHERE id = $id".query[T].unique
      } yield e).transact[Task]
    ).runSyncUnsafe()
  }

  protected def update(sqlQuery: Fragment) = {
    noReturnValue(sqlQuery)
  }

  protected def delete(sqlQuery: Fragment) = {
    noReturnValue(sqlQuery)
  }

  private def noReturnValue(sqlQuery: Fragment) = {
    dbConfig.transactor.use(sqlQuery.stripMargin.update.run.void.transact[Task]).runSyncUnsafe()
  }
}
