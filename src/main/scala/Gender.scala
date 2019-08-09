import play.api.libs.json.{JsResult, JsSuccess, JsValue, Reads}


object Gender extends Enumeration {
  type Gender = GenderDetail

  val MALE = GenderDetail("MALE")
  val FEMALE = GenderDetail("FEMALE")

  // list of the enum values, used for matching
  val genders = List(MALE, FEMALE)

  // an object that extends the Enumeration.Val type. Allows the use of complex enum values
  protected case class GenderDetail(name: String) extends super.Val

  // implicit function to find the gender based on an input string
  implicit def toGender(name: String): Gender = genders.find(_.name == name).get

  // Custom JSON READs function that will take a JSON string and convert it into a Gender Enum type
  implicit val reads: Reads[Gender] = new Reads[Gender] {
    override def reads(json: JsValue): JsResult[Gender] = {
      JsSuccess(toGender(json.as[String]))
    }
  }
}

