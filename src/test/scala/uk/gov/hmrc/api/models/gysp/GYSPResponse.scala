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

import play.api.libs.json.{Json, OFormat}
import uk.gov.hmrc.api.models.benefitscheme.BenefitSchemeDetailsResponse
import uk.gov.hmrc.api.models.indivdual.statepensioninfo.IndStatePensionInfoResponse
import uk.gov.hmrc.api.models.individual.marriagedetails.MarriageDetailsResponse
import uk.gov.hmrc.api.models.longtermbenefitcalc.LongTermBenefitCalculationDetailsResponse
import uk.gov.hmrc.api.models.ltb.LongTermBenefitNotesResponse
import uk.gov.hmrc.api.models.schememembership.SchemeMembershipDetailsResponse

case class GYSPResponse(
    benefitType: String,
    nationalInsuranceNumber: String,
    benefitSchemeDetailsResult: Seq[BenefitSchemeDetailsResponse],
    marriageDetailsResult: MarriageDetailsResponse,
    longTermBenefitCalculationDetailsResult: LongTermBenefitCalculationDetailsResponse,
    longTermBenefitNotesResult: LongTermBenefitNotesResponse,
    schemeMembershipDetailsResult: SchemeMembershipDetailsResponse,
    individualStatePensionInfoResult: IndStatePensionInfoResponse,
    niContributionsAndCreditsResult: Seq[String]
)

object GYSPResponse {
  implicit val format: OFormat[GYSPResponse] = Json.format[GYSPResponse]
}
