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

package org.openfaces.test.openfaces;

import org.openfaces.util.RenderingUtil;
import org.openfaces.test.ElementByLocatorInspector;
import org.openfaces.test.ElementByReferenceInspector;
import org.openfaces.test.ElementInspector;

import java.lang.reflect.Constructor;

/**
 * @author Andrii Gorbatov
 */
public class ForEachInspector extends ElementByReferenceInspector {
    public ForEachInspector(ElementInspector element) {
        super(element);
    }

    public ForEachInspector(String locator) {
        super(new ElementByLocatorInspector(locator));
    }

    public ElementInspector item(int index, String embeddedItemId) {
        return item(index, embeddedItemId, ElementByLocatorInspector.class);
    }

    public <T extends ElementInspector> T item(int index, String embeddedItemId, Class<T> itemClass) {
        T item;

        try {
            Constructor<T> elementConstructor = itemClass.getConstructor(String.class);
            item = elementConstructor.newInstance(id() + RenderingUtil.CLIENT_ID_SUFFIX_SEPARATOR + index + ":" + embeddedItemId);
        } catch (Exception ex) {
            throw new RuntimeException("Creating element failure", ex);
        }

        return item;
    }
}
