/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.responses

import play.api.libs.json.Json
import uk.gov.hmrc.test.api.models.UseCaseResponse

trait CommonResponses {

  val OK: Int           = 200
  val created: Int      = 201
  val unauthorized: Int = 401
  val notFound: Int     = 404

  val unauthorizedResponse: UseCaseResponse = UseCaseResponse(Json.parse(unauthorizedPayload.stripMargin), unauthorized)

  lazy val unauthorizedPayload: String = s"""{
       |    "code": "UNAUTHORIZED",
       |    "message": "Insufficient Enrolments"
       |}"""

  val individual1: String = s"""{
       |  "firstName": "Luke",
       |  "lastName": "Wood",
       |  "nino": "EG724113D",
       |  "dateOfBirth": "1960-04-06"
       |}"""

  val individual2: String = s"""{
       | "firstName": "John",
       | "lastName": "Densmore",
       | "nino": "CS700100A",
       | "dateOfBirth": "1969-03-05"
       |}"""
}
