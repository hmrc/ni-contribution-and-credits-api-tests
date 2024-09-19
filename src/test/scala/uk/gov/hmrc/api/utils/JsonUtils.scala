package uk.gov.hmrc.api.utils

import scala.io.Source
import io.circe._
import io.circe.parser._
import play.api.libs.json.{JsValue, Json}

import java.io.FileInputStream

object JsonUtils {
  def readJsonFile(filePath: String): String = {
    val source = Source.fromResource(filePath)
    try source.getLines().mkString
    finally source.close()
  }

  def parseJson(jsonString: String): Either[ParsingFailure, Json] =
    parse(jsonString)
}
