package pivotal.scala.wrapper.functions;

import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;

public abstract class ScalaFunctionAdapter extends FunctionAdapter {

  private static final long serialVersionUID = 1L;

  @Override
  public void execute(FunctionContext arg0) {
    executeScalaWrapper(arg0);

  }

  @Override
  public String getId() {
    return getIdScalaWrapper();
  }

  public abstract String getIdScalaWrapper();

  public abstract void executeScalaWrapper(FunctionContext arg0);

}
