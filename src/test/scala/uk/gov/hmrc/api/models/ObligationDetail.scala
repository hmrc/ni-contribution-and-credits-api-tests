/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.api.models

import play.api.libs.json.{Json, OFormat}

case class ObligationDetail(
  status: String,
  inboundCorrespondenceFromDate: String,
  inboundCorrespondenceToDate: String,
  periodKey: String
) {

  override def toString: String =
    Json.prettyPrint(Json.toJson(this))

}

object ObligationDetail {

  implicit val obligationDetailsFormat: OFormat[ObligationDetail] = Json.format[ObligationDetail]

}
