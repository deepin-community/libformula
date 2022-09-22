package org.pentaho.reporting.libraries.formula.function.userdefined;

import java.util.ArrayList;

import org.pentaho.reporting.libraries.formula.EvaluationException;
import org.pentaho.reporting.libraries.formula.FormulaContext;
import org.pentaho.reporting.libraries.formula.LibFormulaErrorValue;
import org.pentaho.reporting.libraries.formula.function.Function;
import org.pentaho.reporting.libraries.formula.function.ParameterCallback;
import org.pentaho.reporting.libraries.formula.lvalues.TypeValuePair;
import org.pentaho.reporting.libraries.formula.typing.Sequence;
import org.pentaho.reporting.libraries.formula.typing.coretypes.AnyType;

/**
 * Todo: Document me!
 * <p/>
 * Date: 23.10.2009
 * Time: 11:43:28
 *
 * @author Thomas Morgner.
 */
public class NormalizeArrayFunction implements Function
{
  public NormalizeArrayFunction()
  {
  }

  public String getCanonicalName()
  {
    return "NORMALIZEARRAY";
  }

  public TypeValuePair evaluate(final FormulaContext context,
                                final ParameterCallback parameters) throws EvaluationException
  {
    final int parameterCount = parameters.getParameterCount();
    if (parameterCount != 1)
    {
      throw EvaluationException.getInstance(LibFormulaErrorValue.ERROR_ARGUMENTS_VALUE);
    }

    final Sequence sequence =
        context.getTypeRegistry().convertToSequence(parameters.getType(0), parameters.getValue(0));
    if (sequence == null)
    {
      throw EvaluationException.getInstance(LibFormulaErrorValue.ERROR_NA_VALUE);
    }

    final ArrayList retval = new ArrayList();
    while (sequence.hasNext())
    {
      final Object o = sequence.next();
      if (o != null)
      {
        retval.add(o);
      }
    }
    return new TypeValuePair(AnyType.ANY_ARRAY, retval.toArray());
  }
}
