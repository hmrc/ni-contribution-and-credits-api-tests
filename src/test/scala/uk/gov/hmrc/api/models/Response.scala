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

case class Response(niContribution: Seq[NIContribution], niCredit: Seq[NICredit])

/*object Request {

  implicit val userJsonFormat: OFormat[Request] = Json.format[Request]
 val ninoUser: Request                         = Request("1960-04-06")

}*/
/*{
  "niContribution": [
  {
    "taxYear": 2022,
    "contributionCategoryLetter": "s",
    "contributionCategory": "(NONE)",
    "totalContribution": 99999999999999.98,
    "primaryContribution": 99999999999999.98,
    "class1ContributionStatus": "COMPLIANCE & YIELD INCOMPLETE",
    "primaryPaidEarnings": 99999999999999.98
  }
  ],
  "niCredit": [
  {
    "taxYear": 2022,
    "numberOfCredits": 53,
    "contributionCreditTypeCode": "C2",
    "contributionCreditType": "CLASS 2 - NORMAL RATE",
    "class2Or3EarningsFactor": 99999999999999.98,
    "class2NicAmount": 99999999999999.98,
    "class2Or3CreditStatus": "NOT KNOWN/NOT APPLICABLE"
  }
  ]
}*/