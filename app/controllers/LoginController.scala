package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import services.dbConnection._
import scala.concurrent.duration._
import scala.concurrent.Await
import play.api.i18n.Messages.Implicits._
import play.api.Play.current
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await

/**
  * Created by knoldus on 11/4/16.
  */
class LoginController extends Controller {
  val loginForm = Form(
    tuple(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )
  )

  def show:Action[AnyContent] = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def validate:Action[AnyContent] = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      failure => {
        BadRequest(views.html.login(failure))
      },
      success => {
        val collection = db.collection[BSONCollection]("userinfo")
        val bson = BSONDocument("email" -> success._1, "password" -> success._2)
        val user = Await.result(collection.find(bson).one[BSONDocument], 5.seconds)
        if (user.isDefined) {
          Ok(views.html.dashboard(success._1))
        }
        else {
          Redirect(routes.LoginController.show).flashing("error" -> "wrong username or password")
        }
      }
    )
  }
}
