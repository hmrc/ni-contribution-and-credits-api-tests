/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.requests.individuals.auth

object NictsEjoC4 {

  val authPayload: String = s"""{
    |  "clientId": "id-123232",
    |  "authProvider": "PrivilegedApplication",
    |  "applicationId":"app-1",
    |  "applicationName": "App 1",
    |  "enrolments": ["read:individuals-details-nictsejo-c4",
    |  "read:individuals-employments-nictsejo-c4",
    |  "read:individuals-income-nictsejo-c4",
    |  "read:individuals-matching-nictsejo-c4",
    |  "assigned"],
    |  "ttl": 5000
    |}""".stripMargin
}
