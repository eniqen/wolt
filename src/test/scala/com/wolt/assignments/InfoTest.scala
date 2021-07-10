package com.wolt.assignments

import cats.data.Chain
import com.wolt.assignment.enums.Status.{Close, Open}
import com.wolt.assignment.model.{FromTo, Info, TimeValue}
import eu.timepit.refined.api.Refined
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

/**
 * @author Mikhail Nemenko {@literal <nemenkoma@gmail.com>}
 */
class InfoTest extends AnyFlatSpec with Matchers {

  it should "convert list of infos to FromTo" in {
    //given
    val t1 = TimeValue(Refined.unsafeApply(1))
    val t2 = TimeValue(Refined.unsafeApply(2))
    val t3 = TimeValue(Refined.unsafeApply(3))
    val t4 = TimeValue(Refined.unsafeApply(4))

    val infos = List(
      Info(Open, t1),
      Info(Close, t2),
      Info(Open, t3),
      Info(Close, t4)
    )

    val expectedResult = List(
      FromTo(infos(0).value, infos(1).value),
      FromTo(infos(2).value, infos(3).value)
    )

    //when
    val convertedResult = Info.toFromTo(Chain.fromSeq(infos))

    //then
    convertedResult should contain theSameElementsAs expectedResult
  }

  it should "it should return only one pair on time in odd case" in {
    //given
    val t1   = TimeValue(Refined.unsafeApply(1))
    val t2   = TimeValue(Refined.unsafeApply(2))
    val t3   = TimeValue(Refined.unsafeApply(3))
    val infos = List(
      Info(Open, t1),
      Info(Close, t2),
      Info(Open, t3),
    )

    val expectedResult = List(
      FromTo(infos(0).value, infos(1).value)
    )

    //when
    val convertedResult = Info.toFromTo(Chain.fromSeq(infos))

    //then
    convertedResult should contain theSameElementsAs expectedResult

  }
}
