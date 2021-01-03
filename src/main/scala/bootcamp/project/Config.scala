package bootcamp.project

case class Config(db: DbConfig)

case class DbConfig(username: String, password: String, url: String, driver: String, threadPoolSize: Int)