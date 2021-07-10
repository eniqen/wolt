package com.wolt.assignment

import cats.effect.kernel.Temporal
import cats.effect.{Async, Resource}
import cats.implicits.toShow
import org.http4s.dsl.Http4sDsl
import org.http4s.{HttpApp, HttpRoutes}
import org.http4s.implicits._
import org.http4s.server.{Router, Server}
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import codecs._
import com.wolt.assignment.model.TimeTable
import refined._
import cats.syntax.applicative._

/**
 * @author Mikhail Nemenko {@literal <nemenkoma@gmail.com>}
 */
class ConverterRoute[F[_]: Temporal] extends Http4sDsl[F] {
  def process : HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ POST -> Root => req.decodeR[TimeTable](t => Ok(t.asFormattedResult.map(_.show).mkString("\n")))
  }

  def endpoints: HttpApp[F] = Router("/convert" -> process).orNotFound
}

object ConverterRoute {
  def make[F[_]: Temporal]: F[ConverterRoute[F]] = new ConverterRoute[F].pure

  def run[F[_]: Async](endpoints: HttpApp[F])(ec: ExecutionContext): Resource[F, Server] =
    BlazeServerBuilder.apply[F](ec)
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(endpoints)
      .withIdleTimeout(15.minutes)
      .withResponseHeaderTimeout(15.minutes)
      .resource
}