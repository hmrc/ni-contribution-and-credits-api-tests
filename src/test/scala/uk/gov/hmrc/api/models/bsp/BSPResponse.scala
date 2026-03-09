package uk.gov.hmrc.api.models.bsp

import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.api.models.esajsa.NIContributionsAndCreditsResult
import uk.gov.hmrc.api.models.filteredmarriagedetails.FilteredMarriageDetails

case class BSPResponse(
    benefitType: String,
    nationalInsuranceNumber: String,
    niContributionsAndCreditsResult: NIContributionsAndCreditsResult,
    marriageDetailsResult: FilteredMarriageDetails
)

object BSPResponse {
  implicit val format: Format[BSPResponse] = Json.format[BSPResponse]
}
