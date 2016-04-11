import org.specs2.mutable.Specification


import play.api.test._
import play.api.test.Helpers._

/**
  * Created by knoldus on 11/4/16.
  */
class SignupControllerTest extends Specification{


  "Signup" should {
    "shoud render the signup page" in new WithApplication{
     val res = route(FakeRequest(GET, "/render")).get
      status(res) must equalTo(200)

    }

    "User already exist" in new WithApplication{
      val home = route(FakeRequest(POST, "/signup").withFormUrlEncodedBody("email" -> "rishabh@gmail.com", "password" -> "1992","name"->"rishabh","mobile"->"9999")).get
      status(home) must equalTo(303)
    }

    "valid form submission" in new WithApplication() {
      val input = route(FakeRequest(POST, "/signup").withFormUrlEncodedBody("email" -> "rishabh@yahoo.com", "password" -> "rishabh","name"->"aarwee","mobile"->"0000")).get
      status(input) must equalTo(303)
    }
  }
}

