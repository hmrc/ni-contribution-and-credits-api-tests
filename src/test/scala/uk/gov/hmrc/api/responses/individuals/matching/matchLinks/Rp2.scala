/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses.individuals.matching.matchLinks

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.test.api.models.UseCaseResponse
import uk.gov.hmrc.test.api.responses.CommonResponses

object Rp2 extends CommonResponses {

  val status = OK

  def getFullResponse(matchId: String) =
    UseCaseResponse(getExpectedResponse(matchId), status)

  def getExpectedResponse(matchId: String): JsValue = Json.parse(s"""{
       |    "individual": {
       |        "firstName": "John",
       |        "lastName": "Densmore",
       |        "nino": "CS700100A",
       |        "dateOfBirth": "1969-03-05"
       |    },
       |    "_links": {
       |        "self": {
       |            "href": "/individuals/matching/$matchId"
       |        },
       |        "employments": {
       |            "href": "/individuals/employments/?matchId=$matchId",
       |            "title": "Get the individual's employment data"
       |        }
       |    }
       |}""".stripMargin)
}
