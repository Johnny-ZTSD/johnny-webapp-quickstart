<%@page contentType="text/html;charset=Big5" isErrorPage="true"%>
<html>
<head><title>错误信息</title></head>
<body>
     错误码： <%=request.getAttribute("javax.servlet.error.status_code")%> <br>
     信息： <%=request.getAttribute("javax.servlet.error.message")%> <br>
     异常： <%=request.getAttribute("javax.servlet.error.exception_type")%> <br>
</body>
</html>