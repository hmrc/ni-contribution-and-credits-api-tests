/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.employments.paye

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
      |            "href": "/individuals/employments/paye?matchId=$matchId&fromDate=2019-01-01&toDate=2020-03-01"
      |        }
      |    },
      |    "employments": [
      |        {
      |            "startDate": "2012-11-01",
      |            "endDate": "2019-11-30",
      |            "employer": {
      |                "payeReference": "584/AD12345",
      |                "name": "Acme Inc",
      |                "address": {
      |                    "line1": "Acme House",
      |                    "line2": "23 Acme Street",
      |                    "line3": "Richmond",
      |                    "line4": "Surrey",
      |                    "line5": "UK",
      |                    "postcode": "AI22 9LL"
      |                }
      |            },
      |            "payFrequency": "FORTNIGHTLY"
      |        },
      |        {
      |            "startDate": "2012-01-01",
      |            "endDate": "2019-03-31",
      |            "employer": {
      |                "payeReference": "123/AB12345",
      |                "name": "Disney House - Europe",
      |                "address": {
      |                    "line1": "Disney House",
      |                    "line2": "23 Disney Street",
      |                    "line3": "Brentwood",
      |                    "line4": "Essex",
      |                    "line5": "UK",
      |                    "postcode": "EI22 8TL"
      |                }
      |            },
      |            "payFrequency": "CALENDAR_MONTHLY"
      |        }
      |    ]
      |}""".stripMargin)
}
