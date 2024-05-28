/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.other

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object Ecp extends CommonResponses {

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
       |                "taxYear": "2015-16",
       |                "other": [
       |                    {
       |                        "otherIncome": 44444444444.96,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2016-17",
       |                "other": [
       |                    {
       |                        "otherIncome": 55555555555.96,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2017-18",
       |                "other": [
       |                    {
       |                        "otherIncome": 77777777777.96,
       |                        "utr": "3287654321"
       |                    },
       |                    {
       |                        "otherIncome": 66666666666.96,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2018-19",
       |                "other": [
       |                    {
       |                        "otherIncome": 99999999999.96,
       |                        "utr": "3287654321"
       |                    },
       |                    {
       |                        "otherIncome": 88888888888.96,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
