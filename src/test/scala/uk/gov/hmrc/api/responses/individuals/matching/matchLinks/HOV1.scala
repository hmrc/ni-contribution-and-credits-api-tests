/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.matching.matchLinks

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object HOV1 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |  "individual": $individual1,
       |  "_links": {
       |    "income": {
       |      "name": "GET",
       |      "href": "/individuals/income/?matchId=$matchId",
       |      "title": "View individual's income"
       |    },
       |    "employments": {
       |      "name": "GET",
       |      "href": "/individuals/employments/?matchId=$matchId",
       |      "title": "View individual's employments"
       |    },
       |    "self": {
       |      "href": "/individuals/matching/$matchId"
       |    }
       |  }
       |}""".stripMargin)
}
