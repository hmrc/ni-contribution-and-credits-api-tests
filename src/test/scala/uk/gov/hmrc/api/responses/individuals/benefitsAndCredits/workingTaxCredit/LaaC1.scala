/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.benefitsAndCredits.workingTaxCredit

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LaaC1 extends CommonResponses {

  val status: Int = OK

  def getFullResponse(matchId: String): UseCaseResponse =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/benefits-and-credits/working-tax-credits?matchId=$matchId&fromDate=2019-01-01&toDate=2019-12-31"
       |        }
       |    },
       |    "applications": [
       |        {
       |            "awards": [
       |                {
       |                    "payProfCalcDate": "2020-08-18",
       |                    "totalEntitlement": 18765.23,
       |                    "workingTaxCredit": {
       |                        "amount": 930.98,
       |                        "paidYTD": 8976.34
       |                    },
       |                    "childTaxCredit": {
       |                        "childCareAmount": 930.98
       |                    },
       |                    "payments": [
       |                        {
       |                            "startDate": "1996-01-01",
       |                            "endDate": "1996-03-01",
       |                            "postedDate": "1996-04-01",
       |                            "frequency": 7,
       |                            "tcType": "ETC",
       |                            "amount": 76.34
       |                        }
       |                    ]
       |                }
       |            ]
       |        }
       |    ]
       |}""".stripMargin)
}
