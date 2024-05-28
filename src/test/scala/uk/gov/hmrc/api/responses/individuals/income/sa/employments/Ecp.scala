/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.employments

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object Ecp extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""
       |{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/income/sa/employments?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        }
       |    },
       |    "selfAssessment": {
       |        "taxReturns": [
       |            {
       |                "taxYear": "2015-16",
       |                "employments": [
       |                    {
       |                        "employmentIncome": 44444444444.98,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2016-17",
       |                "employments": [
       |                    {
       |                        "employmentIncome": 55555555555.98,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2017-18",
       |                "employments": [
       |                    {
       |                        "employmentIncome": 77777777777.98,
       |                        "utr": "3287654321"
       |                    },
       |                    {
       |                        "employmentIncome": 66666666666.98,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2018-19",
       |                "employments": [
       |                    {
       |                        "employmentIncome": 99999999999.98,
       |                        "utr": "3287654321"
       |                    },
       |                    {
       |                        "employmentIncome": 88888888888.98,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
