/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.models

case class Response(niContribution: NIContribution)

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