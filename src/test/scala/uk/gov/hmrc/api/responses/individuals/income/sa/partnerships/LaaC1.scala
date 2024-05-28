/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.partnerships

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LaaC1 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
      |    "_links": {
      |        "self": {
      |            "href": "/individuals/income/sa/partnerships?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
      |        }
      |    },
      |    "selfAssessment": {
      |        "taxReturns": [
      |            {
      |                "taxYear": "2018-19",
      |                "partnerships": [
      |                    {
      |                        "partnershipProfit": 99999999999.95
      |                    }
      |                ]
      |            }
      |        ]
      |    }
      |}""".stripMargin)
}
