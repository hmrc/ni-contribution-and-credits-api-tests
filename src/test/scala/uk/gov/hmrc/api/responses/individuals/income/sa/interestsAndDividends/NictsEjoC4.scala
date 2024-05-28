/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.interestsAndDividends

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object NictsEjoC4 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/income/sa/interests-and-dividends?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        }
       |    },
       |    "selfAssessment": {
       |        "taxReturns": [
       |            {
       |                "taxYear": "2018-19",
       |                "interestsAndDividends": [
       |                    {
       |                        "ukInterestsIncome": 99999999999.93,
       |                        "foreignDividendsIncome": 99999999999.94,
       |                        "ukDividendsIncome": 99999999999.92
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
