//package uk.gov.hmrc.api.specs
//
//import uk.gov.hmrc.api.models.Request
//
//class Pending {
//
//  //Response is from backend
//  Scenario("Passing Valid Request but from backend responding 400 ") {
//    val response =
//      niccService.makeRequest(Request("BB000400B", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2019", "2021"))
//    response.status shouldBe 400
//    println("The Response Status Code is : " + response.status + " " + response.statusText)
//    println(response.body)
//  }
//
//
//  Scenario("Passing incorrect date of birth format at request") {
//
//    val response =
//      niccService.makeRequest(Request("BB000400B", "05-1960", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2019", "2021"))
//    response.status shouldBe 400
//    println("The Response Status Code is : " + response.status + " " + response.statusText)
//    println(response.body)
//  }
//
//  Scenario("When backend responds 422 statuscode passed to the frontend ") {
//    val response =
//      niccService.makeRequest(Request("BB000422B", "1976-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2022", "2021"))
//    response.status shouldBe 422
//    println("The Response Status Code is : " + response.status + " " + response.statusText)
//    println(response.body)
//  }
//
//
//  /* Scenario("Incorrect Access Token Type") {
//      val response =
//        niccService.makeRequest(Request("1960-04-05"), "BB000401B", "2019", "2021")
//      response.status shouldBe 401
//      println("The Response Status Code is : " + response.status + " " + response.statusText)
//      println(response.body)
//    }*/
//
//  /* Scenario("No Access Token Type") {
//      val response =
//        niccService.makeRequest(Request("1960-04-05"), "BB000401B", "2019", "2021")
//      response.status shouldBe 401
//      println("The Response Status Code is : " + response.status + " " + response.statusText)
//      println(response.body)
//    }*/
//
//  Scenario("When the backend responds 404 statuscode the frontend responds 500") {
//    val response =
//      niccService.makeRequest(Request("BB000404B", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2022", "2023"))
//    response.status shouldBe 500
//    println("The Response Status Code is : " + response.status + " " + response.statusText)
//    println(response.body)
//  }
//
//  Scenario("When the backend responds 403 statuscode the frontend responds 500") {
//    val response =
//      niccService.makeRequest(Request("BB000403B", "1980-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2022", "2023"))
//    response.status shouldBe 500
//    println("The Response Status Code is : " + response.status + " " + response.statusText)
//  }
//
//  Scenario("When the backend responds 500 statuscode the frontend responds 500") {
//    val response =
//      niccService.makeRequest(Request("BB000500B", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2022", "2023"))
//    response.status shouldBe 500
//    println("The Response Status Code is : " + response.status)
//    println(response.body)
//  }
//
//  Scenario("When the backend responds 400 statuscode the frontend responds 400") {
//    val response =
//      niccService.makeRequest(Request("BB000400B", "1960-04-05", "e470d658-99f7-4292-a4a1-ed12c72f1337", "2022", "2023"))
//    response.status shouldBe 400
//    println("The Response Status Code is : " + response.status)
//    println(response.body)
//  }
//}
