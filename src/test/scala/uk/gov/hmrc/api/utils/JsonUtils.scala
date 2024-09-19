package uk.gov.hmrc.api.utils

import scala.io.Source
import io.circe._
import io.circe.parser._
import io.circe.generic.auto._
import uk.gov.hmrc.api.models.Request

import scala.util

object JsonUtils {
  def readJsonFile(filePath: String): String = {
    val source = Source.fromResource(filePath)
    try source.getLines().mkString
    finally source.close()
  }

  def parseJsonToMap(jsonString: String): Either[Error, Map[String, Request]] =
    parse(jsonString).flatMap(_.as[Map[String, Request]])
}
