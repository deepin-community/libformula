/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2009 Pentaho Corporation.  All rights reserved.
 */

package org.pentaho.reporting.libraries.formula.function.information;

import java.math.BigDecimal;

import org.pentaho.reporting.libraries.formula.EvaluationException;
import org.pentaho.reporting.libraries.formula.FormulaContext;
import org.pentaho.reporting.libraries.formula.LibFormulaErrorValue;
import org.pentaho.reporting.libraries.formula.function.Function;
import org.pentaho.reporting.libraries.formula.function.ParameterCallback;
import org.pentaho.reporting.libraries.formula.lvalues.TypeValuePair;
import org.pentaho.reporting.libraries.formula.typing.Sequence;
import org.pentaho.reporting.libraries.formula.typing.Type;
import org.pentaho.reporting.libraries.formula.typing.coretypes.NumberType;

/**
 * This function counts the number of non-empty values in the list of AnySequences provided. A value is non-blank if it
 * contains any content of any type, including an error.
 *
 * @author Cedric Pronzato
 */
public class CountAFunction implements Function
{

  public CountAFunction()
  {
  }

  public String getCanonicalName()
  {
    return "COUNTA";
  }

  public TypeValuePair evaluate(final FormulaContext context,
                                final ParameterCallback parameters) throws EvaluationException
  {
    final int parameterCount = parameters.getParameterCount();

    if (parameterCount == 0)
    {
      throw EvaluationException.getInstance(LibFormulaErrorValue.ERROR_ARGUMENTS_VALUE);
    }

    int count = 0;

    for (int paramIdx = 0; paramIdx < parameterCount; paramIdx++)
    {
      try
      {
        final Type type = parameters.getType(paramIdx);
        final Object value = parameters.getValue(paramIdx);
        final Sequence sequence = context.getTypeRegistry().convertToSequence(type, value);

        while (sequence.hasNext())
        {
          final Object o = sequence.next();
          if (o != null)
          {
            count++;
          }
        }
      }
      catch (EvaluationException e)
      {
        count++;
      }
    }

    return new TypeValuePair(NumberType.GENERIC_NUMBER, new BigDecimal(count));
  }
}