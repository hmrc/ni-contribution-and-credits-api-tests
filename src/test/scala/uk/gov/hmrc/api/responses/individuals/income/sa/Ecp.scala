/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object Ecp extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "employments": {
       |            "href": "/individuals/income/sa/employments?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA employments data"
       |        },
       |        "self": {
       |            "href": "/individuals/income/sa?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        },
       |        "selfEmployments": {
       |            "href": "/individuals/income/sa/self-employments?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA self employments data"
       |        },
       |        "other": {
       |            "href": "/individuals/income/sa/other?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA other data"
       |        },
       |        "summary": {
       |            "href": "/individuals/income/sa/summary?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA summary data"
       |        }
       |    },
       |    "selfAssessment": {
       |        "registrations": [
       |            {
       |                "registrationDate": "2016-02-27",
       |                "utr": "3287654321"
       |            },
       |            {
       |                "registrationDate": "2017-02-27",
       |                "utr": "3287654321"
       |            },
       |            {
       |                "registrationDate": "2018-02-27",
       |                "utr": "3287654321"
       |            },
       |            {
       |                "registrationDate": "2018-05-27",
       |                "utr": "3287654321"
       |            },
       |            {
       |                "registrationDate": "2019-02-27",
       |                "utr": "3287654321"
       |            },
       |            {
       |                "registrationDate": "2019-04-27",
       |                "utr": "3287654321"
       |            }
       |        ],
       |        "taxReturns": [
       |            {
       |                "taxYear": "2015-16",
       |                "submissions": [
       |                    {
       |                        "receivedDate": "2016-02-28",
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2016-17",
       |                "submissions": [
       |                    {
       |                        "receivedDate": "2017-02-28",
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2017-18",
       |                "submissions": [
       |                    {
       |                        "receivedDate": "2018-02-28",
       |                        "utr": "3287654321"
       |                    },
       |                    {
       |                        "receivedDate": "2018-05-28",
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            },
       |            {
       |                "taxYear": "2018-19",
       |                "submissions": [
       |                    {
       |                        "receivedDate": "2019-02-28",
       |                        "utr": "3287654321"
       |                    },
       |                    {
       |                        "receivedDate": "2019-04-28",
       |                        "utr": "3287654321"
       |                    }
       |                ]
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
