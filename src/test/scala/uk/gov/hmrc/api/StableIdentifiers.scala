/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api

import uk.gov.hmrc.test.api.conf.TestConfiguration

trait StableIdentifiers {

// APIs
  val NewPersonParams: String               = "NewPerson Params API"
// ENDPOINTS
// Common
  val Entrypoint: String                 = TestConfiguration.endpoint("entrypoint")
// Auth
  val Session: String                    = TestConfiguration.endpoint("session")

}
