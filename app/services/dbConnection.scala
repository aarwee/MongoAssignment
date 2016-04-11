package services

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by knoldus on 11/4/16.
  */

class dbConnection {
  val driver = new reactivemongo.api.MongoDriver
  val connection = driver.connection(List("localhost"))
  val db = Await.result(connection.database("user"), 5.seconds)
}

object dbConnection extends dbConnection
