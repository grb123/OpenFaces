package org.openfaces.renderkit.filter;

import org.openfaces.component.filter.CompositeFilter;
import org.openfaces.component.filter.FilterProperty;
import org.openfaces.component.filter.OperationType;
import org.openfaces.component.filter.criterion.PropertyFilterCriterion;
import org.openfaces.component.input.DropDownField;
import org.openfaces.component.input.DropDownItems;
import org.openfaces.renderkit.filter.param.ParametersEditor;
import org.openfaces.util.ComponentUtil;
import org.openfaces.util.RenderingUtil;
import org.openfaces.util.ValueExpressionImpl;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;

/**
 * @author Natalia Zolochevska
 */
public class FilterRow implements Serializable {

    public static final String ROW_ID_SUFFIX = "row";
    public static final String ADD_BUTTON_CONTAINER_SUFFIX = "add";
    public static final String DELETE_BUTTON_CONTAINER_SUFFIX = "delete";
    public static final String BUTTON_SUFFIX = "button";
    public static final String CHECKBOX_SUFFIX = "checkbox";
    public static final String LABEL_SUFFIX = "label";
    public static final String INVERSE_CHECKBOX_CONTAINER_SUFFIX = "inverse";
    public static final String PROPERTY_SELECTOR_ID_SUFFIX = "propertySelector";
    public static final String OPERATION_SELECTOR_ID_SUFFIX = "operationSelector";
    public static final String PARAMETERS_EDITOR_ID_SUFFIX = "parametersEditor";
    public static final String DROP_DOWN_ID_SUFFIX = "dropDown";
    public static final String DROP_DOWN_ITEMS_ID_SUFFIX = "items";


    public static final String DEFAULT_ROW_CLASS = "o_filter_row";
    public static final String DEFAULT_ROW_ITEM_CLASS = "o_filter_row_item";
    public static final String DEFAULT_ROW_ITEM_CHECKBOX_CLASS = "o_filter_row_item o_filter_row_item_checkbox";
    public static final String DEFAULT_ROW_ITEM_INPUT_CLASS = "o_filter_row_item o_filter_row_item_input";
    public static final String DEFAULT_PROPERTY_CLASS = "o_filter_property";
    public static final String DEFAULT_OPERATION_CLASS = "o_filter_operation";
    public static final String DEFAULT_PARAMETER_CLASS = "o_filter_parameter";
    public static final String DEFAULT_ADD_BUTTON_CLASS = "o_filter_add_button";
    public static final String DEFAULT_DELETE_BUTTON_CLASS = "o_filter_delete_button";
    public static final String DEFAULT_INVERSE_CHECKBOX_CLASS = "o_filter_inverse_checkbox";
    public static final String DEFAULT_INVERSE_LABEL_CLASS = "o_filter_inverse_label";


    public static final String INVERSE_LABEL = "not";


    private final String rowIdSuffix;
    private final int index;

    private boolean inverse;
    private FilterProperty property;
    private OperationType operation;
    private ParametersEditor parametersEditor;
    private ParametersEditor.ParameterEditorType parametersEditorType;

    private PropertyFilterCriterion criterion;

    private boolean lastRow;
    private boolean firstRow;


    public FilterRow(int index) {
        this(index, false);
    }

    public FilterRow(int index, boolean firstRow) {
        this.index = index;
        this.firstRow = firstRow;
        rowIdSuffix = ROW_ID_SUFFIX + RenderingUtil.SERVER_ID_SUFFIX_SEPARATOR + index;

    }

    public int getIndex() {
        return index;
    }

    public void setLastRow(boolean lastRow) {
        this.lastRow = lastRow;
    }

    public boolean isInverse() {
        return inverse;
    }

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

    private HtmlPanelGroup createRowContainer(FacesContext context, CompositeFilter compositeFilter) {
        HtmlPanelGroup rowContainer = (HtmlPanelGroup) ComponentUtil.createChildComponent(context, compositeFilter, HtmlPanelGroup.COMPONENT_TYPE, rowIdSuffix);
        rowContainer.setLayout("block");
        rowContainer.setStyleClass(DEFAULT_ROW_CLASS);

        createDeleteButton(context, rowContainer, compositeFilter);
        createAddButton(context, rowContainer, compositeFilter);
        return rowContainer;
    }

    private HtmlPanelGroup getRowContainer(CompositeFilter compositeFilter) {
        return (HtmlPanelGroup) ComponentUtil.getChildBySuffix(compositeFilter, rowIdSuffix);
    }


    private HtmlSelectBooleanCheckbox createInverseCheckBox(FacesContext context, HtmlPanelGroup rowContainer, final CompositeFilter compositeFilter) {
        HtmlPanelGroup inverseCheckBoxContainer = (HtmlPanelGroup) ComponentUtil.createChildComponent(context, rowContainer, HtmlPanelGroup.COMPONENT_TYPE, INVERSE_CHECKBOX_CONTAINER_SUFFIX, 1);

        inverseCheckBoxContainer.setStyleClass(DEFAULT_ROW_ITEM_CHECKBOX_CLASS);
        HtmlSelectBooleanCheckbox inverseCheckBox = (HtmlSelectBooleanCheckbox) ComponentUtil.createChildComponent(context, inverseCheckBoxContainer, HtmlSelectBooleanCheckbox.COMPONENT_TYPE, CHECKBOX_SUFFIX);
        //TODO: add onclick that will send ajax request to update component state        
        inverseCheckBox.setStyleClass(DEFAULT_INVERSE_CHECKBOX_CLASS);
        inverseCheckBox.setTitle(INVERSE_LABEL);

        HtmlOutputText inverseLabel = (HtmlOutputText) ComponentUtil.createChildComponent(context, inverseCheckBoxContainer, HtmlOutputText.COMPONENT_TYPE, LABEL_SUFFIX);
        inverseLabel.setStyleClass(DEFAULT_INVERSE_LABEL_CLASS);
        inverseLabel.setValue(INVERSE_LABEL);
        return inverseCheckBox;
    }

    private void initInverseCheckBox(FacesContext context, HtmlSelectBooleanCheckbox inverseCheckBox, CompositeFilter compositeFilter) {
        inverseCheckBox.setValue(inverse);
    }

    private HtmlSelectBooleanCheckbox getInverseCheckBox(FacesContext context, HtmlPanelGroup inverseCheckBoxContainer) {
        return (HtmlSelectBooleanCheckbox) ComponentUtil.getChildBySuffix(inverseCheckBoxContainer, CHECKBOX_SUFFIX);
    }

    private HtmlPanelGroup getInverseCheckBoxContainer(FacesContext context, HtmlPanelGroup rowContainer) {
        return (HtmlPanelGroup) ComponentUtil.getChildBySuffix(rowContainer, INVERSE_CHECKBOX_CONTAINER_SUFFIX);
    }

    private HtmlSelectBooleanCheckbox findInverseCheckBox(FacesContext context, CompositeFilter compositeFilter) {
        HtmlPanelGroup rowContainer = getRowContainer(compositeFilter);
        if (rowContainer == null) {
            return null;
        }
        HtmlPanelGroup inverseContainer = getInverseCheckBoxContainer(context, rowContainer);
        if (inverseContainer == null) {
            return null;
        }
        HtmlSelectBooleanCheckbox inverseCheckBox = getInverseCheckBox(context, inverseContainer);
        return inverseCheckBox;
    }


    private HtmlCommandButton createAddButton(FacesContext context, HtmlPanelGroup rowContainer, CompositeFilter compositeFilter) {
        HtmlPanelGroup addButtonContainer = (HtmlPanelGroup) ComponentUtil.createChildComponent(context, rowContainer, HtmlPanelGroup.COMPONENT_TYPE, ADD_BUTTON_CONTAINER_SUFFIX);
        addButtonContainer.setStyleClass(DEFAULT_ROW_ITEM_CLASS);
        HtmlCommandButton addButton = (HtmlCommandButton) ComponentUtil.createChildComponent(context, addButtonContainer, HtmlCommandButton.COMPONENT_TYPE, BUTTON_SUFFIX);
        addButton.setValue("+");
        addButton.setOnclick("O$.Filter.add('" + compositeFilter.getClientId(context) + "'); return false;");
        addButtonContainer.setValueExpression("rendered", new ValueExpressionImpl() {
            public Object getValue(ELContext elContext) {
                return lastRow;
            }
        });
        addButton.setStyleClass(DEFAULT_ADD_BUTTON_CLASS);
        return addButton;
    }

    private HtmlCommandButton createDeleteButton(FacesContext context, HtmlPanelGroup rowContainer, CompositeFilter compositeFilter) {
        HtmlPanelGroup deleteButtonContainer = (HtmlPanelGroup) ComponentUtil.createChildComponent(context, rowContainer, HtmlPanelGroup.COMPONENT_TYPE, DELETE_BUTTON_CONTAINER_SUFFIX);
        deleteButtonContainer.setStyleClass(DEFAULT_ROW_ITEM_CLASS);
        HtmlCommandButton deleteButton = null;
        deleteButton = (HtmlCommandButton) ComponentUtil.createChildComponent(context, deleteButtonContainer, HtmlCommandButton.COMPONENT_TYPE, BUTTON_SUFFIX);
        deleteButton.setValue("-");
        deleteButton.setOnclick("O$.Filter._remove('" + compositeFilter.getClientId(context) + "'," + index + "); return false;");
        deleteButton.setStyleClass(DEFAULT_DELETE_BUTTON_CLASS);

        return deleteButton;
    }


    private HtmlPanelGroup createPropertySelectorContainer(FacesContext context, HtmlPanelGroup rowContainer) {
        HtmlPanelGroup propertySelectorContainer = (HtmlPanelGroup) ComponentUtil.createChildComponent(context, rowContainer, HtmlPanelGroup.COMPONENT_TYPE, PROPERTY_SELECTOR_ID_SUFFIX, 0);
        propertySelectorContainer.setStyleClass(DEFAULT_ROW_ITEM_INPUT_CLASS);
        return propertySelectorContainer;
    }

    private HtmlPanelGroup getPropertySelectorContainer(FacesContext context, HtmlPanelGroup rowContainer) {
        return (HtmlPanelGroup) ComponentUtil.getChildBySuffix(rowContainer, PROPERTY_SELECTOR_ID_SUFFIX);
    }

    private DropDownField createPropertySelector(FacesContext context, HtmlPanelGroup propertySelectorContainer, CompositeFilter compositeFilter) {
        DropDownField propertySelector = (DropDownField) ComponentUtil.createChildComponent(context, propertySelectorContainer, DropDownField.COMPONENT_TYPE, DROP_DOWN_ID_SUFFIX);
        DropDownItems dropDownItems = (DropDownItems) ComponentUtil.createChildComponent(context, propertySelector, DropDownItems.COMPONENT_TYPE, DROP_DOWN_ITEMS_ID_SUFFIX);
        List<String> properties = compositeFilter.getProperties();
        dropDownItems.setValue(properties);
        propertySelector.setOnchange("O$.Filter._propertyChange('" + compositeFilter.getClientId(context) + "'," + index + ");");
        propertySelector.setStyleClass(DEFAULT_PROPERTY_CLASS);
        propertySelector.setCustomValueAllowed(false);
        return propertySelector;
    }

    private void initPropertySelector(FacesContext context, DropDownField propertySelector, CompositeFilter compositeFilter) {
        String propertyValue = (property != null) ? property.getValue() : null;
        propertySelector.setValue(propertyValue);
    }

    private DropDownField getPropertySelector(FacesContext context, HtmlPanelGroup propertySelectorContainer) {
        return (DropDownField) ComponentUtil.getChildBySuffix(propertySelectorContainer, DROP_DOWN_ID_SUFFIX);
    }

    private DropDownField findPropertySelector(FacesContext context, CompositeFilter compositeFilter) {
        HtmlPanelGroup rowContainer = getRowContainer(compositeFilter);
        if (rowContainer == null) return null;
        HtmlPanelGroup propertySelectorContainer = getPropertySelectorContainer(context, rowContainer);
        if (propertySelectorContainer == null) return null;
        DropDownField propertySelector = getPropertySelector(context, propertySelectorContainer);
        return propertySelector;
    }


    private HtmlPanelGroup createOperationSelectorContainer(FacesContext context, HtmlPanelGroup rowContainer) {
        HtmlPanelGroup operationSelectorContainer = (HtmlPanelGroup) ComponentUtil.createChildComponent(context, rowContainer, HtmlPanelGroup.COMPONENT_TYPE, OPERATION_SELECTOR_ID_SUFFIX, 2);
        operationSelectorContainer.setStyleClass(DEFAULT_ROW_ITEM_INPUT_CLASS);
        operationSelectorContainer.setValueExpression("rendered", new ValueExpressionImpl() {
            public Object getValue(ELContext elContext) {
                return property != null;
            }
        });
        return operationSelectorContainer;
    }

    private HtmlPanelGroup getOperationSelectorContainer(FacesContext context, HtmlPanelGroup rowContainer) {
        return (HtmlPanelGroup) ComponentUtil.getChildBySuffix(rowContainer, OPERATION_SELECTOR_ID_SUFFIX);
    }

    private DropDownField createOperationSelector(FacesContext context, HtmlPanelGroup operationSelectorContainer, CompositeFilter compositeFilter) {
        DropDownField operationSelector = (DropDownField) ComponentUtil.createChildComponent(context, operationSelectorContainer, DropDownField.COMPONENT_TYPE, DROP_DOWN_ID_SUFFIX);
        DropDownItems dropDownItems = (DropDownItems) ComponentUtil.createChildComponent(context, operationSelector, DropDownItems.COMPONENT_TYPE, DROP_DOWN_ITEMS_ID_SUFFIX);
        operationSelector.setOnchange("O$.Filter._operationChange('" + compositeFilter.getClientId(context) + "'," + index + ");");
        operationSelector.setConverter(compositeFilter.getOperationConverter());
        operationSelector.setStyleClass(DEFAULT_OPERATION_CLASS);
        operationSelector.setStyle("width: 100px;");
        operationSelector.setCustomValueAllowed(false);
        return operationSelector;
    }


    private void initOperationSelector(FacesContext context, DropDownField operationSelector, CompositeFilter compositeFilter) {
        operationSelector.setValue(operation);
        DropDownItems dropDownItems = getOperationSelectorItems(context, operationSelector);
        EnumSet<OperationType> operations = compositeFilter.getOperations(property);
        dropDownItems.setValue(operations);
    }


    private DropDownField getOperationSelector(FacesContext context, HtmlPanelGroup operationSelectorContainer) {
        return (DropDownField) ComponentUtil.getChildBySuffix(operationSelectorContainer, DROP_DOWN_ID_SUFFIX);
    }

    private DropDownField findOperationSelector(FacesContext context, CompositeFilter compositeFilter) {
        HtmlPanelGroup rowContainer = getRowContainer(compositeFilter);
        if (rowContainer == null) return null;
        HtmlPanelGroup operationSelectorContainer = getOperationSelectorContainer(context, rowContainer);
        if (operationSelectorContainer == null) return null;
        DropDownField operationSelector = getOperationSelector(context, operationSelectorContainer);
        return operationSelector;
    }


    private DropDownItems getOperationSelectorItems(FacesContext context, DropDownField operationSelector) {
        return (DropDownItems) ComponentUtil.getChildBySuffix(operationSelector, DROP_DOWN_ITEMS_ID_SUFFIX);
    }


    private HtmlPanelGroup createParametersEditorContainer(FacesContext context, HtmlPanelGroup rowContainer) {
        HtmlPanelGroup parametersEditorContainer = (HtmlPanelGroup) ComponentUtil.createChildComponent(context, rowContainer, HtmlPanelGroup.COMPONENT_TYPE, PARAMETERS_EDITOR_ID_SUFFIX, 3);
        parametersEditorContainer.setStyleClass(DEFAULT_ROW_ITEM_INPUT_CLASS);
        parametersEditorContainer.setValueExpression("rendered", new ValueExpressionImpl() {
            public Object getValue(ELContext elContext) {
                return operation != null;
            }
        });
        return parametersEditorContainer;
    }


    private HtmlPanelGroup getParametersEditorContainer(FacesContext context, HtmlPanelGroup rowContainer) {
        return (HtmlPanelGroup) ComponentUtil.getChildBySuffix(rowContainer, PARAMETERS_EDITOR_ID_SUFFIX);
    }


    private HtmlPanelGroup findParametersEditorContainer(FacesContext context, CompositeFilter compositeFilter) {
        HtmlPanelGroup rowContainer = getRowContainer(compositeFilter);
        if (rowContainer == null) {
            return null;
        }
        HtmlPanelGroup parametersEditorContainer = getParametersEditorContainer(context, rowContainer);
        return parametersEditorContainer;
    }

    public HtmlPanelGroup preparateRowComponentHierarchy(FacesContext context, CompositeFilter compositeFilter) throws IOException {
        HtmlPanelGroup rowContainer = getRowContainer(compositeFilter);
        if (rowContainer == null) {
            rowContainer = createRowContainer(context, compositeFilter);
        }
        HtmlPanelGroup propertySelectorContainer = getPropertySelectorContainer(context, rowContainer);
        if (propertySelectorContainer == null) {
            propertySelectorContainer = createPropertySelectorContainer(context, rowContainer);
        }
        DropDownField propertySelector = getPropertySelector(context, propertySelectorContainer);
        if (propertySelector == null) {
            propertySelector = createPropertySelector(context, propertySelectorContainer, compositeFilter);
        }
        initPropertySelector(context, propertySelector, compositeFilter);

        if (property != null) {
            preparateOperationComponentHierarchy(context, rowContainer, compositeFilter);
        }
        if (operation != null) {
            preparateParametersComponentHierarchy(context, rowContainer, compositeFilter);
        }
        return rowContainer;

    }

    public HtmlPanelGroup preparateOperationComponentHierarchy(FacesContext context, HtmlPanelGroup rowContainer, CompositeFilter compositeFilter) throws IOException {
        HtmlSelectBooleanCheckbox inverseCheckBox = findInverseCheckBox(context, compositeFilter);
        if (inverseCheckBox == null) {
            inverseCheckBox = createInverseCheckBox(context, rowContainer, compositeFilter);
        }
        initInverseCheckBox(context, inverseCheckBox, compositeFilter);
        HtmlPanelGroup operationSelectorContainer = getOperationSelectorContainer(context, rowContainer);
        if (operationSelectorContainer == null) {
            operationSelectorContainer = createOperationSelectorContainer(context, rowContainer);
        }
        DropDownField operationSelector = getOperationSelector(context, operationSelectorContainer);
        if (operationSelector == null) {
            operationSelector = createOperationSelector(context, operationSelectorContainer, compositeFilter);
        }
        initOperationSelector(context, operationSelector, compositeFilter);
        return operationSelectorContainer;
    }

    public HtmlPanelGroup preparateParametersComponentHierarchy(FacesContext context, HtmlPanelGroup rowContainer, CompositeFilter compositeFilter) throws IOException {
        HtmlPanelGroup parametersEditorContainer = getParametersEditorContainer(context, rowContainer);
        if (parametersEditorContainer == null) {
            parametersEditorContainer = createParametersEditorContainer(context, rowContainer);
        }
        if (parametersEditor == null) {
            ParametersEditor.ParameterEditorType type = ParametersEditor.getParameterEditorType(property, operation);
            parametersEditor = ParametersEditor.getInstance(type, property, operation);
            if (parametersEditorType == type && criterion != null) {
                parametersEditor.setParameters(criterion);
            }
            parametersEditorType = type;
        }
        parametersEditor.prepare(context, compositeFilter, this, parametersEditorContainer);
        return parametersEditorContainer;
    }


    public void encodeRow(FacesContext context, CompositeFilter compositeFilter) throws IOException {
        UIComponent component = preparateRowComponentHierarchy(context, compositeFilter);
        component.encodeAll(context);
    }

    public void encodeOperationSelector(FacesContext context, CompositeFilter compositeFilter) throws IOException {
        HtmlPanelGroup rowContainer = getRowContainer(compositeFilter);
        UIComponent component = preparateOperationComponentHierarchy(context, rowContainer, compositeFilter);
        HtmlPanelGroup inverseCheckBoxContainer = getInverseCheckBoxContainer(context, rowContainer);
        inverseCheckBoxContainer.encodeAll(context);
        component.encodeAll(context);
    }

    public void encodeParametersEditor(FacesContext context, CompositeFilter compositeFilter) throws IOException {
        HtmlPanelGroup rowContainer = getRowContainer(compositeFilter);
        if (rowContainer == null) {
            return;
        }
        UIComponent component = preparateParametersComponentHierarchy(context, rowContainer, compositeFilter);
        component.encodeAll(context);
    }


    public void updateRowModelFromEditors(FacesContext context, CompositeFilter compositeFilter) {
        HtmlPanelGroup parametersEditorContainer = findParametersEditorContainer(context, compositeFilter);
        DropDownField propertySelector = findPropertySelector(context, compositeFilter);
        if (propertySelector == null) {
            property = null;
            operation = null;
            criterion = null;
            parametersEditor = null;
            return;
        }
        String propertyValue = (String) propertySelector.getValue();
        FilterProperty newProperty = compositeFilter.getFilterProperty(propertyValue);
        boolean propertyModified = property == null ? newProperty != null : newProperty != null && !newProperty.getName().equals(property.getName());
        property = newProperty;
        DropDownField operationSelector = findOperationSelector(context, compositeFilter);
        HtmlSelectBooleanCheckbox inverseCheckBox = findInverseCheckBox(context, compositeFilter);
        if (propertyModified || property == null || operationSelector == null || inverseCheckBox == null) {
            operation = null;
            parametersEditor = null;
            return;
        }
        OperationType newOperation = (OperationType) operationSelector.getValue();
        inverse = (Boolean) inverseCheckBox.getValue();
        boolean operationModified = newOperation == null ? operation == null : !newOperation.equals(operation);
        operation = newOperation;
        if (operation == null || parametersEditor == null) {
            parametersEditor = null;
            criterion = null;
            return;
        }
        parametersEditor.update(context, compositeFilter, this, parametersEditorContainer);
        criterion = parametersEditor.getCriterion();
        if (operationModified) {
            parametersEditor = null;
        }

    }

    public void updateRowModelFromCriterion(PropertyFilterCriterion criterion, CompositeFilter compositeFilter) {
        property = compositeFilter.getFilterPropertyByName(criterion.getProperty());
        operation = criterion.getOperation();
        inverse = criterion.isInverse();
        parametersEditorType = ParametersEditor.getParameterEditorType(property, operation);
        parametersEditor = ParametersEditor.getInstance(parametersEditorType, property, operation);

    }

    public PropertyFilterCriterion getCriterion() {
        if (parametersEditor == null) {
            return null;
        }
        return criterion;
    }

    public void removeInlineComponents(CompositeFilter compositeFilter) {
        HtmlPanelGroup rowContainer = getRowContainer(compositeFilter);
        rowContainer.getParent().getChildren().remove(rowContainer);
    }

}