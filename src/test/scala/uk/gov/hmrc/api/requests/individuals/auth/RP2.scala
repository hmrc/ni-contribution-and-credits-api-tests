/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.requests.individuals.auth

object RP2 {

  val authPayload: String = s"""{
    |  "clientId": "id-123232",
    |  "authProvider": "PrivilegedApplication",
    |  "applicationId":"app-1",
    |  "applicationName": "App 1",
    |  "enrolments": ["read:individuals-matching-ho-rp2",
    |  "read:individuals-employments-ho-rp2",
    |  "assigned"],
    |  "ttl": 5000
    |}""".stripMargin
}
