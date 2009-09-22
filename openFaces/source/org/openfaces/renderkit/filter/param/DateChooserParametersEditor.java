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

package org.openfaces.renderkit.filter.param;

import org.openfaces.component.filter.CompositeFilter;
import org.openfaces.component.filter.FilterProperty;
import org.openfaces.component.filter.OperationType;
import org.openfaces.component.input.DateChooser;
import org.openfaces.util.ComponentUtil;
import org.openfaces.renderkit.filter.FilterRow;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.TimeZone;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class DateChooserParametersEditor extends ParametersEditor implements Serializable {

    private static final String DATE_CHOOSER_ID_SUFFIX = "dateChooser";

    public DateChooserParametersEditor() {
    }

    public DateChooserParametersEditor(FilterProperty filterProperty, OperationType operation) {
        super(filterProperty, operation);
    }

    private DateChooser getDateChooser(FacesContext context, UIComponent container) {
        return (DateChooser) ComponentUtil.getChildBySuffix(container, DATE_CHOOSER_ID_SUFFIX);
    }

    private DateChooser createDateChooser(FacesContext context, UIComponent container) {
        clearContainer(container);
        DateChooser dateChooser = (DateChooser) ComponentUtil.createChildComponent(context, container, DateChooser.COMPONENT_TYPE, DATE_CHOOSER_ID_SUFFIX);
        dateChooser.setStyleClass(FilterRow.DEFAULT_PARAMETER_CLASS);
        dateChooser.setStyle("width: 90px !important;");
        return dateChooser;
    }

    private void initDateChooser(FacesContext context, DateChooser dateChooser) {
        dateChooser.setValue(criterion.getParameter());
        dateChooser.setTimeZone(filterProperty.getTimeZone());
        dateChooser.setPattern(filterProperty.getPattern());        
    }

    public void prepare(FacesContext context, CompositeFilter compositeFilter, FilterRow filterRow, UIComponent container) {
        super.prepare(context, compositeFilter, filterRow, container);
        DateChooser dateChooser = getDateChooser(context, container);
        if (dateChooser == null) {
            dateChooser = createDateChooser(context, container);
        }
        initDateChooser(context, dateChooser);
    }

    public void update(FacesContext context, CompositeFilter compositeFilter, FilterRow filterRow, UIComponent container) {
        DateChooser dateChooser = getDateChooser(context, container);
        if (dateChooser == null) {
            return;
        }
        Date param1 = (Date) dateChooser.getValue();
        TimeZone param2 = filterProperty.getTimeZone();
        List<Object> parameters = new ArrayList<Object>(2);
        parameters.add(param1);
        parameters.add(param2);
        criterion.setParameters(parameters);
    }

}