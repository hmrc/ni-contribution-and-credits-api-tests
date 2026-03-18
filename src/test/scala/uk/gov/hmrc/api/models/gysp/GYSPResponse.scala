/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.api.models.gysp

import play.api.libs.json.{Format, Json, OFormat, Writes}
import uk.gov.hmrc.api.models.esajsa.NIContributionsAndCreditsResult

import java.time.LocalDate

case class FilteredMarriageDetailsItem(
    status: String,
    startDate: Option[LocalDate],
    startDateStatus: Option[String],
    endDate: Option[LocalDate],
    endDateStatus: Option[String],
    spouseIdentifier: Option[String],
    spouseForename: Option[String],
    spouseSurname: Option[String]
)

object FilteredMarriageDetailsItem {

  implicit val filteredMarriageDetailsItemWrites: Format[FilteredMarriageDetailsItem] =
    Json.format[FilteredMarriageDetailsItem]

}

case class FilteredMarriageDetails(
    marriageDetails: List[FilteredMarriageDetailsItem]
)

object FilteredMarriageDetails {

  implicit val filteredMarriageDetailsWrites: Format[FilteredMarriageDetails] =
    Json.format[FilteredMarriageDetails]

}

case class FilteredLongTermBenefitCalculationDetailsItem(
    guaranteedMinimumPensionContractedOutDeductionsPre1988: Option[
      BigDecimal
    ],
    guaranteedMinimumPensionContractedOutDeductionsPost1988: Option[
      BigDecimal
    ],
    contractedOutDeductionsPre1988: Option[BigDecimal],
    contractedOutDeductionsPost1988: Option[BigDecimal],
    longTermBenefitNotes: List[String]
)

object FilteredLongTermBenefitCalculationDetailsItem {

  implicit val filteredLongTermBenefitCalculationDetailsItemWrites
      : Format[FilteredLongTermBenefitCalculationDetailsItem] =
    Json.format[FilteredLongTermBenefitCalculationDetailsItem]

}

case class FilteredLongTermBenefitCalculationDetails(
    benefitCalculationDetails: List[FilteredLongTermBenefitCalculationDetailsItem]
)

object FilteredLongTermBenefitCalculationDetails {

  implicit val filteredLongTermBenefitCalculationDetailsWrites: Format[FilteredLongTermBenefitCalculationDetails] =
    Json.format[FilteredLongTermBenefitCalculationDetails]

}

case class FilteredSchemeMembershipDetailsItem(
    schemeName: Option[String],
    schemeMembershipStartDate: Option[LocalDate],
    schemeMembershipEndDate: Option[LocalDate],
    employersContractedOutNumberDetails: Option[String]
)

object FilteredSchemeMembershipDetailsItem {

  implicit val filteredSchemeMembershipDetailsItemWrites: Format[FilteredSchemeMembershipDetailsItem] =
    Json.format[FilteredSchemeMembershipDetailsItem]

}

case class FilteredSchemeMembershipDetails(schemeMembershipDetails: List[FilteredSchemeMembershipDetailsItem])

object FilteredSchemeMembershipDetails {

  implicit val filteredSchemeMembershipDetailsWrites: Format[FilteredSchemeMembershipDetails] =
    Json.format[FilteredSchemeMembershipDetails]

}

case class FilteredIndividualStatePensionContributionsByTaxYear(
    totalPrimaryPaidEarnings: Option[BigDecimal],
    qualifyingTaxYear: Option[Boolean]
)

object FilteredIndividualStatePensionContributionsByTaxYear {

  implicit val FilteredIndividualStatePensionContributionsByTaxYearWrites
      : Format[FilteredIndividualStatePensionContributionsByTaxYear] =
    Json.format[FilteredIndividualStatePensionContributionsByTaxYear]

}

case class FilteredIndividualStatePensionInfo(
    numberOfQualifyingYears: Option[Int],
    contributionsByTaxYear: List[FilteredIndividualStatePensionContributionsByTaxYear]
)

object FilteredIndividualStatePensionInfo {

  implicit val filteredIndividualStatePensionInfoWrites: Format[FilteredIndividualStatePensionInfo] =
    Json.format[FilteredIndividualStatePensionInfo]

}

case class GYSPResponse(
    benefitType: String,
    nationalInsuranceNumber: String,
    marriageDetailsResult: FilteredMarriageDetails,
    longTermBenefitCalculationDetailsResult: FilteredLongTermBenefitCalculationDetails,
    schemeMembershipDetailsResult: FilteredSchemeMembershipDetails,
    individualStatePensionInfoResult: FilteredIndividualStatePensionInfo,
    niContributionsAndCreditsResult: NIContributionsAndCreditsResult
)

object GYSPResponse {
  implicit val format: OFormat[GYSPResponse] = Json.format[GYSPResponse]
}
