/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.partnerships

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object HOV1 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
      |    "_links": {
      |        "self": {
      |            "href": "/individuals/income/sa/partnerships?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        }
      |    },
      |    "selfAssessment": {
      |        "taxReturns": [
      |            {
      |                "taxYear": "2020-21",
      |                "partnerships": [
      |                    {
      |                        "utr": "0987654321",
      |                        "partnershipProfit": 91.78
      |                    }
      |                ]
      |            },
      |            {
      |                "taxYear": "2019-20",
      |                "partnerships": [
      |                    {
      |                        "utr": "0987654321",
      |                        "partnershipProfit": 65.78
      |                    }
      |                ]
      |            }
      |        ]
      |    }
      |}""".stripMargin)
}
