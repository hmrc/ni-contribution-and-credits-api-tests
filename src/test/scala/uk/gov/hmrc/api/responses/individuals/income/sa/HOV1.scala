/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object HOV1 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
      |    "_links": {
      |        "pensionsAndStateBenefits": {
      |            "href": "/individuals/income/sa/pensions-and-state-benefits?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        },
      |        "employments": {
      |            "href": "/individuals/income/sa/employments?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        },
      |        "additionalInformation": {
      |            "href": "/individuals/income/sa/additional-information?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        },
      |        "self": {
      |            "href": "/individuals/income/sa?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        },
      |        "partnerships": {
      |
      |            "href": "/individuals/income/sa/partnerships?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        },
      |        "ukProperties": {
      |            "href": "/individuals/income/sa/uk-properties?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        },
      |        "selfEmployments": {
      |            "href": "/individuals/income/sa/self-employments?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        },
      |        "foreign": {
      |            "href": "/individuals/income/sa/foreign?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        },
      |        "interestsAndDividends": {
      |            "href": "/individuals/income/sa/interests-and-dividends?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        },
      |        "other": {
      |            "href": "/individuals/income/sa/other?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        },
      |        "summary": {
      |            "href": "/individuals/income/sa/summary?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        },
      |        "trusts": {
      |            "href": "/individuals/income/sa/trusts?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        }
      |    },
      |    "selfAssessment": {
      |        "registrations": [
      |            {
      |                "utr": "0987654321",
      |                "registrationDate": "2013-07-15"
      |            },
      |            {
      |                "utr": "0987654321",
      |                "registrationDate": "2011-07-15"
      |            }
      |        ],
      |        "taxReturns": [
      |            {
      |                "taxYear": "2020-21",
      |                "submissions": [
      |                    {
      |                        "utr": "0987654321",
      |                        "receivedDate": "2021-08-09"
      |                    }
      |                ]
      |            },
      |            {
      |                "taxYear": "2019-20",
      |                "submissions": [
      |                    {
      |                        "utr": "0987654321",
      |                        "receivedDate": "2020-07-01"
      |                    }
      |                ]
      |            }
      |        ]
      |    }
      |}""".stripMargin)
}
