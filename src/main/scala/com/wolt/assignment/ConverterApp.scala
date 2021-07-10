package com.wolt.assignment

import java.util.concurrent.Executors

import cats.effect.{Async, ExitCode, IO, IOApp}

import scala.concurrent.ExecutionContext._
import fs2._

/**
 * @author Mikhail Nemenko
 */
object ConverterApp extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = runApp[IO].compile.drain.flatMap(_ => IO.never).as(ExitCode.Success)

  private def runApp[F[_]: Async]: Stream[F, Unit] = for {
    route      <- Stream.eval(ConverterRoute.make[F])
    blockingEC = fromExecutorService(Executors.newFixedThreadPool(20))
    _          <- Stream.resource(ConverterRoute.run(route.endpoints)(blockingEC)) >> Stream.never
  } yield ()
}
