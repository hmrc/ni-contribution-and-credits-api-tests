/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.test.api.helpers

import uk.gov.hmrc.test.api.StableIdentifiers
import uk.gov.hmrc.test.api.models.{IndUseCase, UseCaseResponse}
import uk.gov.hmrc.test.api.responses.individuals.benefitsAndCredits.{childTaxCredit, entrypoint, workingTaxCredit}

object NewPersonParamsHelper extends StableIdentifiers {

  def getEndpointResponse(endpoint: String, ninoId: String): UseCaseResponse =
    endpoint match {
      case Entrypoint       => getEntrypointResponse(ninoId: String)
      case _                => throw new Exception(s"Unable to perform match on endpoint: $endpoint")
    }

}
