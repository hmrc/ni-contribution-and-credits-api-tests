package uk.gov.hmrc.api.specs

import uk.gov.hmrc.api.models.Request

class ErrorValidation_AuthToken extends BaseSpec {

  Feature("VALIDATION OF ERROR CODES FOR AUTH TOKEN") {

    Scenario("Request with invalid bearer token receives error response 500 from MDTP") {
      val response =
        niccService.makeRequestWithBearerToken(Request("BB000200", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2019", "2021"), "invalidToken")
      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe
      println("Response Body is: " + response.body)
    }

    Scenario("Request with empty bearer token receives error response 500 from MDTP") {
      val response =
        niccService.makeRequestWithBearerToken(Request("BB000200", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2019", "2021"), "")
      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe
        println("Response Body is: " + response.body)
    }
    
    Scenario("Request with expired bearer token receives error response 500 from MDTP") {
      val response =
        niccService.makeRequestWithBearerToken(Request("BB000200", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2019", "2021"), "")
      response.status shouldBe 500
      println("Response Status Code is : " + response.status + " " + response.statusText)
      response.body shouldBe
        println("Response Body is: " + response.body)
    }
  }
}
