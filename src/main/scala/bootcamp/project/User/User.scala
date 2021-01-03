package bootcamp.project.user

import java.sql.Timestamp

case class User(
                 id: Int,
                 username: String,
                 password: String,
                 email: String
               )
