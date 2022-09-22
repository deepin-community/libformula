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

package org.pentaho.reporting.libraries.formula;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.StringTokenizer;
import java.util.Collections;
import java.io.Serializable;

import org.pentaho.reporting.libraries.formula.typing.Type;
import org.pentaho.reporting.libraries.base.config.Configuration;

/**
 * Creation-Date: 03.11.2006, 14:28:12
 *
 * @author Thomas Morgner
 */
public class DefaultLocalizationContext implements LocalizationContext, Serializable
{
  private static final String CONFIG_TIMEZONE_KEY = "org.pentaho.reporting.libraries.formula.timezone";

  private static final String CONFIG_LOCALE_KEY = "org.pentaho.reporting.libraries.formula.locale";

  private static final String CONFIG_DATEFORMAT_KEY = "org.pentaho.reporting.libraries.formula.dateformat.";

  private ArrayList dateFormats;

  private ArrayList datetimeFormats;

  private ArrayList timeFormats;

  private Locale locale;

  private TimeZone timeZone;

  public DefaultLocalizationContext()
  {
    dateFormats = new ArrayList();
    datetimeFormats = new ArrayList();
    timeFormats = new ArrayList();
  }

  public Locale getLocale()
  {
    return locale;
  }

  public ResourceBundle getBundle(final String id)
  {
    return ResourceBundle.getBundle(id, getLocale());
  }

  public TimeZone getTimeZone()
  {
    return timeZone;
  }

  public List getDateFormats(final Type type)
  {
    if (type.isFlagSet(Type.DATE_TYPE))
    {
      return (List) dateFormats.clone();
    }
    else if (type.isFlagSet(Type.DATETIME_TYPE))
    {
      return (List) datetimeFormats.clone();
    }
    else if (type.isFlagSet(Type.TIME_TYPE))
    {
      return (List) timeFormats.clone();
    }
    return Collections.EMPTY_LIST;
  }

  private String[] createLocale(final String locale)
  {
    final StringTokenizer strtok = new StringTokenizer(locale, "_");
    final String[] retval = new String[3];
    if (strtok.hasMoreElements())
    {
      retval[0] = strtok.nextToken();
    }
    else
    {
      retval[0] = "";
    }
    if (strtok.hasMoreElements())
    {
      retval[1] = strtok.nextToken();
    }
    else
    {
      retval[1] = "";
    }
    if (strtok.hasMoreElements())
    {
      retval[2] = strtok.nextToken();
    }
    else
    {
      retval[2] = "";
    }
    return retval;
  }

  public void initialize(final Configuration config)
  {
    initialize(config, null, null);
  }

  public void initialize(final Configuration config, final Locale locale, final TimeZone timeZone)
  {
    if (config == null)
    {
      throw new NullPointerException();
    }

    if (locale == null)
    {
      // setting locale
      final String declaredLocale = config.getConfigProperty(CONFIG_LOCALE_KEY, Locale.getDefault().toString());
      final String[] declaredLocaleParts = createLocale(declaredLocale);
      this.locale = new Locale(declaredLocaleParts[0], declaredLocaleParts[1], declaredLocaleParts[2]);
    }
    else
    {
      this.locale = locale;
    }

    //setting timezone
    if (timeZone == null)
    {
      final String timeZoneId = config.getConfigProperty(CONFIG_TIMEZONE_KEY, TimeZone.getDefault().getID());
      this.timeZone = TimeZone.getTimeZone(timeZoneId);
    }
    else
    {
      this.timeZone = TimeZone.getDefault();
    }

    final Locale activeLocale = getLocale();
    datetimeFormats.add(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, activeLocale));
    dateFormats.add(DateFormat.getDateInstance(DateFormat.FULL, activeLocale));
    timeFormats.add(DateFormat.getTimeInstance(DateFormat.FULL, activeLocale));

    datetimeFormats.add(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, activeLocale));
    dateFormats.add(DateFormat.getDateInstance(DateFormat.LONG, activeLocale));
    timeFormats.add(DateFormat.getTimeInstance(DateFormat.LONG, activeLocale));

    datetimeFormats.add(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, activeLocale));
    dateFormats.add(DateFormat.getDateInstance(DateFormat.MEDIUM, activeLocale));
    timeFormats.add(DateFormat.getTimeInstance(DateFormat.MEDIUM, activeLocale));

    datetimeFormats.add(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, activeLocale));
    dateFormats.add(DateFormat.getDateInstance(DateFormat.SHORT, activeLocale));
    timeFormats.add(DateFormat.getTimeInstance(DateFormat.SHORT, activeLocale));

    // adding default dateformats using current local
    datetimeFormats.add(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, activeLocale));
    dateFormats.add(DateFormat.getDateInstance(DateFormat.SHORT, activeLocale));
    timeFormats.add(DateFormat.getTimeInstance(DateFormat.SHORT, activeLocale));

    // adding default ISO8 dateformats
    datetimeFormats.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US));
    datetimeFormats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US));
    
    dateFormats.add(new SimpleDateFormat("yyyy-MM-dd", Locale.US));
    timeFormats.add(new SimpleDateFormat("HH:mm:ss", Locale.US));
    timeFormats.add(new SimpleDateFormat("HH:mm", Locale.US));

    for (int i = 0; i < dateFormats.size(); i++)
    {
      final DateFormat dateFormat = (DateFormat) dateFormats.get(i);
      dateFormat.setLenient(false);
    }

    for (int i = 0; i < datetimeFormats.size(); i++)
    {
      final DateFormat dateFormat = (DateFormat) datetimeFormats.get(i);
      dateFormat.setLenient(false);
    }

    for (int i = 0; i < timeFormats.size(); i++)
    {
      final DateFormat dateFormat = (DateFormat) timeFormats.get(i);
      dateFormat.setLenient(false);
    }

  }
}
