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

package uk.gov.hmrc.api.models.benefitscheme

import play.api.libs.json.{Json, OFormat}

final case class BenefitSchemeDetails(
    magneticTapeNumber: Option[Int],
    schemeName: Option[String],
    schemeStartDate: Option[String],
    schemeCessationDate: Option[String],
    contractedOutDeductionExtinguishedDate: Option[String],
    paymentSuspensionDate: Option[String],
    recoveriesSuspendedDate: Option[String],
    paymentRestartDate: Option[String],
    recoveriesRestartedDate: Option[String],
    schemeNature: Option[String],
    benefitSchemeInstitution: Option[String],
    accruedGMPLiabilityServiceDate: Option[String],
    rerouteToSchemeCessation: Option[String],
    statementInhibitor: Option[String],
    certificateCancellationDate: Option[String],
    suspendedDate: Option[String],
    isleOfManInterest: Option[Boolean],
    schemeWindingUp: Option[Boolean],
    revaluationRateSequenceNumber: Option[Int],
    benefitSchemeStatus: Option[String],
    dateFormallyCertified: Option[String],
    privatePensionSchemeSanctionDate: Option[String],
    currentOptimisticLock: Int,
    schemeConversionDate: Option[String],
    schemeInhibitionStatus: String,
    reconciliationDate: Option[String],
    schemeContractedOutNumberDetails: String
)

object BenefitSchemeDetails {
  implicit val format: OFormat[BenefitSchemeDetails] = Json.format[BenefitSchemeDetails]
}
