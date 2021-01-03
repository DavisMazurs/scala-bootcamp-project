package bootcamp.project

import bootcamp.project.db.BaseDao
import doobie.implicits._

class UserDao(dbConfig: DBconf) extends BaseDao(dbConfig) {

  def getUser(id: Int): Option[User] = {
    selectSingle[User](sql"SELECT * FROM users WHERE id = $id")
  }

  def getAllUsers(): List[User] = {
    selectList[User](sql"SELECT * FROM users")
  }

  def createUser(user: User): Unit = {
    insert(sql"INSERT INTO users (username, password, email) VALUES (${user.username}, ${user.password}, ${user.email})")
  }

  def createUser(username: String, password: String, email: String): User = {
    insertAndReturn[User](sql"INSERT INTO users (username, password, email) VALUES ($username, $password, $email)")
  }

}