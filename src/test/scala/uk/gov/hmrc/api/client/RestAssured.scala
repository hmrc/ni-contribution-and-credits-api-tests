/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.api.client

//import uk.gov.hmrc.api.conf.TestConfiguration
//import uk.gov.hmrc.api.utils.Zap.{isEnabled, proxyPort, proxyServer}

trait RestAssured {
   /*val url: String  = TestConfiguration.url("nicc") + "/" + TestConfiguration.getConfigValue("nicc-api-uri")
   def initiateProxy(requestSpec: RequestSpecification): Unit =
      if(isEnabled){
         val proxySpec = ProxySpecification.host(proxyServer).withPort(proxyPort).withSchema("http")
         requestSpec.proxy(proxySpec)
      }*/

}
