import play.api.libs.json.{Json, JsonConfiguration}
import play.api.libs.json.JsonNaming.SnakeCase

/**
 * RunTestJson - test code for scala JSON parsing from a string
 * The code below proves (probably) that you can implicitly cast properly formatted JSON strings into a Scala object
 * by using the play JSON library. The code below takes a string representatino of a JSON body and without any manual
 * parsing converts it into a "complicated" Scala case class object.
 */
class RunTestJson {

  // case class for the inner objects to be parsed from a JSON string
  case class TestJson(name: String, age: Int, stat: Option[Double], dec: BigDecimal)

  // since the JSON we're testing with is a list of JSON objects, this case class will represent that as a list of
  // the expected case class (yo dawg, I heard you like case classes...)
  case class JsonResp (ret: List[TestJson])

  // the companion object for the inner JSON object. Defines how to parse a JSON string into this object
  object TestJson {
    implicit val config = JsonConfiguration(SnakeCase)
    implicit val testJsonFormat = Json.format[TestJson]
  }

  // the companion object for the collection of JSON objects. NOTE: It is absolutely necessary that this is defined
  // !!!--- AFTER ---!!! the inner objects are defined, as the compiler will complain otherwise
  object JsonResp {
    implicit val config = JsonConfiguration(SnakeCase)
    implicit val jsonRespJsonFormat = Json.format[JsonResp]
  }

  // make a reasonably complex JSON string (list of JSON objects)
  // NOTE: The names absolutely MUST MATCH otherwise all hell will break loose (RTE)
  // NOTE: an Option type will be parsed as None if the JSON string being parsed does not define the field.
  //        In the string below, the first object will be parsed as TestJson(fubar, 123, None)
  val body =
  """{"ret": [{ "name" : "fubar", "age" : 123, "dec" : 1.23456789 }, { "name" : "barfu", "age" : 456 , "stat"
    |: 2.718281828 , "dec" : 1.23456789 } ]
    |}""".stripMargin
  // val body = """{ "name" : "fubar", "age" : 123 }"""

  // Scala can tell that this is a JSON formatted string, so it'll create a generic JSON object tree out of said string
  val jsonBody = Json.parse(body)

  // Here's where the magic happens. Scala will map the derived object structure to the specified case class in the
  // as[T] statement. This will result in some ezpz JSON string to Scala object conversion
  val obj = jsonBody.as[JsonResp]
  // val obj = jsonBody.as[TestJson]

  // Print the object to verify our work...
  println(obj)

  // iterate over the list of TestJson objects in the JsonResp object and print the class
  obj.ret.map(ent => println(ent.getClass))

}