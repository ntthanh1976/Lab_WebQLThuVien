/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DocGia;
import model.DocGiaDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "DocGiaServlet", urlPatterns = {"/docgia"})
public class DocGiaServlet extends HttpServlet {

    DocGiaDAO dgDAO;

    @Override
    public void init() throws ServletException {
         dgDAO = new DocGiaDAO();
    }
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
        request.setCharacterEncoding("UTF-8");
       /*
         su dung tham so action de nhan dien thao tac tu client:
         action=list : liet ke danh sach doc gia
         action=add: them doc gia
         action=edit&id=..., : tra ve giao dien sua doc gia theo id
         action=update : cap nhat thong tin doc gia
         action=delete&id=.. : xoa doc gia theo id
         action=search : xu ly tra cuu doc gia
        */
        String action= request.getParameter("action");               
        if(action==null)
        {
           action="list";
        }
        switch (action) {
            case "list":
                var dsDocGia = dgDAO.getAll(); //goi DAO de lay du lieu tu DB
                request.setAttribute("dsDG", dsDocGia);
                request.getRequestDispatcher("/admin/list-docgia.jsp").forward(request, response);     
                break;   
            case "search":
                String keyword = request.getParameter("hoten");
                var dskq = dgDAO.searchDocGia(keyword); //goi DAO de lay du lieu tu DB
                request.setAttribute("dsDG", dskq);
                request.getRequestDispatcher("/admin/list-docgia.jsp").forward(request, response);     
                break;                   
            case "add":
                xuLyThem(request,response);
                break;
            case "edit":
                  int id = Integer.parseInt(request.getParameter("id"));
                  var kq = dgDAO.getById(id);
                  response.setContentType("application/json");
                  response.setCharacterEncoding("UTF-8");
                  Gson gson = new Gson();
                  PrintWriter out = response.getWriter();
                  out.print(gson.toJson(kq));
                  out.flush();
            //...
        }     
       
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void xuLyThem(HttpServletRequest request, HttpServletResponse response) {
        try {
            String hoten = request.getParameter("HoTen");
            String ngaysinhStr = request.getParameter("NgaySinh");
            String diachi = request.getParameter("DiaChi");
            String sodienthoai = request.getParameter("SoDienThoai");
            String email = request.getParameter("Email");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ngaysinh = new Date(sdf.parse(ngaysinhStr).getTime());
            DocGia  dg = new DocGia(hoten,ngaysinh, diachi, sodienthoai, email);
            
            dgDAO.insertDocGia(dg);
            //xu ly gui thong bao
            request.setAttribute("success", "Thêm độc giả thành công");
            //chuyen tiep ve action=list
            request.getRequestDispatcher("/docgia?action=list").forward(request, response);
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
    }

}
