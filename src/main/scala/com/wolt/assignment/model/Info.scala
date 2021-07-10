package com.wolt.assignment.model

import cats.data.Chain
import com.wolt.assignment.enums.Status

/**
 * @author Mikhail Nemenko {@literal <nemenkoma@gmail.com>}
 */
final case class Info(`type`: Status, value: TimeValue)

object Info {
  def toFromTo(chain: Chain[Info]): List[FromTo] = chain.toList.grouped(2).toList.flatMap {
    case List(from, to) => List(FromTo(from.value, to.value))
    case _              => Nil
  }
}