/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.interestsAndDividends

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
      |            "href": "/individuals/income/sa/interests-and-dividends?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        }
      |    },
      |    "selfAssessment": {
      |        "taxReturns": [
      |            {
      |                "taxYear": "2020-21",
      |                "interestsAndDividends": [
      |                    {
      |                        "utr": "0987654321",
      |                        "ukInterestsIncome": 94.34,
      |                        "foreignDividendsIncome": 8236.45,
      |                        "ukDividendsIncome": 73.62
      |                    }
      |                ]
      |            },
      |            {
      |                "taxYear": "2019-20",
      |                "interestsAndDividends": [
      |                    {
      |                        "utr": "0987654321",
      |                        "ukInterestsIncome": 51.07,
      |                        "foreignDividendsIncome": 76.45,
      |                        "ukDividendsIncome": 98.76
      |                    }
      |                ]
      |            }
      |        ]
      |    }
      |}""".stripMargin)
}
