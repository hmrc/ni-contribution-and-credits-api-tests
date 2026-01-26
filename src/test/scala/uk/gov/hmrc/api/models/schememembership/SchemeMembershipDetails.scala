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

package uk.gov.hmrc.api.models.schememembership

import play.api.libs.json.{Json, OFormat}

import java.time.LocalDate

final case class SchemeMembershipDetails(
    nationalInsuranceNumber: String,
    schemeMembershipSequenceNumber: Int,
    schemeMembershipOccurrenceNumber: Int,
    schemeMembershipStartDate: Option[LocalDate],
    schemeMembershipEndDate: Option[LocalDate],
    contractedOutEmployerIdentifier: Option[Int],
    methodOfPreservationType: Option[String],
    totalLinkedGuaranteedMinimumPensionContractedOutDeductions: Option[BigDecimal],
    accruedPensionContractedOutDeductionsValue: Option[BigDecimal],
    totalLinkedGuaranteedMinimumPensionContractedOutDeductionsPost1988: Option[BigDecimal],
    accruedPensionContractedOutDeductionsValuePost1988: Option[BigDecimal],
    revaluationRate: Option[String],
    guaranteedMinimumPensionReconciliationStatus: Option[String],
    employeesReference: Option[String],
    finalYearEarnings: Option[BigDecimal],
    penultimateYearEarnings: Option[BigDecimal],
    retrospectiveEarnings: Option[BigDecimal],
    furtherPaymentsConfirmation: Option[String],
    survivorStatus: Option[String],
    transferPremiumElectionDate: Option[LocalDate],
    revaluationApplied: Option[Boolean],
    stateEarningsRelatedPensionsSchemeNonRestorationValue: Option[BigDecimal],
    stateEarningsRelatedPensionsSchemeValuePost1988: Option[BigDecimal],
    apparentUnnotifiedTerminationStatus: Option[String], // EnumAutStat

    terminationMicrofilmNumber: Option[Int],
    debitVoucherMicrofilmNumber: Option[Int],
    creationMicrofilmNumber: Option[Int],
    inhibitSchemeProcessing: Option[Boolean],
    extensionDate: Option[LocalDate],
    guaranteedMinimumPensionContractedOutDeductionsRevalued: Option[BigDecimal],
    clericalCalculationInvolved: Option[String],
    clericallyControlledTotal: Option[BigDecimal],
    clericallyControlledTotalPost1988: Option[BigDecimal],
    certifiedAmount: Option[BigDecimal],
    enforcementStatus: Option[String],
    stateSchemePremiumDeemed: Option[String],
    transferTakeUpDate: Option[LocalDate],
    schemeMembershipTransferSequenceNumber: Option[Int],
    contributionCategoryFinalYear: Option[String],
    contributionCategoryPenultimateYear: Option[String],
    contributionCategoryRetrospectiveYear: Option[String],
    protectedRightsStartDate: Option[LocalDate],
    schemeMembershipDebitReason: Option[String],
    technicalAmount: Option[BigDecimal],
    minimumFundTransferAmount: Option[BigDecimal],
    actualTransferValue: Option[BigDecimal],
    schemeSuspensionType: Option[String],
    guaranteedMinimumPensionConversionApplied: Option[Boolean],
    employersContractedOutNumberDetails: Option[String],
    schemeCreatingContractedOutNumberDetails: Option[String],
    schemeTerminatingContractedOutNumberDetails: Option[String],
    importingAppropriateSchemeNumberDetails: Option[String],
    apparentUnnotifiedTerminationDestinationDetails: Option[String]
)

object SchemeMembershipDetails {

  implicit val format: OFormat[SchemeMembershipDetails] =
    Json.format[SchemeMembershipDetails]

}
