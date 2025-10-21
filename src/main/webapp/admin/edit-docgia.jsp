<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sua thong tin doc gia</title>
        <%@ include file="layout/header.jsp" %>       
    </head>
    <body>
        <%@ include file="layout/nav.jsp" %>      
        <div class="container-fluid">
            <div class="row">
                <div class="col-2">
                    <%@ include file="layout/sidebar.jsp" %>
                </div>

                <div class="col-10 mt-3">
                    <div class="container mt-4">                      
                    <!-- Modal: Sửa độc giả -->
                        <div>
                            <div class="">
                                <div class="card">
                                    <form action="docgia">
                                        <input name="action" value="update" type="hidden" />
                                        <div class="card-header">
                                            <h5>Sửa thông tin độc giả</h5>                                           
                                        </div>
                                        <div class="card-body">
                                            <div class="mb-3">
                                                <label class="form-label">Mã độc giả</label>
                                                <input type="text" class="form-control" name="MaDocGia" readonly value="${dg.maDocGia}">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Họ tên</label>
                                                <input type="text" class="form-control" name="HoTen" required value="${dg.hoTen}">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Ngày sinh</label>
                                                <input type="date" class="form-control" name="NgaySinh" value="${dg.ngaySinh}">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Địa chỉ</label>
                                                <input type="text" class="form-control" name="DiaChi" value="${dg.diaChi}">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Số điện thoại</label>
                                                <input type="text" class="form-control" name="SoDienThoai" value="${dg.soDienThoai}">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Email</label>
                                                <input type="email" class="form-control" name="Email" value="${dg.email}">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                            <button type="submit" class="btn btn-warning text-white">Cập nhật</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>      
            </div>
        </div>

        <%@ include file="layout/footer.jsp" %>

    </body>
</html>

