package org.pentaho.reporting.libraries.formula.function.text;

import java.text.MessageFormat;

import org.pentaho.reporting.libraries.formula.function.Function;
import org.pentaho.reporting.libraries.formula.function.ParameterCallback;
import org.pentaho.reporting.libraries.formula.lvalues.TypeValuePair;
import org.pentaho.reporting.libraries.formula.FormulaContext;
import org.pentaho.reporting.libraries.formula.EvaluationException;
import org.pentaho.reporting.libraries.formula.LibFormulaErrorValue;
import org.pentaho.reporting.libraries.formula.typing.Type;
import org.pentaho.reporting.libraries.formula.typing.coretypes.TextType;

/**
 * Todo: Document me!
 * <p/>
 * Date: 11.11.2009
 * Time: 13:02:29
 *
 * @author Thomas Morgner.
 */
public class MessageFunction implements Function
{
  public String getCanonicalName()
  {
    return "MESSAGE";
  }

  public TypeValuePair evaluate(final FormulaContext context, final ParameterCallback parameters) throws EvaluationException
  {
    final int parameterCount = parameters.getParameterCount();
    if (parameterCount < 1)
    {
      throw EvaluationException.getInstance(LibFormulaErrorValue.ERROR_ARGUMENTS_VALUE);
    }
    final Type type1 = parameters.getType(0);
    final Object value1 = parameters.getValue(0);
    final String message = context.getTypeRegistry().convertToText(type1, value1);

    final MessageFormat format = new MessageFormat(message, context.getLocalizationContext().getLocale());
    final Object[] args = new Object[parameterCount - 1];
    for (int i = 1; i < parameterCount; i += 1)
    {
      args[i - 1] = parameters.getValue(i);
    }
    return new TypeValuePair(TextType.TYPE, format.format(args));
  }
}
