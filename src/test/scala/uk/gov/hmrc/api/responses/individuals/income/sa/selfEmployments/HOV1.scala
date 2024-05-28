/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.selfEmployments

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
      |            "href": "/individuals/income/sa/self-employments?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        }
      |    },
      |    "selfAssessment": {
      |        "taxReturns": [
      |            {
      |                "taxYear": "2020-21",
      |                "selfEmployments": [
      |                    {
      |                        "utr": "0987654321",
      |                        "selfEmploymentProfit": 9870.55
      |                    }
      |                ]
      |            },
      |            {
      |                "taxYear": "2019-20",
      |                "selfEmployments": [
      |                    {
      |                        "utr": "0987654321",
      |                        "selfEmploymentProfit": 4060.55
      |                    }
      |                ]
      |            }
      |        ]
      |    }
      |}""".stripMargin)
}
