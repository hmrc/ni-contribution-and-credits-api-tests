/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.selfEmployments

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
       |            "href": "/individuals/income/sa/self-employments?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        }
       |    },
       |    "selfAssessment": {
       |        "taxReturns": [
       |            {
       |                "taxYear": "2015-16",
       |                "selfEmployments": [
       |                    {
       |                        "selfEmploymentProfit": 44444444444.97,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2016-17",
       |                "selfEmployments": [
       |                    {
       |                        "selfEmploymentProfit": 55555555555.97,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2017-18",
       |                "selfEmployments": [
       |                    {
       |                        "selfEmploymentProfit": 77777777777.97,
       |                        "utr": "3287654321"
       |                    },
       |                    {
       |                        "selfEmploymentProfit": 66666666666.97,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2018-19",
       |                "selfEmployments": [
       |                    {
       |                        "selfEmploymentProfit": 99999999999.97,
       |                        "utr": "3287654321"
       |                    },
       |                    {
       |                        "selfEmploymentProfit": 88888888888.97,
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
