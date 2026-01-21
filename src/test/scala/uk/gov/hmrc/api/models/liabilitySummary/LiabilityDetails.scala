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

package uk.gov.hmrc.api.models.liabilitySummary

import play.api.libs.json.{Json, OFormat}
import uk.gov.hmrc.api.models.common.OfficeDetails

final case class LiabilityDetails(
    identifier: String,
    `type`: String,
    occurrenceNumber: Int,
    startDate: String,
    startDateStatus: Option[String],
    endDateStatus: Option[String],
    endDate: Option[String],
    country: Option[String],
    trainingCreditApprovalStatus: Option[String],
    casepaperReferenceNumber: Option[String],
    homeResponsibilitiesProtectionBenefitReference: Option[String],
    homeResponsibilitiesProtectionRate: Option[BigDecimal],
    homeResponsibilitiesProtectionIndicator: Option[String],
    lostCardNotificationReason: Option[String],
    lostCardRulingReason: Option[String],
    homeResponsibilityProtectionCalculationYear: Option[Int],
    awardAmount: Option[BigDecimal],
    resourceGroupIdentifier: Option[Int],
    officeDetails: Option[OfficeDetails]
)

object LiabilityDetails {

  implicit val format: OFormat[LiabilityDetails] =
    Json.format[LiabilityDetails]

}
