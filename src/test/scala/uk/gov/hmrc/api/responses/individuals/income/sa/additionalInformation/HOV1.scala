/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.additionalInformation

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
       |            "href": "/individuals/income/sa/additional-information?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
       |        }
       |    },
       |    "selfAssessment": {
       |        "taxReturns": [
       |            {
       |                "taxYear": "2020-21",
       |                "additionalInformation": [
       |                    {
       |                        "utr": "0987654321",
       |                        "gainsOnLifePolicies": 963.67,
       |                        "sharesOptionsIncome": 499.67
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2019-20",
       |                "additionalInformation": [
       |                    {
       |                        "utr": "0987654321",
       |                        "gainsOnLifePolicies": 54.36,
       |                        "sharesOptionsIncome": 98.21
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}
       |
       |
       |""".stripMargin)
}
