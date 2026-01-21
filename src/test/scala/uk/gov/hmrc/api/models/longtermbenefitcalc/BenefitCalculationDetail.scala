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

package uk.gov.hmrc.api.models.longtermbenefitcalc

import play.api.libs.json.{Json, OFormat}
import uk.gov.hmrc.api.models.common.OfficeDetails

import java.time.LocalDate

final case class BenefitCalculationDetail(
    nationalInsuranceNumber: String,
    benefitType: String,
    associatedCalculationSequenceNumber: Int,
    calculationStatus: Option[String],
    substitutionMethod1: Option[Int],
    substitutionMethod2: Option[Int],
    calculationDate: Option[LocalDate],
    guaranteedMinimumPensionContractedOutDeductionsPre1988: Option[BigDecimal],
    guaranteedMinimumPensionContractedOutDeductionsPost1988: Option[BigDecimal],
    contractedOutDeductionsPre1988: Option[BigDecimal],
    contractedOutDeductionsPost1988: Option[BigDecimal],
    additionalPensionPercentage: Option[BigDecimal],
    basicPensionPercentage: Option[Int],
    survivorsBenefitAgeRelatedPensionPercentage: Option[BigDecimal],
    additionalAgeRelatedPensionPercentage: Option[BigDecimal],
    inheritedBasicPensionPercentage: Option[BigDecimal],
    inheritedAdditionalPensionPercentage: Option[BigDecimal],
    inheritedGraduatedPensionPercentage: Option[BigDecimal],
    inheritedGraduatedBenefit: Option[BigDecimal],
    calculationSource: Option[String],
    payday: Option[String],
    dateOfBirth: Option[LocalDate],
    husbandDateOfDeath: Option[LocalDate],
    additionalPost1997PensionPercentage: Option[BigDecimal],
    additionalPost1997AgeRelatedPensionPercentage: Option[BigDecimal],
    additionalPensionNotionalPercentage: Option[BigDecimal],
    additionalPost1997PensionNotionalPercentage: Option[BigDecimal],
    inheritedAdditionalPensionNotionalPercentage: Option[BigDecimal],
    inheritableAdditionalPensionPercentage: Option[Int],
    additionalPost2002PensionNotionalPercentage: Option[BigDecimal],
    additionalPost2002PensionPercentage: Option[BigDecimal],
    inheritedAdditionalPost2002PensionNotionalPercentage: Option[BigDecimal],
    inheritedAdditionalPost2002PensionPercentage: Option[BigDecimal],
    additionalPost2002AgeRelatedPensionPercentage: Option[BigDecimal],
    singleContributionConditionRulesApply: Option[Boolean],
    officeDetails: Option[OfficeDetails],
    newStatePensionCalculationDetails: Option[NewStatePensionCalculationDetails]
)

object BenefitCalculationDetail {

  implicit val format: OFormat[BenefitCalculationDetail] =
    Json.format[BenefitCalculationDetail]

}
