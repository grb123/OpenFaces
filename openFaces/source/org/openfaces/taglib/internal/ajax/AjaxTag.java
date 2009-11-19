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
package org.openfaces.taglib.internal.ajax;

import org.openfaces.component.ajax.Ajax;
import org.openfaces.taglib.internal.AbstractComponentTag;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Ilya Musihin
 */
public class AjaxTag extends AbstractComponentTag {
    public String getComponentType() {
        return Ajax.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return "org.openfaces.AjaxRenderer";
    }

    @Override
    public void setComponentProperties(FacesContext facesContext, UIComponent component) {
        super.setComponentProperties(facesContext, component);
        setStringProperty(component, "event");
        setStringProperty(component, "for");
        setBooleanProperty(component, "standalone");

        setCollectionProperty(component, "render");
        setCollectionProperty(component, "execute");
        setBooleanProperty(component, "submitInvoker");
        setIntProperty(component, "delay");
        setBooleanProperty(component, "disableDefault");

        /*Ajax ajaxComponent = (Ajax) component;
        setActionListener(facesContext, ajaxComponent, "listener");*/
        setPropertyBinding(component, "listener");
        setBooleanProperty(component, "immediate");

        setStringProperty(component, "onerror");
        setStringProperty(component, "onajaxstart");
        setStringProperty(component, "onajaxend");
    }

}