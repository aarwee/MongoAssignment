import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._

/**
  * Created by knoldus on 11/4/16.
  */
class LoginControllerTest  extends Specification{



  "Login" should {
    "shoud render the signup page" in new WithApplication{
      val res = route(FakeRequest(GET, "/")).get
      status(res) must equalTo(200)

    }
    "shoud render the signup page" in new WithApplication{
      val res = route(FakeRequest(GET, "/login")).get
      status(res) must equalTo(200)

    }

    "valid id password" in new WithApplication{
      val home = route(FakeRequest(POST, "/dashboard").withFormUrlEncodedBody("email" -> "rishabh@gmail.com", "password" -> "1992")).get
      status(home) must equalTo(200)
    }

    "invalid form submission" in new WithApplication() {
      val input = route(FakeRequest(POST, "/dashboard").withFormUrlEncodedBody("email" -> "abc", "password" -> "abc","name"->"aarwee","mobile"->"0000")).get
      status(input) must equalTo(303)
    }
  }

}
