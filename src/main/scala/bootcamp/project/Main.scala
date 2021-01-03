package bootcamp.project

import pureconfig.ConfigSource
import pureconfig.generic.auto._

object Main {

  lazy val config: Config = ConfigSource.default.loadOrThrow[Config]

  def main(args: Array[String]): Unit = {
    val db: DBconf = new DBconf(config)
    val userDao: UserDao = new UserDao(db)

    val userOne: User = userDao.getUser(1) match {
      case Some(value) => value
      case None => User(0, "EMPTY", "EMPTY", "EMPTY")
    }

    println(userOne.id + " " + userOne.username + " " + userOne.password + " " + userOne.email)
    println()

    val users: List[User] = userDao.getAllUsers()

    users.foreach(user =>
      println(user.id + " " + user.username + " " + user.password + " " + user.email)
    )


    val userTwo: User = userDao.createUser("chuvaks", "stronkPaswort", "majl")
    println()
    println(userTwo.id + " " + userTwo.username + " " + userTwo.password + " " + userTwo.email)
  }
}
