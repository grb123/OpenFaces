package org.openfaces.renderkit.filter;

import org.openfaces.component.filter.CompositeFilter;
import org.openfaces.org.json.JSONException;
import org.openfaces.org.json.JSONObject;
import org.openfaces.renderkit.AjaxPortionRenderer;
import org.openfaces.renderkit.RendererBase;
import org.openfaces.renderkit.input.DateChooserRenderer;
import org.openfaces.renderkit.input.DropDownComponentRenderer;
import org.openfaces.util.AjaxUtil;
import org.openfaces.util.ComponentUtil;
import org.openfaces.util.ResourceUtil;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * @author Natalia Zolochevska
 */
public class CompositeFilterRenderer extends RendererBase implements AjaxPortionRenderer {

    private static final String NO_FILTER_ROW_SUFFIX = "no_filter";    
    private static final String NO_FILTER_TEXT_SUFFIX = "text";
    private static final String DEFAULT_NO_FILTER_TEXT_CLASS = "o_filter_row_item o_filter_no_text";


    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (!component.isRendered()) return;

        CompositeFilter compositeFilter = (CompositeFilter) component;
        compositeFilter._processUpdates(context);
        
        super.encodeBegin(context, component);

        AjaxUtil.prepareComponentForAjax(context, component);

        encodeJsLinks(context);


        compositeFilter.synchronizeFilterRowsWithCriterions();
        ResponseWriter writer = context.getResponseWriter();
        String clientId = compositeFilter.getClientId(context);
        writer.startElement("div", compositeFilter);
        writer.writeAttribute("id", clientId, "id");
        writer.flush();
    }

    private void encodeNoFilterRow(FacesContext context, CompositeFilter compositeFilter) throws IOException {
        HtmlPanelGroup rowContainer = (HtmlPanelGroup) ComponentUtil.getChildBySuffix(compositeFilter, NO_FILTER_ROW_SUFFIX);
        if (rowContainer == null) {
            rowContainer = (HtmlPanelGroup) ComponentUtil.createChildComponent(context, compositeFilter, HtmlPanelGroup.COMPONENT_TYPE, NO_FILTER_ROW_SUFFIX);
            rowContainer.setLayout("block");
            rowContainer.setStyleClass(FilterRow.DEFAULT_ROW_CLASS);
            //rowContainer.setValueExpression("rendered", compositeFilter.getNoFilterRowRendererExpression());

            HtmlOutputText noFilterText = (HtmlOutputText) ComponentUtil.createChildComponent(context, rowContainer, HtmlOutputText.COMPONENT_TYPE, NO_FILTER_TEXT_SUFFIX);
            noFilterText.setStyleClass(DEFAULT_NO_FILTER_TEXT_CLASS);
            noFilterText.setValue(compositeFilter.getNoFilterMessage());

            HtmlPanelGroup addButtonContainer = (HtmlPanelGroup) ComponentUtil.createChildComponent(context, rowContainer, HtmlPanelGroup.COMPONENT_TYPE, FilterRow.ADD_BUTTON_CONTAINER_SUFFIX);
            addButtonContainer.setStyleClass(FilterRow.DEFAULT_ROW_ITEM_CLASS);
            HtmlCommandButton addButton = (HtmlCommandButton) ComponentUtil.createChildComponent(context, addButtonContainer, HtmlCommandButton.COMPONENT_TYPE, FilterRow.BUTTON_SUFFIX);
            addButton.setValue("+");
            addButton.setOnclick("O$.Filter.add('" + compositeFilter.getClientId(context) + "'); return false;");
            addButton.setStyleClass(FilterRow.DEFAULT_ADD_BUTTON_CLASS);
        }
        rowContainer.encodeAll(context);
    }

    private void encodeJsLinks(FacesContext context) throws IOException {
        String[] libs = getNecessaryJsLibs(context);
        for (String lib : libs) {
            ResourceUtil.renderJSLinkIfNeeded(lib, context);
        }
    }

    public static String getFilterJsURL(FacesContext facesContext) {
        return ResourceUtil.getInternalResourceURL(facesContext, CompositeFilterRenderer.class, "filter.js");
    }

    private String[] getNecessaryJsLibs(FacesContext context) {
        return new String[]{
                ResourceUtil.getUtilJsURL(context),
                ResourceUtil.getJsonJsURL(context),
                ResourceUtil.getInternalResourceURL(context, DropDownComponentRenderer.class, "dropdown.js"),
                ResourceUtil.getInternalResourceURL(context, DateChooserRenderer.class, "dateChooser.js"),
                getFilterJsURL(context)};
    }


    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (!component.isRendered()) return;
        super.encodeEnd(context, component);

        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div");
        writer.flush();
    }


    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        if (!component.isRendered()) return;
        CompositeFilter compositeFilter = (CompositeFilter) component;        
        if (compositeFilter.isEmpty()) {
            encodeNoFilterRow(context, compositeFilter);
        } else {
            for (FilterRow filterRow : compositeFilter.getFilterRows()) {
                filterRow.encodeRow(context, compositeFilter);
            }
        }
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    public JSONObject encodeAjaxPortion(FacesContext context, UIComponent component, String portionName, JSONObject jsonParam) throws IOException, JSONException {
        CompositeFilter compositeFilter = (CompositeFilter) component;
        compositeFilter._processUpdates(context);
        String operation = (String) jsonParam.get("operation");
        if (operation == null) {
        } else if (operation.equals("add")) {
            FilterRow filterRow = compositeFilter.addFilterRow();
            filterRow.encodeRow(context, compositeFilter);
        } else if (operation.equals("clear")) {
            compositeFilter.clear();
            encodeNoFilterRow(context, compositeFilter);
        } else if (operation.equals("remove")) {
            int index = (Integer) jsonParam.get("index");
            compositeFilter.removeFilterRow(index);
            if (compositeFilter.isEmpty()) {
                encodeNoFilterRow(context, compositeFilter);
            }
        } else if (operation.equals("propertyChange")) {
            int index = (Integer) jsonParam.get("index");
            FilterRow filterRow = compositeFilter.getFilterRow(index);
            filterRow.encodeOperationSelector(context, compositeFilter);
        } else if (operation.equals("operationChange")) {
            int index = (Integer) jsonParam.get("index");
            FilterRow filterRow = compositeFilter.getFilterRow(index);
            filterRow.encodeParametersEditor(context, compositeFilter);
        }
        return null;
    }
}