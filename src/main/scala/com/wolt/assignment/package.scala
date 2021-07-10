package com.wolt

import cats.MonadThrow
import cats.effect.Concurrent
import cats.syntax.all._
import com.wolt.assignment.model.TimeValue.TimeValueExpr
import com.wolt.assignment.model.{Info, TimeTable, TimeValue}
import com.wolt.assignment.refined.decoderOf
import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import org.http4s.circe.{JsonDecoder, jsonOf, toMessageSyntax}
import org.http4s.dsl.Http4sDsl
import org.http4s.{EntityDecoder, Request, Response}

/**
 * @author Mikhail Nemenko {@literal <nemenkoma@gmail.com>}
 */
package object assignment {
  object codecs {
    implicit val infoDecoder:      Decoder[Info]                                   = deriveDecoder
    implicit val timeTableDecoder: Decoder[TimeTable]                              = deriveDecoder
    implicit val timeValueDecoder: Decoder[TimeValue]                              = decoderOf[Int, TimeValueExpr].map(TimeValue(_))
    implicit def entityDecoder[F[_]: Concurrent, A: Decoder]: EntityDecoder[F, A]  = jsonOf[F, A]
  }

  object refined {
    implicit class RefinedRequestDecoder[F[_]: JsonDecoder: MonadThrow](req: Request[F]) extends Http4sDsl[F] {
      def decodeR[A: Decoder](f: A => F[Response[F]]): F[Response[F]] =
        req.asJsonDecode[A].attempt.flatMap {
          case Left(e) =>
            Option(e.getCause) match {
              case Some(c) if c.getMessage.contains("Predicate")   => BadRequest(c.getMessage)
              case _                                               => UnprocessableEntity()
            }
          case Right(a) => f(a)
        }
    }

    def decoderOf[T, P](implicit v: Validate[T, P], d: Decoder[T]): Decoder[T Refined P] =
      d.emap(refineV[P].apply[T](_))
  }
}
