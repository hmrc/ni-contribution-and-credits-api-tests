/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.api.models.nicc

import play.api.libs.json.{Json, OFormat}

final case class NiccResponse(
    niClass1: Option[List[NiClass1]],
    niClass2: Option[List[NiClass2]]
)

object NiccResponse {
  implicit val format: OFormat[NiccResponse] = Json.format[NiccResponse]
}
