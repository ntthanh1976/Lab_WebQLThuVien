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
import javax.servlet.http.HttpSession;
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
     //   request.setCharacterEncoding("UTF-8");    
        //kiem CHUNG lai thong tin da xac thuc cho nguoi dung
//        HttpSession session = request.getSession(false); //lay session cho user
//        if(session==null || session.getAttribute("user")==null) //chua xac thuc thanh cong
//        {
//           request.getRequestDispatcher("login.jsp").forward(request, response);
//        }               
        
        
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
                int pageSize=5;
                int page =1;
                if(request.getParameter("page")!=null)
                {
                   page = Integer.parseInt(request.getParameter("page"));
                }                
                var dsDocGia = dgDAO.getByPage(page, pageSize); //goi DAO de lay du lieu tu DB    
                //tinh tong so trang co the co
                var tongsotrang = (int) Math.ceil((double)dgDAO.getAll().size()/pageSize);
                //truyen du lieu cho View (JSP)                
                request.setAttribute("tongsotrang", tongsotrang);               
                request.setAttribute("dsDG", dsDocGia);               
                request.setAttribute("currentPage", page);                
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
            case "delete":
                String id_str = request.getParameter("id");                
                int id = Integer.parseInt(id_str);                
              //  System.out.println("ma doc gia:" + id);
                boolean kq= dgDAO.deleteDocGia(id); //goi DAO de xoa doc gia   
                //thong bao cho client biet ket qua    
                if(kq==true)
                    request.setAttribute("success", "Xóa độc giả thành công");
                else
                   request.setAttribute("error", "Không thể xóa độc giả, do có chi tiêt mượn sách");
                //dieu huong nguoi dung den trang quan ly doc gia 
                 request.getRequestDispatcher("docgia?action=list").forward(request, response);
                break;                
            case "edit":
                  hienThiFormEdit(request,response);  
                  break;
            case "update":
                   xuLyUpdate(request,response);
                   break; 
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
            
            dgDAO.insertDocGia(dg); //goi DAO de insert doc gia
            //xu ly gui thong bao
            request.setAttribute("success", "Thêm độc giả thành công");
            //chuyen tiep ve action=list
            request.getRequestDispatcher("/docgia?action=list").forward(request, response);
        } catch (Exception ex) {
            try {
                System.out.println("Loi:" + ex.toString());
                request.setAttribute("error", "Thêm độc giả thất bại");            
                request.getRequestDispatcher("/docgia?action=list").forward(request, response);
            } catch (Exception ex1) {                
            }
        }
    }

    private void hienThiFormEdit(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id_str = request.getParameter("id");  
            int id = Integer.parseInt(id_str);
            DocGia dg = dgDAO.getById(id); //goi DAO de lay thong tin doc gia
            //tra ve giao dien de chinh sua thong tin doc gia
            request.setAttribute("dg", dg);
            request.getRequestDispatcher("/admin/edit-docgia.jsp").forward(request, response);
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        } 
    }

    private void xuLyUpdate(HttpServletRequest request, HttpServletResponse response) {
       try {
            String hoten = request.getParameter("HoTen");
            String ngaysinhStr = request.getParameter("NgaySinh");
            String diachi = request.getParameter("DiaChi");
            String sodienthoai = request.getParameter("SoDienThoai");
            String email = request.getParameter("Email");
            int maDocGia = Integer.parseInt(request.getParameter("MaDocGia"));
                    
            
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ngaysinh = new Date(sdf.parse(ngaysinhStr).getTime());
            DocGia  dg = new DocGia(maDocGia, hoten,ngaysinh, diachi, sodienthoai, email);
            
            dgDAO.updateDocGia(dg); //goi DAO de update doc gia
            //xu ly gui thong bao
            request.setAttribute("success", "Cập nhật độc giả thành công");
            //chuyen tiep ve action=list
            request.getRequestDispatcher("/docgia?action=list").forward(request, response);
        } catch (Exception ex) {
            try {
                System.out.println("Loi:" + ex.toString());
                request.setAttribute("error", "Cập nhật độc giả thất bại");            
                request.getRequestDispatcher("/docgia?action=list").forward(request, response);
            } catch (Exception ex1) {                
            }
        }
    }

}
