/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.other

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LsaniC3 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/income/sa/other?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        }
       |    },
       |    "selfAssessment": {
       |        "taxReturns": [
       |            {
       |                "taxYear": "2018-19",
       |                "other": [
       |                    {
       |                        "otherIncome": 99999999999.87
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
