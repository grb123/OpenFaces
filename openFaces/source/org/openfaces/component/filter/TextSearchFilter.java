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

import org.openfaces.util.ComponentUtil;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * @author Dmitry Pikhulya
 */
public abstract class TextSearchFilter extends Filter {

    public static final String SEARCH_COMPONENT_SUFFIX = "searchComponent";

    @Override
    public void createSubComponents(FacesContext context) {
        super.createSubComponents(context);
        ComponentUtil.createChildComponent(context, this, getInputComponentType(), SEARCH_COMPONENT_SUFFIX);
    }

    protected abstract String getInputComponentType();

    public UIComponent getSearchComponent() {
        List children = getChildren();
        if (children.size() != 1)
            throw new IllegalStateException("TextSearchFilter should have exactly one child component - " +
                    "the search component. children.size = " + children.size());
        UIComponent searchComponent = (UIComponent) children.get(0);
        return searchComponent;
    }
}