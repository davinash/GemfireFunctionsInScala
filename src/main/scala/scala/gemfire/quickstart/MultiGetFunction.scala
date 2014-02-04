package scala.gemfire.quickstart

import scala.collection.JavaConversions._
import com.gemstone.gemfire.cache.execute.FunctionAdapter
import com.gemstone.gemfire.cache.CacheFactory
import com.gemstone.gemfire.cache.execute.RegionFunctionContext
import com.gemstone.gemfire.cache.Region
import com.gemstone.gemfire.cache.Declarable
import com.gemstone.gemfire.cache.execute.FunctionContext
import com.gemstone.gemfire.cache.partition.PartitionRegionHelper
import pivotal.scala.wrapper.functions.ScalaFunctionAdapter

class MultiGetFunction extends ScalaFunctionAdapter {

  def getIdScalaWrapper: String = this.getClass.getName

  def executeScalaWrapper(fc: FunctionContext): Unit = {
    fc match {
      case frc: RegionFunctionContext => {
        val context = frc.asInstanceOf[RegionFunctionContext]
        val keys = context.getFilter()
        var keysTillSecondLast = new scala.collection.mutable.HashSet[Any]()
        keysTillSecondLast.addAll(keys.take(keys.size - 1))
        keysTillSecondLast.foreach { key =>
          context.getResultSender().
            sendResult(PartitionRegionHelper.
              getLocalDataForContext(context).get(key));
        }
        context.getResultSender().
          lastResult(PartitionRegionHelper.
            getLocalDataForContext(context).get(keys.last));
      }
      case _ => {
        fc.getResultSender().
          lastResult(Runtime.getRuntime().freeMemory() / (1024 * 1024));
      }
    }
  }

}