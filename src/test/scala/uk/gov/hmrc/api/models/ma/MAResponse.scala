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

package uk.gov.hmrc.api.models.ma

import play.api.libs.json.{Format, Json, OFormat, Writes}
import uk.gov.hmrc.api.models.esajsa.NIContributionsAndCreditsResult

import java.time.LocalDate

case class FilteredClass2MaReceipts(
    receiptDates: List[LocalDate]
)

object FilteredClass2MaReceipts {
  implicit val filteredClass2MaReceiptsWrites: Format[FilteredClass2MaReceipts] = Json.format[FilteredClass2MaReceipts]
}

case class FilteredLiabilitySummaryDetailItem(
    startDate: LocalDate,
    endDate: Option[LocalDate]
)

object FilteredLiabilitySummaryDetailItem {

  implicit val filteredLiabilitySummaryDetailItemWrites: Format[FilteredLiabilitySummaryDetailItem] =
    Json.format[FilteredLiabilitySummaryDetailItem]

}

case class FilteredLiabilitySummaryDetails(
    liabilityDetails: List[FilteredLiabilitySummaryDetailItem]
)

object FilteredLiabilitySummaryDetails {

  implicit val filteredLiabilitySummaryDetailsWrites: Format[FilteredLiabilitySummaryDetails] =
    Json.format[FilteredLiabilitySummaryDetails]

}

case class MAResponse(
    benefitType: String,
    nationalInsuranceNumber: String,
    class2MAReceiptsResult: FilteredClass2MaReceipts,
    liabilitySummaryDetailsResult: List[FilteredLiabilitySummaryDetails],
    niContributionsAndCreditsResult: NIContributionsAndCreditsResult
)

object MAResponse {
  implicit val format: Format[MAResponse] = Json.format[MAResponse]
}
