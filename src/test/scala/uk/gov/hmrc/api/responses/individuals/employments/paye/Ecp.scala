/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.employments.paye

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
       |                "name": "ACME Industries Limited",
       |                "address": {
       |                    "line1": "Unit 23",
       |                    "line2": "Utilitarian Industrial Park",
       |                    "line3": "Utilitown",
       |                    "line4": "County Durham",
       |                    "line5": "UK",
       |                    "postcode": "DH1 4YX"
       |                }
       |            },
       |            "payment": [
       |                {
       |                    "date": "2019-01-31",
       |                    "paidTaxablePay": 4765.32
       |                }
       |            ]
       |        },
       |        {
       |            "startDate": "2018-01-01",
       |            "endDate": "2018-01-21",
       |            "payFrequency": "WEEKLY",
       |            "employer": {
       |                "payeReference": "248/ZT6767896A",
       |                "name": "Aperture Science",
       |                "address": {
       |                    "line1": "Unit 21",
       |                    "line2": "Utilitarian Industrial Park",
       |                    "line3": "Utilitown",
       |                    "line4": "County Durham",
       |                    "line5": "UK",
       |                    "postcode": "DH2 4YY"
       |                }
       |            },
       |            "payment": [
       |                {
       |                    "date": "2018-01-07",
       |                    "paidTaxablePay": 1765.33
       |                },
       |                {
       |                    "date": "2018-01-14",
       |                    "paidTaxablePay": 1765.34
       |                },
       |                {
       |                    "date": "2018-01-21",
       |                    "paidTaxablePay": 1765.35
       |                }
       |            ]
       |        },
       |        {
       |            "startDate": "2017-01-01",
       |            "endDate": "2017-03-31",
       |            "payFrequency": "CALENDAR_MONTHLY",
       |            "employer": {
       |                "payeReference": "249/ZT6767896A",
       |                "name": "ENCOM",
       |                "address": {
       |                    "line1": "Unit 19",
       |                    "line2": "Utilitarian Industrial Park",
       |                    "line3": "Utilitown",
       |                    "line4": "County Durham",
       |                    "line5": "UK",
       |                    "postcode": "DH3 4YZ"
       |                }
       |            },
       |            "payment": [
       |                {
       |                    "date": "2017-03-31",
       |                    "paidTaxablePay": 3065.4
       |                },
       |                {
       |                    "date": "2017-02-28",
       |                    "paidTaxablePay": 3065.41
       |                },
       |                {
       |                    "date": "2017-01-31",
       |                    "paidTaxablePay": 3065.42
       |                }
       |            ]
       |        }
       |    ]
       |}""".stripMargin)
}
