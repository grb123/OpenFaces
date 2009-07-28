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
package org.openfaces.taglib.jsp.input;

import javax.el.ValueExpression;

import org.openfaces.taglib.internal.input.SelectBooleanCheckboxTag;
import org.openfaces.taglib.jsp.UIInputJspTag;

/**
 * @author Roman Porotnikov
 */
public class SelectBooleanCheckboxJspTag extends UIInputJspTag {

    public SelectBooleanCheckboxJspTag() {
        super(new SelectBooleanCheckboxTag());
    }

    public void setAccesskey(ValueExpression accesskey) {
        getDelegate().setPropertyValue("accesskey", accesskey);
    }

    public void setTabindex(ValueExpression tabindex) {
        getDelegate().setPropertyValue("tabindex", tabindex);
    }

    public void setTitle(ValueExpression title) {
        getDelegate().setPropertyValue("title", title);
    }

    public void setDir(ValueExpression dir) {
        getDelegate().setPropertyValue("dir", dir);
    }

    public void setLang(ValueExpression lang) {
        getDelegate().setPropertyValue("lang", lang);
    }

    public void setOnselect(ValueExpression onselect) {
        getDelegate().setPropertyValue("onselect", onselect);
    }

    public void setDisabled(ValueExpression disabled) {
        getDelegate().setPropertyValue("disabled", disabled);
    }

    public void setDisabledStyle(ValueExpression disabledStyle) {
        getDelegate().setPropertyValue("disabledStyle", disabledStyle);
    }

    public void setDisabledClass(ValueExpression disabledClass) {
        getDelegate().setPropertyValue("disabledClass", disabledClass);
    }

    public void setTriStateAllowed(ValueExpression triStateAllowed) {
        getDelegate().setPropertyValue("triStateAllowed", triStateAllowed);
    }

    public void setSelectedImageUrl(ValueExpression selectedImageUrl) {
        getDelegate().setPropertyValue("selectedImageUrl", selectedImageUrl);
    }

    public void setUnselectedImageUrl(ValueExpression unselectedImageUrl) {
        getDelegate().setPropertyValue("unselectedImageUrl", unselectedImageUrl);
    }

    public void setUndefinedImageUrl(ValueExpression undefinedImageUrl) {
        getDelegate().setPropertyValue("undefinedImageUrl", undefinedImageUrl);
    }

    public void setRolloverSelectedImageUrl(ValueExpression rolloverSelectedImageUrl) {
        getDelegate().setPropertyValue("rolloverSelectedImageUrl", rolloverSelectedImageUrl);
    }

    public void setRolloverUnselectedImageUrl(ValueExpression rolloverUnselectedImageUrl) {
        getDelegate().setPropertyValue("rolloverUnselectedImageUrl", rolloverUnselectedImageUrl);
    }

    public void setRolloverUndefinedImageUrl(ValueExpression rolloverUndefinedImageUrl) {
        getDelegate().setPropertyValue("rolloverUndefinedImageUrl", rolloverUndefinedImageUrl);
    }

    public void setPressedSelectedImageUrl(ValueExpression pressedSelectedImageUrl) {
        getDelegate().setPropertyValue("pressedSelectedImageUrl", pressedSelectedImageUrl);
    }

    public void setPressedUnselectedImageUrl(ValueExpression pressedUnselectedImageUrl) {
        getDelegate().setPropertyValue("pressedUnselectedImageUrl", pressedUnselectedImageUrl);
    }

    public void setPressedUndefinedImageUrl(ValueExpression pressedUndefinedImageUrl) {
        getDelegate().setPropertyValue("pressedUndefinedImageUrl", pressedUndefinedImageUrl);
    }

    public void setDisabledSelectedImageUrl(ValueExpression disabledSelectedImageUrl) {
        getDelegate().setPropertyValue("disabledSelectedImageUrl", disabledSelectedImageUrl);
    }

    public void setDisabledUnselectedImageUrl(ValueExpression disabledUnselectedImageUrl) {
        getDelegate().setPropertyValue("disabledUnselectedImageUrl", disabledUnselectedImageUrl);
    }

    public void setDisabledUndefinedImageUrl(ValueExpression disabledUndefinedImageUrl) {
        getDelegate().setPropertyValue("disabledUndefinedImageUrl", disabledUndefinedImageUrl);
    }

    public void setSelectedStyle(ValueExpression selectedStyle) {
        getDelegate().setPropertyValue("selectedStyle", selectedStyle);
    }

    public void setSelectedClass(ValueExpression selectedClass) {
        getDelegate().setPropertyValue("selectedClass", selectedClass);
    }

    public void setUnselectedStyle(ValueExpression unselectedStyle) {
        getDelegate().setPropertyValue("unselectedStyle", unselectedStyle);
    }

    public void setUnselectedClass(ValueExpression unselectedClass) {
        getDelegate().setPropertyValue("unselectedClass", unselectedClass);
    }

}