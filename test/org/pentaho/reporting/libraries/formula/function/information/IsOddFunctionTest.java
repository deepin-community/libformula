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
 * Copyright (c) 2006 - 2009 Pentaho Corporation and Contributors.  All rights reserved.
 */

package org.pentaho.reporting.libraries.formula.function.information;

import org.pentaho.reporting.libraries.formula.FormulaTestBase;
import org.pentaho.reporting.libraries.formula.LibFormulaErrorValue;

/**
 * @author Cedric Pronzato
 */
public class IsOddFunctionTest extends FormulaTestBase
{
  public void testDefault() throws Exception
  {
    runDefaultTest();
  }

  public Object[][] createDataTest()
  {
    return new Object[][]
        {
            {"ISODD(3)", Boolean.TRUE},
            {"ISODD(5)", Boolean.TRUE},
            {"ISODD(3.1)", Boolean.TRUE},
            {"ISODD(3.5)", Boolean.TRUE},
            {"ISODD(3.9)", Boolean.TRUE},
            {"ISODD(4)", Boolean.FALSE},
            {"ISODD(4.9)", Boolean.FALSE},
            {"ISODD(-3)", Boolean.TRUE},
            {"ISODD(-3.1)", Boolean.TRUE},
            {"ISODD(-3.5)", Boolean.TRUE},
            {"ISODD(-3.9)", Boolean.TRUE},
            {"ISODD(-4)", Boolean.FALSE},
            {"ISODD(NA())", LibFormulaErrorValue.ERROR_NA_VALUE},
            {"ISODD(0)", Boolean.FALSE},
            {"ISODD(1)", Boolean.TRUE},
            {"ISODD(2)", Boolean.FALSE},
            {"ISODD(2.9)", Boolean.FALSE},};
  }

}
