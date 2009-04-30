<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://openfaces.org/" prefix="o" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>

<html>
<head>
  <title>Testing stardard validators and converters on client side</title>
  <script type="text/javascript">
    function fillInvalidData(formName) {
      var field = document.getElementById(formName + "required");
      field.value = "";
      field = document.getElementById(formName + "validDR");
      field.value = 5;
      field = document.getElementById(formName + "validLR");
      field.value = 101;
      field = document.getElementById(formName + "validL");
      field.value = 1111;
      field = document.getElementById(formName + "intConv");
      field.value = "Non-integer value";
      field = document.getElementById(formName + "doubleConv");
      field.value = "Non-double value";
      field = document.getElementById(formName + "byteConv");
      field.value = "2V0";
      field = document.getElementById(formName + "shortConv");
      field.value = "999999999";
      field = document.getElementById(formName + "floatConv");
      field.value = "2V0";
      field = document.getElementById(formName + "dateConv");
      field.value = "12/02/2007";
      field = document.getElementById(formName + "dateConv2");
      field.value = "12/02/2007";
      field = document.getElementById(formName + "numberConv");
      field.value = "Not Number";
    }

    function fillValidData(formName) {
      var field = document.getElementById(formName + "required");
      field.value = "text";
      field = document.getElementById(formName + "validDR");
      field.value = 0.1;
      field = document.getElementById(formName + "validLR");
      field.value = 55;
      field = document.getElementById(formName + "validL");
      field.value = 12;
      field = document.getElementById(formName + "intConv");
      field.value = 10;
      field = document.getElementById(formName + "doubleConv");
      field.value = 8.6;
      field = document.getElementById(formName + "byteConv");
      field.value = 12;
      field = document.getElementById(formName + "shortConv");
      field.value = 95;
      field = document.getElementById(formName + "floatConv");
      field.value = 7.8;
      field = document.getElementById(formName + "dateConv");
      //field.value = "\u0418\u044e\u043d-07-2006 12:00";
      field.value = "Jun-07-2006 12:00";
      field = document.getElementById(formName + "dateConv2");
      field.value = "Thursday, June 8, 2006 10:00 PM";
      field = document.getElementById(formName + "numberConv");
      field.value = 1055.6;
    }

  </script>
</head>

<body onload="fillInvalidData('serverValidation:'); fillInvalidData('clientValidation:')">
<f:view>
  <a href="../../../../main.jsp" style="float: right;">Return to main page</a>
  <br/>
  <a href="../../../../components_test.jsp" style="float: right;">Return to validation testing main page</a>
  <table width="100%">
    <tr>
      <td width="50%">
        <h:form id="serverValidation">
          <%@ include file="standardValidatorsServer_core.xhtml" %>
        </h:form>
      </td>
      <td width="50%">
        <h:form id="clientValidation">
          <%@ include file="standardValidatorsClient_core.xhtml" %>
        </h:form>
      </td>
    </tr>
  </table>
</f:view>

</body>
</html>