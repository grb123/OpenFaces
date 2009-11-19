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

O$.DaySwitcher = {
  _init: function(daySwitcherId,
                  dayTableId,
                  day,
                  pattern,
                  upperPattern,
                  locale,
                  stylingParams,
                  enabled) {
    var daySwitcher = O$(daySwitcherId);

    var dtf = O$.getDateTimeFormatObject(locale);
    daySwitcher._day = dtf.parse(day, "dd/MM/yyyy");

    daySwitcher._dayTableId = dayTableId;
    daySwitcher._pattern = pattern;
    daySwitcher._upperPattern = upperPattern;
    daySwitcher._locale = locale;

    O$.initComponent(daySwitcherId, {rollover: stylingParams.rolloverClass});

    var textId = daySwitcherId + "::text";
    daySwitcher._text = O$(textId);
    if (daySwitcher._text) {
      O$.initComponent(textId, {rollover: stylingParams.textRolloverClass});
    }

    var upperTextId = daySwitcherId + "::upper_text";
    daySwitcher._upperText = O$(upperTextId);
    if (daySwitcher._upperText) {
      O$.initComponent(upperTextId, {rollover: stylingParams.upperTextRolloverClass});
    }

    if (enabled) {
      var previousButtonId = daySwitcherId + "::previous_button";
      O$.initComponent(previousButtonId, {rollover: stylingParams.previousButtonRolloverClass});
      daySwitcher._previousButton = O$(previousButtonId);
      var previousButton = daySwitcher._previousButton;

      var nextButtonId = daySwitcherId + "::next_button";
      O$.initComponent(nextButtonId, {rollover: stylingParams.nextButtonRolloverClass});
      daySwitcher._nextButton = O$(nextButtonId);
      var nextButton = daySwitcher._nextButton;

      previousButton.onmouseup = function () {
        O$.setStyleMappings(previousButton, {
          pressed: null
        });
      };

      nextButton.onmouseup = function () {
        O$.setStyleMappings(nextButton, {
          pressed: null
        });
      };

      previousButton.ondragstart =
      nextButton.ondragstart =
      previousButton.onselectstart =
      nextButton.onselectstart = function(e) {
        O$.breakEvent(e);
      };

      previousButton.ondblclick =
      nextButton.ondblclick = O$.repeatClickOnDblclick;

    }

    daySwitcher._getDayTable = function() {
      if (!daySwitcher._dayTable) {
        daySwitcher._dayTable = O$(daySwitcher._dayTableId);
      }
      return daySwitcher._dayTable;
    };

    daySwitcher.getDay = function() {
      return daySwitcher._day;
    };


    daySwitcher.setDay = function(day) {
      if (O$._datesEqual(daySwitcher._day, day))
        return;
      daySwitcher._day = day;

      if (daySwitcher._pattern) {
        var dtf = O$.getDateTimeFormatObject(daySwitcher._locale);
        daySwitcher._text.innerHTML = dtf.format(day, daySwitcher._pattern);
      }

      if (daySwitcher._upperPattern) {
        dtf = O$.getDateTimeFormatObject(daySwitcher._locale);
        daySwitcher._upperText.innerHTML = dtf.format(day, daySwitcher._upperPattern);
      }

      var dayTable = daySwitcher._getDayTable();
      if (!dayTable) {
        return;
      }

      daySwitcher._dayTable.setDay(day);
    };

    daySwitcher.previousDay = function() {
      var prevDay = O$.incDay(daySwitcher._day, -1);
      daySwitcher.setDay(prevDay);
    };

    daySwitcher.nextDay = function() {
      var nextDay = O$.incDay(daySwitcher._day, 1);
      daySwitcher.setDay(nextDay);
    };

    daySwitcher.today = function() {
      var today = new Date();
      daySwitcher.setDay(today);
    };

    if (enabled) {
      previousButton.onmousedown = function () {
        O$.setStyleMappings(previousButton, {
          pressed: stylingParams.previousButtonPressedClass
        });
        daySwitcher.previousDay();
      };

      nextButton.onmousedown = function () {
        O$.setStyleMappings(nextButton, {
          pressed: stylingParams.nextButtonPressedClass
        });
        daySwitcher.nextDay();
      };

      O$.addLoadEvent(function () {
        var dayTable = daySwitcher._getDayTable();
        if (!dayTable) {
          return;
        }

        var _onDayChange = dayTable._onDayChange;
        dayTable._onDayChange = function(day) {
          if (_onDayChange) {
            _onDayChange(day);
          }
          daySwitcher.setDay(day);
        };
      });

    }


  }
};