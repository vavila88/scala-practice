import play.api.libs.json.{JsObject, JsResult, JsString, JsSuccess, JsValue, Json, Reads, Writes}

object Contact extends Enumeration {
  type Contact = ContactDetail

  val PHONE = ContactDetail("PHONE")
  val EMAIL = ContactDetail("EMAIL")

  // list of the enum values, used for matching
  val genders = List(PHONE, EMAIL)

  // an object that extends the Enumeration.Val type. Allows the use of complex enum values
  protected case class ContactDetail(name: String) extends super.Val

//  object ContactDetail {
//    implicit val writesEnumName = Writes.enumNameWrites
//    implicit val contactDetailFormat = Json.format[ContactDetail]
//  }

  // implicit function to find the gender based on an input string
  implicit def toContact(name: String): Contact =
    genders.find(_.name == name).get

//  implicit val writesEnumName = Writes.enumNameWrites

  // Custom JSON READs function that will take a JSON string and convert it into a Contact Enum type
  implicit val reads: Reads[Contact] = new Reads[Contact] {
    override def reads(json: JsValue): JsResult[Contact] = {
      JsSuccess(toContact(json.as[String]))
    }
  }
}
