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

package org.pentaho.reporting.libraries.formula.function.userdefined;

import org.pentaho.reporting.libraries.formula.function.AbstractFunctionDescription;
import org.pentaho.reporting.libraries.formula.function.FunctionCategory;
import org.pentaho.reporting.libraries.formula.typing.Type;
import org.pentaho.reporting.libraries.formula.typing.coretypes.AnyType;
import org.pentaho.reporting.libraries.formula.typing.coretypes.TextType;

/**
 * Todo: Document me!
 * <p/>
 * Date: 02.07.2009
 * Time: 20:21:23
 *
 * @author Thomas Morgner.
 */
public class NormalizeArrayFunctionDescription extends AbstractFunctionDescription
{
  public NormalizeArrayFunctionDescription()
  {
    super("NORMALIZEARRAY", "org.pentaho.reporting.libraries.formula.function.userdefined.NormalizeArray-Function");
  }

  public Type getValueType()
  {
    return AnyType.ANY_ARRAY;
  }

  public FunctionCategory getCategory()
  {
    return UserDefinedFunctionCategory.CATEGORY;
  }

  public int getParameterCount()
  {
    return 1;
  }

  /**
   * Returns the parameter type at the given position using the function
   * metadata. The first parameter is at the position 0;
   *
   * @param position The parameter index.
   * @return The parameter type.
   */
  public Type getParameterType(final int position)
  {
    if (position == 0)
    {
      return AnyType.ANY_SEQUENCE;
    }

    return TextType.TYPE;
  }

  /**
   * Defines, whether the parameter at the given position is mandatory. A
   * mandatory parameter must be filled in, while optional parameters need
   * not to be filled in.
   *
   * @return
   */
  public boolean isParameterMandatory(final int position)
  {
    if (position == 0)
    {
      return true;
    }
    return false;
  }
}