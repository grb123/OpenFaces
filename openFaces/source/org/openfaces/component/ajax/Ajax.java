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
package org.openfaces.component.ajax;

import org.openfaces.component.OUIClientAction;
import org.openfaces.util.AjaxUtil;
import org.openfaces.util.StyleUtil;
import org.openfaces.util.ValueBindings;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Musihin
 */
public class Ajax extends UICommand implements OUIClientAction {
    public static final String COMPONENT_TYPE = "org.openfaces.Ajax";
    public static final String COMPONENT_FAMILY = "org.openfaces.Ajax";

    private String event = "onclick";
    private String _for;
    private Boolean standalone;

    private List<String> render;
    private List<String> execute;
    private Boolean submitInvoker;
    private Integer delay;
    private Boolean disableDefault;

    private String onajaxstart;
    private String onajaxend;
    private String onerror;

    private AjaxHelper helper = new AjaxHelper();

    public Ajax() {
        setRendererType("org.openfaces.AjaxRenderer");
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    @Override
    public void setParent(UIComponent parent) {
        super.setParent(parent);
        if (parent != null)
            helper.onParentChange(this, parent);
    }

    public void setRender(List<String> render) {
        String s = render.get(0);
        s = s.trim();
        String[] strings = s.split(" ");
        this.render = new ArrayList<String>();
        for (String string : strings) {
            this.render.add(string);
        }
    }

    public List<String> getRender() {
        return render;
    }

    public boolean getSubmitInvoker() { // todo: remove the "submitInvoker" property and hard-code the "true" behavior if no use-case where this should be customizible arises
        return ValueBindings.get(this, "submitInvoker", submitInvoker, true);
    }

    public void setSubmitInvoker(boolean submitInvoker) {
        this.submitInvoker = submitInvoker;
    }

    public List<String> getExecute() {
        return execute;
    }

    public void setExecute(List<String> execute) {
        String s = execute.get(0);
        s = s.trim();
        String[] strings = s.split(" ");
        this.execute = new ArrayList<String>();
        for (String string : strings) {
            this.execute.add(string);
        }
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        if (!event.startsWith("on")) {
            event = "on" + event;
        }
        this.event = event;
    }

    public String getFor() {
        return ValueBindings.get(this, "for", _for);
    }

    public void setFor(String _for) {
        this._for = _for;
    }


    public boolean isStandalone() {
        return ValueBindings.get(this, "standalone", standalone, false);
    }

    public void setStandalone(boolean standalone) {
        this.standalone = standalone;
    }

    public int getDelay() {
        return ValueBindings.get(this, "delay", delay, 0);
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean getDisableDefault() {
        return ValueBindings.get(this, "disableDefault", disableDefault, false);
    }

    public void setDisableDefault(boolean disableDefault) {
        this.disableDefault = disableDefault;
    }

    public String getOnajaxstart() {
        return ValueBindings.get(this, "onajaxstart", onajaxstart);
    }

    public void setOnajaxstart(String onajaxstart) {
        this.onajaxstart = onajaxstart;
    }

    public String getOnajaxend() {
        return ValueBindings.get(this, "onajaxend", onajaxend);
    }

    public void setOnajaxend(String onajaxend) {
        this.onajaxend = onajaxend;
    }

    public String getOnerror() {
        return ValueBindings.get(this, "onerror", onerror);
    }

    public void setOnerror(String onerror) {
        this.onerror = onerror;
    }


    @Override
    public Object saveState(FacesContext context) {
        Object superState = super.saveState(context);
        AjaxUtil.addJSLinks(context, getParent());
        StyleUtil.requestDefaultCss(FacesContext.getCurrentInstance());
        
        return new Object[]{superState,
                saveAttachedState(context, render),
                saveAttachedState(context, execute),
                event,
                _for,
                standalone,
                submitInvoker,
                delay,
                disableDefault,
                onerror,
                onajaxstart,
                onajaxend,
        };
    }

    @Override
    public void restoreState(FacesContext context, Object state) {
        Object[] stateArray = (Object[]) state;
        int i = 0;
        super.restoreState(context, stateArray[i++]);
        render = (List<String>) restoreAttachedState(context, stateArray[i++]);
        execute = (List<String>) restoreAttachedState(context, stateArray[i++]);
        event = (String) stateArray[i++];
        _for = (String) stateArray[i++];
        standalone = (Boolean) stateArray[i++];
        submitInvoker = (Boolean) stateArray[i++];
        delay = (Integer) stateArray[i++];
        disableDefault = (Boolean) stateArray[i++];
        onerror = (String) stateArray[i++];
        onajaxstart = (String) stateArray[i++];
        onajaxend = (String) stateArray[i++];
    }

}