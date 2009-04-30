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
package org.openfaces.taglib.internal.chart;

import org.openfaces.component.chart.ChartTitle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Pavel Kaplin
 */
public class ChartTitleTag extends AbstractStyledComponentTag {
    public void setComponentProperties(FacesContext facesContext, UIComponent component) {
        super.setComponentProperties(facesContext, component);

        setStringProperty(component, "text");
        setStringProperty(component, "url");
        setStringProperty(component, "tooltip");

        ChartTitle chartTitle = (ChartTitle) component;
        setActionProperty(facesContext, chartTitle);
        setActionListener(facesContext, chartTitle);
    }

    public String getComponentType() {
        return "org.openfaces.ChartTitle";
    }

    public String getRendererType() {
        return null;
    }
}
