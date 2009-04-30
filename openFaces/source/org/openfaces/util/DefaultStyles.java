/*
 * OpenFaces - JSF Component Library 2.0
 * Copyright (C) 2007-2009, TeamDev Ltd.
 * licensing@openfaces.org
 * Unless agreed in writing the contents of this file are subject to
 * the GNU Lesser General Public License Version 2.1 (the "LGPL" License).
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * Please visit http://openfaces.org/licensing/ for more details.
 */
package org.openfaces.util;

/**
 * @author Dmitry Pikhulya
 */
public class DefaultStyles {
    public static final String CLASS_INITIALLY_INVISIBLE = "o_initially_invisible";
    public static final String CLASS_CALENDAR_FONT_FAMILY = "o_dialogControlsFontFamily";
    public static final String CLASS_DROP_DOWN_LIST = "o_dialogControlsFontFamily";

    public static String getSelectionBackgroundColor() {
        if (EnvironmentUtil.isSafari() || EnvironmentUtil.isUndefinedBrowser()) {
            return "blue";
        } else {
            return "Highlight";
        }
    }

    public static String getSelectionTextColor() {
        if (EnvironmentUtil.isSafari() || EnvironmentUtil.isUndefinedBrowser()) {
            return "white";
        } else {
            return "HighlightText";
        }
    }

    public static String getBackgroundColor() {
        if (EnvironmentUtil.isSafari() || EnvironmentUtil.isUndefinedBrowser()) {
            return "white";
        } else {
            return "Window";
        }
    }

    public static String getBackgroundColorClass() {
        if (EnvironmentUtil.isSafari() || EnvironmentUtil.isUndefinedBrowser()) {
            return "o_background_color_safari";
        } else {
            return "o_background_color";
        }
    }

    public static String getPopupMenuBackgroundColorClass() {
        if (EnvironmentUtil.isSafari() || EnvironmentUtil.isUndefinedBrowser() || EnvironmentUtil.isOpera()) {
            return "o_popup_menu_background_safari";
        } else {
            return "o_popup_menu_background";
        }
    }

    public static String getTextColorClass() {
        if (EnvironmentUtil.isSafari() || EnvironmentUtil.isUndefinedBrowser()) {
            return "o_text_color_safari";
        } else {
            return "o_text_color";
        }
    }

    public static String getDefaultSelectionStyle() {
        return "color: " + getSelectionTextColor() + "; background: " + getSelectionBackgroundColor() + ";";
    }
}
