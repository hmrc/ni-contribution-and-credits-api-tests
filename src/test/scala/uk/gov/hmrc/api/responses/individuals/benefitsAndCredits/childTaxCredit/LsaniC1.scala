/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.benefitsAndCredits.childTaxCredit

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LsaniC1 extends CommonResponses {

  val status: Int = OK

  def getFullResponse(matchId: String): UseCaseResponse =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/benefits-and-credits/child-tax-credits?matchId=$matchId&fromDate=2019-01-01&toDate=2019-12-31"
       |        }
       |    },
       |    "applications": [
       |        {
       |            "id": 123,
       |            "awards": [
       |                {
       |                    "payProfCalcDate": "2020-08-18",
       |                    "totalEntitlement": 18765.23,
       |                    "childTaxCredit": {
       |                        "childCareAmount": 930.98,
       |                        "ctcChildAmount": 730.49,
       |                        "familyAmount": 100.49,
       |                        "babyAmount": 100,
       |                        "paidYTD": 8976.34
       |                    },
       |                    "payments": [
       |                        {
       |                            "startDate": "1996-01-01",
       |                            "endDate": "1996-03-01",
       |                            "postedDate": "1996-04-01",
       |                            "frequency": 7,
       |                            "tcType": "ICC",
       |                            "amount": 76.34
       |                        }
       |                    ]
       |                }
       |            ]
       |        }
       |    ]
       |}""".stripMargin)
}
