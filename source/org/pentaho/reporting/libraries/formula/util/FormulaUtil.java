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

package org.pentaho.reporting.libraries.formula.util;

import org.pentaho.reporting.libraries.formula.Formula;
import org.pentaho.reporting.libraries.formula.lvalues.LValue;
import org.pentaho.reporting.libraries.formula.lvalues.Term;
import org.pentaho.reporting.libraries.formula.lvalues.ContextLookup;
import org.pentaho.reporting.libraries.formula.parser.ParseException;
import org.pentaho.reporting.libraries.base.util.LinkedMap;

/**
 * Todo: Document me!
 * <p/>
 * Date: 15.04.2009
 * Time: 16:23:02
 *
 * @author Thomas Morgner.
 */
public class FormulaUtil
{
  private FormulaUtil()
  {

  }

  public static Object[] getReferences(final String formula) throws ParseException
  {
    final String formulaExpression;
    if (formula.length() > 0 && formula.charAt(0) == '=')
    {
//      formulaNamespace = "report";
      formulaExpression = formula.substring(1);
    }
    else
    {
      final int separator = formula.indexOf(':');
      if (separator <= 0 || ((separator + 1) == formula.length()))
      {
        // error: invalid formula.
//        formulaNamespace = null;
        formulaExpression = null;
      }
      else
      {
//        formulaNamespace = formula.substring(0, separator);
        formulaExpression = formula.substring(separator + 1);
      }
    }

    if (formulaExpression == null)
    {
      throw new ParseException("Formula is invalid");
    }
    return getReferences(new Formula(formulaExpression));
  }

  public static Object[] getReferences(final Formula formula)
  {
    final LinkedMap map = new LinkedMap();

    final LValue lValue = formula.getRootReference();
    collectReferences(lValue, map);
    return map.keys();
  }

  private static void collectReferences(final LValue lval, final LinkedMap map)
  {
    if (lval instanceof Term)
    {
      final Term t = (Term) lval;
      final LValue[] childValues = t.getChildValues();
      for (int i = 0; i < childValues.length; i++)
      {
        final LValue childValue = childValues[i];
        collectReferences(childValue, map);
      }
    }
    else if (lval instanceof ContextLookup)
    {
      final ContextLookup cl = (ContextLookup) lval;
      map.put(cl.getName(), Boolean.TRUE);
    }
  }
}
