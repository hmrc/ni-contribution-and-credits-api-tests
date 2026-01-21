package uk.gov.hmrc.api.models.common

import play.api.libs.json.{Json, OFormat}

case class OfficeDetails(
    officeLocationDecode: Option[Int],
    officeLocationValue: Option[String],
    officeIdentifier: Option[String]
)

object OfficeDetails {
  implicit val format: OFormat[OfficeDetails] = Json.format
}
