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

/**
 * @author Dmitry Pikhulya
 */
public class NonEmptyRecordsCriterion extends FilterCriterion {

    @Override
    public boolean acceptsAll() {
        return false;
    }

    @Override
    public boolean acceptsValue(Object value) {
        return value != null && !value.equals("");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NonEmptyRecordsCriterion;
    }
}