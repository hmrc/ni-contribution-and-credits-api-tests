/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object HmctsC3 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/income/sa?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        },
       |        "ukProperties": {
       |            "href": "/individuals/income/sa/uk-properties?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA UK properties data"
       |        },
       |        "selfEmployments": {
       |            "href": "/individuals/income/sa/self-employments?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA self employments data"
       |        },
       |        "foreign": {
       |            "href": "/individuals/income/sa/foreign?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA foreign income data"
       |        },
       |        "interestsAndDividends": {
       |            "href": "/individuals/income/sa/interests-and-dividends?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA interest and dividends data"
       |        },
       |        "summary": {
       |            "href": "/individuals/income/sa/summary?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA summary data"
       |        }
       |    },
       |    "selfAssessment": {
       |        "registrations": [
       |            {
       |                "registrationDate": "2018-02-28"
       |            }
       |        ],
       |        "taxReturns": [
       |            {
       |                "taxYear": "2018-19",
       |                "submissions": []
       |            }
       |        ]
       |    }
       |}""".stripMargin)
}
