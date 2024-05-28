/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object LaaC2 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "pensionsAndStateBenefits": {
       |            "href": "/individuals/income/sa/pensions-and-state-benefits?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA pensions and state benefits data"
       |        },
       |        "employments": {
       |            "href": "/individuals/income/sa/employments?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA employments data"
       |        },
       |        "additionalInformation": {
       |            "href": "/individuals/income/sa/additional-information?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA additional information data"
       |        },
       |        "self": {
       |            "href": "/individuals/income/sa?matchId=$matchId&fromTaxYear=2018-19&toTaxYear=2019-20"
       |        },
       |        "partnerships": {
       |            "href": "/individuals/income/sa/partnerships?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA partnerships data"
       |        },
       |        "ukProperties": {
       |            "href": "/individuals/income/sa/uk-properties?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA UK properties data"
       |        },
       |        "foreign": {
       |            "href": "/individuals/income/sa/foreign?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA foreign income data"
       |        },
       |        "furtherDetails": {
       |            "href": "/individuals/income/sa/further-details?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA further details data"
       |        },
       |        "interestsAndDividends": {
       |            "href": "/individuals/income/sa/interests-and-dividends?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA interest and dividends data"
       |        },
       |        "other": {
       |            "href": "/individuals/income/sa/other?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA other data"
       |        },
       |        "summary": {
       |            "href": "/individuals/income/sa/summary?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA summary data"
       |        },
       |        "trusts": {
       |            "href": "/individuals/income/sa/trusts?matchId=$matchId{&fromTaxYear,toTaxYear}",
       |            "title": "Get an individual's SA trusts data"
       |        }
       |    },
       |    "selfAssessment": {
       |        "registrations": [
       |            {
       |                "registrationDate": "2018-02-20"
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
