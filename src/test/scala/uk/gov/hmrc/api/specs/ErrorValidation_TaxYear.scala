package uk.gov.hmrc.api.specs

import uk.gov.hmrc.api.models.Request

class ErrorValidation_TaxYear extends BaseSpec {
  Feature("VALIDATION OF ERROR CODES FOR TAX YEAR") {

    Scenario("Request with end tax year before start tax year receives error response 422 from MDTP"){
      val response =
        niccService.makeRequest(Request("BB000422B", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "1970", "2019"))
      response.status shouldBe 422
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe ""
      println("Response Body is: " + response.body)
    }
}
}
