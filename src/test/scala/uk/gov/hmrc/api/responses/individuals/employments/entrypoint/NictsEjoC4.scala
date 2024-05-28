/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.employments.entrypoint

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object NictsEjoC4 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "_links": {
       |        "paye": {
       |            "href": "/individuals/employments/paye?matchId=$matchId{&fromDate,toDate}",
       |            "title": "Get an individual's PAYE employment data"
       |        },
       |        "self": {
       |            "href": "/individuals/employments/?matchId=$matchId"
       |        }
       |    }
       |}""".stripMargin)
}
