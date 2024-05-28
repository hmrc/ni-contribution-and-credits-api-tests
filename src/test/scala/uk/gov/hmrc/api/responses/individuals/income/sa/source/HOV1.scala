/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.income.sa.source

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
      |            "href": "/individuals/income/sa/sources?matchId=$matchId&fromTaxYear=2019-20&toTaxYear=2021-22"
      |        }
      |    },
      |    "selfAssessment": {
      |        "taxReturns": [
      |            {
      |                "taxYear": "2020-21",
      |                "sources": [
      |                    {
      |                        "utr": "0987654321",
      |                        "businessAddress": {
      |                            "line1": "1 Jazz Lane",
      |                            "line2": "Smooth City",
      |                            "line3": "Chillshire",
      |                            "line4": "England",
      |                            "postcode": "NE11 9ZZ",
      |                            "addressType": "correspondenceAddress"
      |                        }
      |                    }
      |                ]
      |            },
      |            {
      |                "taxYear": "2019-20",
      |                "sources": [
      |                    {
      |                        "utr": "0987654321",
      |                        "businessAddress": {
      |                            "line1": "1 Hip Road",
      |                            "line2": "Funky Town",
      |                            "line3": "Soul County",
      |                            "line4": "England",
      |                            "postcode": "NE99 1ZZ",
      |                            "addressType": "homeAddress"
      |                        }
      |                    }
      |                ]
      |            }
      |        ]
      |    }
      |}""".stripMargin)
}
