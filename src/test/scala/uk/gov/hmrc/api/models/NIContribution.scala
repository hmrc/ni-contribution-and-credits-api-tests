/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.api.models

import play.api.libs.json.{Json, OFormat}

case class NIContribution(taxYear: Int,
                          contributionCategoryLetter: String,
                          contributionCategory: String,
                          totalContribution: BigDecimal,
                          primaryContribution: BigDecimal,
                          class1ContributionStatus: String,
                          primaryPaidEarnings: BigDecimal) {


  /*taxYear: 2022,
  "contributionCategoryLetter": "s",
  "contributionCategory": "(NONE)",
  "totalContribution": 99999999999999.98,
  "primaryContribution": 99999999999999.98,
  "class1ContributionStatus": "COMPLIANCE & YIELD INCOMPLETE",
  "primaryPaidEarnings": 99999999999999.98*/

}

object NIContribution {

  implicit val format: OFormat[NIContribution] = Json.format[NIContribution]

}
