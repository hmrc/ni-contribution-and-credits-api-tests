/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.requests.individuals.auth

object Ecp {

  val authPayload: String = s"""{
    |  "clientId": "id-123232",
    |  "authProvider": "PrivilegedApplication",
    |  "applicationId":"app-1",
    |  "applicationName": "App 1",
    |  "enrolments": ["read:individuals-matching-ho-ecp",
    |  "read:individuals-employments-ho-ecp",
    |  "read:individuals-income-ho-ecp",
    |  "assigned"],
    |  "ttl": 5000
    |}""".stripMargin
}
