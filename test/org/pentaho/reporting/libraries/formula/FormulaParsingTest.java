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

package org.pentaho.reporting.libraries.formula;

import junit.framework.TestCase;
import org.pentaho.reporting.libraries.formula.parser.ParseException;
import org.pentaho.reporting.libraries.formula.lvalues.Term;

/**
 * Todo: Document Me
 *
 * @author Thomas Morgner
 */
public class FormulaParsingTest extends TestCase
{
  public FormulaParsingTest()
  {
    super();
  }

  public FormulaParsingTest(final String s)
  {
    super(s);
  }

  protected void setUp() throws Exception
  {
    LibFormulaBoot.getInstance().start();
  }

  public void testTermOperands() throws ParseException, EvaluationException
  {
    final Formula formula = new Formula("\"a\" & \"b\" & \"c\"");
    formula.initialize(new DefaultFormulaContext());
    Term term = (Term) formula.getRootReference();
    term.getOperands();
  }

  public void testParseWithLineBreaks() throws ParseException, EvaluationException
  {
    final Formula formula = new Formula("\"aaa\" \n&\n \"bb\" & \"\n\" & \"\"");
    formula.initialize(new DefaultFormulaContext());
    final Object o = formula.evaluate();
    assertEquals("Formula value", "aaabb\n", o);
  }

  public void testParse() throws ParseException, EvaluationException
  {
    final Formula formula = new Formula("MID(UPPER([name] & \n\r" +
        "   \" \" \n\r" +
        "   & [firstname]);5;10)");
    final DefaultFormulaContext context = new DefaultFormulaContext();
    context.defineReference("name", "name");
    context.defineReference("firstname", "firstname");
    formula.initialize(context);
    final Object o = formula.evaluate();
    assertEquals("Formula value", " FIRSTNAME", o);
  }

  public void testEscapes() throws Exception
  {
    final Formula formula = new Formula("T(\"\\\") = \"A\"");
    final DefaultFormulaContext context = new DefaultFormulaContext();
    context.defineReference("path", "x");
    formula.initialize(context);
    final Object o = formula.evaluate();
    assertEquals("Formula value", Boolean.FALSE, o);
  }

  public void testQuotedReference() throws Exception
  {
    final Formula formula = new Formula("T([\"\\\\\"]) = \"A\"");
    final DefaultFormulaContext context = new DefaultFormulaContext();
    context.defineReference("path", "x");
    formula.initialize(context);
    final Object o = formula.evaluate();
    assertEquals("Formula value", Boolean.FALSE, o);
  }

  public void testQuotedReference2() throws Exception
  {
    final Formula formula = new Formula("T([\"[x]\"]) = \"x\"");
    final DefaultFormulaContext context = new DefaultFormulaContext();
    context.defineReference("[x]", "x");
    formula.initialize(context);
    final Object o = formula.evaluate();
    assertEquals("Formula value", Boolean.TRUE, o);
  }
}
