<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold d-flex align-items-center" href="trang-chu">
            <i class="bi bi-book-half me-2"></i> Quản lý Thư viện
        </a>
        <div class="d-flex align-items-center">
             
            <span class="navbar-text text-light me-3">
                <i class="bi bi-person-circle me-1"></i> ${sessionScope.user !=null? sessionScope.user.fullname: "GUEST"}
            </span>
           
            <c:if test="${sessionScope.user!=null}">
              <a href="logout" class="btn btn-outline-light btn-sm">
                <i class="bi bi-box-arrow-right me-1"></i> Đăng xuất
              </a>
          </c:if>
        </div>
    </div>
</nav>
