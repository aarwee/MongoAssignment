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

class SignupController extends Controller {

  val signupForm = Form(
    tuple(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText,
      "name" -> nonEmptyText,
      "mobile" -> nonEmptyText
    )
  )

  def show:Action[AnyContent] = Action { implicit request =>
    Ok(views.html.signup(signupForm))
  }

  def signup:Action[AnyContent] = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      failure => BadRequest(views.html.signup(failure)),
      success => {
        val collection = db.collection[BSONCollection]("userinfo")
        val bson = BSONDocument("email" -> success._1)
        val user = Await.result(collection.find(bson).one[BSONDocument], 5.seconds)
        if (user.isDefined) {
          Redirect(routes.SignupController.show).flashing("error" -> "username already exists")
        }
        else {
          val bson = BSONDocument("email" -> success._1, "password" -> success._2, "name" -> success._3, "mobile" -> success._4)
          collection.insert(bson)
          Redirect(routes.LoginController.show).flashing("success" -> "Account created")

        }
      }
    )

  }
}
