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
package org.openfaces.test;

import com.thoughtworks.selenium.Selenium;
import org.junit.runner.RunWith;
import org.openfaces.test.junit4ex.BrowserRestartPolicy;
import org.openfaces.test.openfaces.LoadingMode;

/**
 * A base class for TestCase classes using the SeleniumInspector framework. It provides access to the APIs for loading and
 * inspecting Web pages. Rich possibilities for inspecting contents of Web pages can be accessed using the hierarchy of
 * ElementInspector classes. Each instance of ElementInspector (or actually of its subclasses) corresponds to an
 * appropriate DOM element and provides various possibilities for inspection (style inspection, JavaScript-based
 * element/function inspection, etc.), and navigation though child elements. In fact ElementInspectors are not
 * restricted to simple DOM elements, and there is a hierarchy of inspectors that even allow inspecting the entire JSF
 * components. (todo in fact this hierarchy is being created currently)
 * <p/>
 * The root access points to inspecting page contents are the window(), and elementById() functions.
 * <p/>
 * Note, that there are a lot of component-specific deprecated classes and constants in this class. Avoid using them,
 * they are going to be removed and integrated into the ElementInspector hierarchy.
 */
@BrowserRestartPolicy
@RunWith(org.openfaces.test.junit4ex.SeleniumBlockClassRunner.class)
public abstract class SeleniumTestCase extends org.junit.Assert {

    protected enum EventType {
        KEY("KeyEvents"), MOUSE("MouseEvents");

        private String value;

        private EventType(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    private static WindowInspector windowInspector;

    public static Selenium getSelenium() {
        return SeleniumHolder.getInstance().getSelenium();
    }

    protected void openAndWait(String pageUrl) {
        try {
            getSelenium().open(pageUrl);
            waitForPageToLoad();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't open the page: " + pageUrl, e);
        }
    }

    protected void openAndWait(String applicationContextPrefix, String applicationPage) {
        openAndWait(applicationContextPrefix + applicationPage);
    }

    protected void waitForPageToLoad() {
        LoadingMode.SERVER.waitForLoadCompletion();
    }

    protected void waitForAjax() {
        // todo: write correct processing for OpenFaces Ajax completion without relying on fixed time delays

        LoadingMode.AJAX.waitForLoadCompletion();
    }

    protected void waitForAjax4JSF() {
        //todo: write correct processing for Ajax4jsf conditions waiting
        sleep(4000);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method should usually be invoked after various client-side actions that require data loading and it waits for
     * completion of a data loading process.
     *
     * @param loadingMode specifies the kind of loading that should
     *                    be waited for:
     *                    <ul>
     *                    <li> AJAX - should be specified if an action triggers ajax data loading (works only with OpenFaces Ajax)
     *                    <li> SERVER - should be specified if an action triggers form submission. Specifying this option will result in waitng for page load completion.
     *                    <li> CLIENT - should be specified if the data is preloaded on the client, and no additional waiting is required.
     *                    </ul>
     */
    protected static void waitForLoadCompletion(LoadingMode loadingMode) {
        loadingMode.waitForLoadCompletion();
    }

    protected void assertPageAvailable(String pageUrl, String expectedPageTitle) {
        Selenium selenium = getSelenium();
        openAndWait(pageUrl);
        assertEquals("Checking page has loaded successfully: " + pageUrl, expectedPageTitle, selenium.getTitle());
    }

    protected void assertNoAlert(String messagePrefix) {
        Selenium selenium = getSelenium();
        if (selenium.isAlertPresent()) {
            fail(messagePrefix + " " + selenium.getAlert());
        }
    }

    /**
     * Using current page as list of names, generate unique name (such as "Group23")
     *
     * @param originalName an original name
     * @return unique item name to create
     */
    protected String getUniqueName(String originalName) {
        String result = originalName;
        int index = 0;
        Selenium selenium = getSelenium();
        while (selenium.isTextPresent(result)) {
            result = originalName + index++;
        }
        return result;
    }

    protected boolean isMessageTextPresent(String text) {
        Selenium selenium = getSelenium();
        return selenium.isElementPresent("//span[contains(text(),'" + text + "')]") ||
                selenium.isElementPresent("//li[contains(text(),'" + text + "')]");
    }

    protected void assertPageContainsErrorIcon(boolean shouldContainIcon) {
        boolean iconExists = getSelenium().isElementPresent("//img[contains(@src,'openFacesResources/org/openfaces/component/validation/error_icon')]");
        assertEquals(shouldContainIcon, iconExists);
    }

    protected void assertConversionErrorOccured(boolean errorExpected) {
        assertEquals(errorExpected, isMessageTextPresent("Conversion error") || isMessageTextPresent("Conversion Error") || isMessageTextPresent("could not be understood as a date"));
        // todo: can case-insensitive comparison be performed(needed to run under RI & MyFaces)?
    }

    protected void createEvent(ElementInspector containerElement, String elementPath, EventType eventType, String eventName, int keyCode, boolean shiftPressed) {

        String event = null;
        switch (eventType) {
            case KEY: {
                String initKeyEventArgs = eventName + "', true, true, window, false, false, " + shiftPressed + ", false, " + keyCode;
                event = "var eventObj = document.createEvent('" + eventType + "'); " +
                        "eventObj.initKeyEvent('" + initKeyEventArgs + ", 0); element.dispatchEvent(eventObj);";
                break;
            }
            case MOUSE: {
                event = "var eventObj = document.createEvent('" + eventType + "'); eventObj.initEvent('" + eventName + "', true, false );" +
                        "element.dispatchEvent(eventObj)";
                break;
            }
        }

        String element;
        if (elementPath == null) {
            element = "var element = this.page().findElement('" + containerElement.id() + "');";
        } else {
            element = "var element = this.page().getCurrentWindow().O$.findElementByPath(this.page().findElement('" + containerElement.id() + "'), '" + elementPath + "');";
        }
        getSelenium().getEval(element + event);
    }

    /**
     * @return an ElementInspector instance that corresponds to the currently tested window
     */
    protected static WindowInspector window() {
        if (windowInspector == null)
            windowInspector = new WindowInspector();
        return windowInspector;
    }

    /**
     * @param elementLocator Selenium element locator
     * @return ElementInspector for an element referred by the specified locator
     * @see com.thoughtworks.selenium.Selenium
     */
    protected ElementInspector element(String elementLocator) {
        return new ElementByLocatorInspector(elementLocator);
    }

}