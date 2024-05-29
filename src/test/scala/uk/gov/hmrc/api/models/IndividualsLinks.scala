/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.models

import play.api.libs.json.{Json, OFormat}

case class IndividualsLinks(name: String, href: String, title: String)

object IndividualsLinks {

  implicit val userJsonFormat: OFormat[IndividualsLinks] = Json.format[IndividualsLinks]

}
