<%-- 
    Document   : thongbao
    Created on : Oct 14, 2025, 4:17:46 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<%
    if (request.getAttribute("success") != null) {        
%>
<script>
    Swal.fire({
        position: "top-end",
        icon: "success",
        title: "<%=request.getAttribute("success")%>",
        showConfirmButton: false,
        timer: 1500
    });
</script>

<%
    }
%>
