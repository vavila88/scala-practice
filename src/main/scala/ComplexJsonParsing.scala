import play.api.libs.json.{Format, JsObject, JsResult, JsString, JsSuccess, JsValue, Json, JsonConfiguration, Reads, Writes}
import play.api.libs.json.JsonNaming.SnakeCase
import Gender.Gender
import Contact.Contact

class ComplexJsonParsing {
  // PersonProfile - contains person data
  case class PersonProfile(gender: Gender, contactInfo: Map[Contact, String])

  // Person Object, contains name, age, and a profile
  case class Person(name: String, age: Int, profile: PersonProfile)

  // class with a number of people and a list of people
  case class Payroll(numPeople: Int, personList: List[Person])

  // Object to aide in the JSON parsing
  object PersonProfile {
    implicit val config = JsonConfiguration(SnakeCase)

    implicit val customFmt = new Format[Map[Contact, String]]  {

      override def writes(map: Map[Contact, String]) ={
        JsObject( map.map(t2 => t2._1.name -> JsString(t2._2)))
      }

      override def reads(json: JsValue): JsResult[Map[Contact, String]] = {
          JsSuccess(Map(Contact.PHONE -> json.toString()))
      }
    }

    implicit val objectFormat = Json.format[PersonProfile]
//    implicit val complexJsonFormat:Format[PersonProfile] = Format(objectFormat, customFmt)
  }

  object Person {
    implicit val config = JsonConfiguration(SnakeCase)
    implicit val complexJsonFormat = Json.format[Person]
  }

  object Payroll {
    implicit val config = JsonConfiguration(SnakeCase)
    implicit val complexJsonFormat = Json.format[Payroll]
  }

  val myComplex = Payroll(
    2,
    List(
      Person(
        "me",
        123,
        PersonProfile(
          Gender.MALE,
          Map(
            Contact.PHONE -> "123-456-7890",
            Contact.EMAIL -> "me@you.com"
          )
        )
      ),
      Person(
        "you",
        456,
        PersonProfile(
          Gender.FEMALE,
          Map(
            Contact.PHONE -> "124-455-7889",
            Contact.EMAIL -> "you@me.com"
          )
        )
      )
    )
  )

  val myComplexJson = Json.toJson(myComplex)

  println(Json.prettyPrint(myComplexJson))
}

