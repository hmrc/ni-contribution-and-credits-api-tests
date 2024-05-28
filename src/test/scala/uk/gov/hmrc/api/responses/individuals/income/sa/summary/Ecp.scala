/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.summary

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object Ecp extends CommonResponses {

  val status: Int = OK

  def getFullResponse(matchId: String): UseCaseResponse =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/income/sa/summary?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        }
       |    },
       |    "selfAssessment": {
       |        "taxReturns": [
       |            {
       |                "taxYear": "2015-16",
       |                "summary": [
       |                    {
       |                        "totalIncome": 44444444444.99,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2016-17",
       |                "summary": [
       |                    {
       |                        "totalIncome": 55555555555.99,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2017-18",
       |                "summary": [
       |                    {
       |                        "totalIncome": 77777777777.99,
       |                        "utr": "3287654321"
       |                    },
       |                    {
       |                        "totalIncome": 66666666666.99,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2018-19",
       |                "summary": [
       |                    {
       |                        "totalIncome": 99999999999.99,
       |                        "utr": "3287654321"
       |                    },
       |                    {
       |                        "totalIncome": 88888888888.99,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
