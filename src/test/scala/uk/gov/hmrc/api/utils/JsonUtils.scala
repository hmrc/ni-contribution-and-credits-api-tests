/*package uk.gov.hmrc.api.utils

import scala.io.Source
import io.circe._
import io.circe.parser._
import io.circe.generic.auto._
import play.api.libs.json.{JsValue, Json}
import java.io.File

import java.io.FileInputStream

case class NIDetails(nationalInsuranceNumber: String ,dateOfBirth: String, customerCorrelationID: String,startTaxYear: Int, endTaxYear: Int)
 def parseJsonFile(file: File): Either[ParsingFailure, Json] = {
  val source = Source.fromFile(file)
  val jsonString = try source.mkString finally source.close()
  parse(jsonString)
}*/