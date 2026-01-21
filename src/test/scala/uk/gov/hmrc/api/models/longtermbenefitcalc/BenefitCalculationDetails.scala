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

import java.time.LocalDate

final case class BenefitCalculationDetails(
    additionalPensionAmountPre1997: Option[BigDecimal],
    additionalPensionAmountPost1997: Option[BigDecimal],
    pre97AgeRelatedAdditionalPension: Option[BigDecimal],
    post97AgeRelatedAdditionalPension: Option[BigDecimal],
    basicPensionIncrementsCashValue: Option[BigDecimal],
    additionalPensionIncrementsCashValue: Option[BigDecimal],
    graduatedRetirementBenefitCashValue: Option[BigDecimal],
    totalGuaranteedMinimumPension: Option[BigDecimal],
    totalNonGuaranteedMinimumPension: Option[BigDecimal],
    longTermBenefitsIncrementalCashValue: Option[BigDecimal],
    greatBritainPaymentAmount: Option[BigDecimal],
    dateOfBirth: Option[LocalDate],
    notionalPost1997AdditionalPension: Option[BigDecimal],
    notionalPre1997AdditionalPension: Option[BigDecimal],
    inheritableNotionalAdditionalPensionIncrements: Option[BigDecimal],
    conditionOneSatisfied: Option[String],
    reasonForFormIssue: Option[String],
    longTermBenefitsCategoryACashValue: Option[BigDecimal],
    longTermBenefitsCategoryBLCashValue: Option[BigDecimal],
    longTermBenefitsUnitValue: Option[BigDecimal],
    additionalNotionalPensionAmountPost2002: Option[BigDecimal],
    additionalPensionAmountPost2002: Option[BigDecimal],
    additionalNotionalPensionIncrementsInheritedPost2002: Option[BigDecimal],
    additionalPensionIncrementsInheritedPost2002: Option[BigDecimal],
    post02AgeRelatedAdditionalPension: Option[BigDecimal],
    pre1975ShortTermBenefits: Option[Int],
    survivingSpouseAge: Option[Int],
    operativeBenefitStartDate: Option[LocalDate],
    sicknessBenefitStatusForReports: Option[String],
    benefitCalculationDetail: Option[BenefitCalculationDetail]
)

object BenefitCalculationDetails {

  implicit val format: OFormat[BenefitCalculationDetails] =
    Json.format[BenefitCalculationDetails]

}
