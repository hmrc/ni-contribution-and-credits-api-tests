package uk.gov.hmrc.api.models.ltb

import play.api.libs.json.{Json, OFormat}

final case class LongTermBenefitNotesResponse(
    longTermBenefitNotes: Seq[String]
)

object LongTermBenefitNotesResponse {

  implicit val format: OFormat[LongTermBenefitNotesResponse] =
    Json.format[LongTermBenefitNotesResponse]

}
