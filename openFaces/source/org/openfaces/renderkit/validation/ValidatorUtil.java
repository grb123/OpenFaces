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
package org.openfaces.renderkit.validation;

import org.openfaces.validator.AbstractClientValidator;
import org.openfaces.util.RenderingUtil;
import org.openfaces.util.ResourceUtil;

import javax.faces.context.FacesContext;
import java.io.IOException;

public class ValidatorUtil {
    private ValidatorUtil() {
    }

    public static void renderPresentationExistsForAllInputComponents(FacesContext context) throws IOException {
        RenderingUtil.renderInitScript(context, "O$._presentationExistsForAllComponents();", new String[]{
                ResourceUtil.getUtilJsURL(context),
                getValidatorUtilJsUrl(context)});
    }

    public static void renderPresentationExistsForComponent(String componentClientId, FacesContext context) throws IOException {
        RenderingUtil.renderInitScript(context, "O$._presentationExistsForComponent('" + componentClientId + "');", new String[]{
                ResourceUtil.getUtilJsURL(context),
                getValidatorUtilJsUrl(context)});
    }

    public static String getValidatorUtilJsUrl(FacesContext context) {
        return ResourceUtil.getInternalResourceURL(context, AbstractClientValidator.class, "validatorUtil.js");
    }
}
