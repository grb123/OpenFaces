<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://openfaces.org/" prefix="o" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
<head><title>Screenshot</title>

  <style type="text/css">
    <!--
    /** {*/
    /*font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif;*/
    /*font-size: 11px;*/
    /*}*/

    -->
  </style>
</head>

<body>
<f:view>
  <h:form id="form1">
   <%@ include file="screenshot_core.xhtml" %>
  </h:form>
</f:view>

</body>
</html>