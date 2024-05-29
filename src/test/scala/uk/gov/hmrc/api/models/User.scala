/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.models

import play.api.libs.json.{Json, OFormat}

case class User(firstName: String, lastName: String, nino: String, dateOfBirth: String)

object User {

  implicit val userJsonFormat: OFormat[User] = Json.format[User]
  val ninoUser: User                         = User("Luke", "Wood", "EG724113D", "1960-04-06")

}
