/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.employments.paye

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object Rp2 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/employments/paye?matchId=$matchId&fromDate=2019-01-01&toDate=2019-12-31"
       |        }
       |    },
       |    "employments": [
       |        {
       |            "startDate": "2019-01-01",
       |            "endDate": "2019-03-31",
       |            "payFrequency": "CALENDAR_MONTHLY",
       |            "employer": {
       |                "payeReference": "247/ZT6767895A",
       |                "name": "Umbrella Industries Ltd",
       |                "address": {
       |                    "line1": "Unit 42",
       |                    "line2": "Utilitarian Industrial Park",
       |                    "line3": "Utilitown",
       |                    "line4": "County Durham",
       |                    "line5": "UK",
       |                    "postcode": "DH4 2YX"
       |                }
       |            },
       |            "payment": [
       |                {
       |                    "date": "2019-01-31",
       |                    "paidTaxablePay": 7765.42
       |                },
       |                {
       |                    "date": "2019-02-28",
       |                    "paidTaxablePay": 8765.42
       |                },
       |                {
       |                    "date": "2019-03-31",
       |                    "paidTaxablePay": 9765.42
       |                }
       |            ]
       |        }
       |    ]
       |}""".stripMargin)
}
