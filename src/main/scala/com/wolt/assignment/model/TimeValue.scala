package com.wolt.assignment.model

import com.wolt.assignment.model.TimeValue.TimeValuePred
import eu.timepit.refined.api.Refined
import eu.timepit.refined.predicates.all.{And, Greater, Less, Not}

/**
 * @author Mikhail Nemenko {@literal <nemenkoma@gmail.com>}
 */
final case class TimeValue(value: TimeValuePred) {

}

object TimeValue {
  type TimeValueExpr = Not[Less[0]] And Not[Greater[86399]]
  type TimeValuePred = Int Refined TimeValueExpr

  def convert(timeValue: TimeValue): String = {
    val m      = (timeValue.value.value/60) % 60
    val h      = (timeValue.value.value/60/60) % 24
    val pmOrAm = if(h >= 12) "PM" else "AM"
    "%1d:%02d %s".format(if(h % 12 == 0) 12 else h % 12, m, pmOrAm)
  }
}
