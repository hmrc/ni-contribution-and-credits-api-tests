/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.models

import play.api.libs.json.{Json, OFormat}
case class Request(dateOfBirth: String)

/*object Request {

  implicit val userJsonFormat: OFormat[Request] = Json.format[Request]
 val ninoUser: Request                         = Request("1960-04-06")

}*/