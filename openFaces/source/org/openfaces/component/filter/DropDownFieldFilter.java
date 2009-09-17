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
package org.openfaces.component.filter;

import org.openfaces.component.input.DropDownField;
import org.openfaces.component.input.DropDownItems;
import org.openfaces.util.ComponentUtil;

import javax.faces.context.FacesContext;

/**
 * @author Dmitry Pikhulya
 */
public class DropDownFieldFilter extends TextSearchFilter {
    public static final String COMPONENT_TYPE = "org.openfaces.DropDownFieldFilter";
    public static final String COMPONENT_FAMILY = "org.openfaces.DropDownFieldFilter";

    public DropDownFieldFilter() {
        setRendererType("org.openfaces.DropDownFieldFilterRenderer");
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    @Override
    protected boolean isShowingPredefinedCriterionNames() {
        return true;
    }

    protected String getInputComponentType() {
        return DropDownField.COMPONENT_TYPE;
    }

    @Override
    public void createSubComponents(FacesContext context) {
        super.createSubComponents(context);
        ComponentUtil.createChildComponent(context, getSearchComponent(), DropDownItems.COMPONENT_TYPE, "dropdownItems");
    }
}