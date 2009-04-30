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
package org.openfaces.renderkit.chart;

import org.openfaces.component.chart.Chart;
import org.openfaces.component.chart.ChartView;
import org.openfaces.component.chart.impl.JfcRenderHints;
import org.openfaces.component.chart.impl.helpers.MapRenderUtilities;
import org.openfaces.component.output.DynamicImage;
import org.openfaces.component.output.ImageType;
import org.openfaces.renderkit.RendererBase;
import org.openfaces.util.RenderingUtil;
import org.openfaces.util.ResourceUtil;
import org.openfaces.renderkit.output.DynamicImageRenderer;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author Ekaterina Shliakhovetskaya
 */
public class ChartRenderer extends RendererBase {

    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
        RenderingUtil.generateIdIfNotSpecified(component);
        ResponseWriter writer = facesContext.getResponseWriter();
        Chart chart = (Chart) component;

        ChartView view = chart.getChartView();
        if (!chart.isRendered() || view == null)
            return;

        writer.startElement("div", chart);
        writeIdAttribute(facesContext, chart);
        RenderingUtil.writeComponentClassAttribute(writer, chart);

        String actionFiledId = chart.getClientId(facesContext) + MapRenderUtilities.ACTION_FIELD_SUFFIX;
        writeNewLine(writer);
        RenderingUtil.renderHiddenField(writer, actionFiledId, null);
        writeNewLine(writer);

        final byte[] imageAsByteArray = view.renderAsImageFile();

        chart.setImageBytes(imageAsByteArray);

        DynamicImage dynamicImage = new DynamicImage();
        ValueExpression ve = new ValueExpression() {
            public Object getValue(ELContext elContext) {
                return imageAsByteArray;
            }

            public void setValue(ELContext elContext, Object value) {
                throw new UnsupportedOperationException("Could not change 'data' property using ValueExpression");
            }

            public boolean isReadOnly(ELContext elContext) {
                return true;
            }

            public Class getType(ELContext elContext) {
                if (imageAsByteArray == null)
                    return Object.class;
                return imageAsByteArray.getClass();
            }

            public Class getExpectedType() {
                return Object.class;
            }

            public String getExpressionString() {
                return null;
            }

            public boolean equals(Object o) {
                return false;
            }

            public int hashCode() {
                return 0;
            }

            public boolean isLiteralText() {
                return false;
            }
        };
        dynamicImage.setValueExpression("data", ve);
        dynamicImage.setId(chart.getId() + RenderingUtil.SERVER_ID_SUFFIX_SEPARATOR + "img");

        JfcRenderHints jfcRenderHints = chart.getRenderHints();
        dynamicImage.setMapId(jfcRenderHints.getMapId(chart));
        String map = jfcRenderHints.getMap();
        dynamicImage.setMap(map);
        dynamicImage.getAttributes().put(DynamicImageRenderer.DEFAULT_STYLE_ATTR, "o_chart");
        dynamicImage.setWidth(chart.getWidth());
        dynamicImage.setHeight(chart.getHeight());
        copyAttributes(dynamicImage, chart, "onclick", "ondblclick", "onmousedown", "onmouseup",
                "onmousemove", "onmouseover", "onmouseout");

        dynamicImage.setImageType(ImageType.PNG);
        dynamicImage.encodeBegin(facesContext);
        dynamicImage.encodeEnd(facesContext);
        if (map != null)
            ResourceUtil.renderJSLinkIfNeeded(ResourceUtil.getUtilJsURL(facesContext), facesContext);
        writer.endElement("div");
    }

    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
        Map requestParameterMap = context.getExternalContext().getRequestParameterMap();

        Chart chart = (Chart) component;
        String name = chart.getClientId(FacesContext.getCurrentInstance()) + MapRenderUtilities.ACTION_FIELD_SUFFIX;
        String actionValue = (String) requestParameterMap.get(name);

        if (actionValue != null && !actionValue.equals("")) {
            if (actionValue.equals("title")) {
                //   chart.setAction(chart.getTitle().getActionExpression());
                chart.getTitle().decodeAction(actionValue);
            } else {
                //   chart.setAction(chart.getChartView().getActionExpression());
                chart.getChartView().decodeAction(actionValue);
                // chart.getChartView().queueEvent(event);
            }
        }
        chart.setModel(null);
    }

    private void copyAttributes(UIComponent dest, UIComponent src, String... attributeNames) {
        for (String attributeName : attributeNames) {
            Object attributeValue = src.getAttributes().get(attributeName);
            dest.getAttributes().put(attributeName, attributeValue);
        }
    }

}
