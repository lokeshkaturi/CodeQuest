package com.codequest;
import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.*;import java.sql.*;import org.mindrot.jbcrypt.BCrypt;
@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet{
 protected void doPost(HttpServletRequest q,HttpServletResponse p)throws IOException{
  String u=q.getParameter("username"),pw=q.getParameter("password");
  try(Connection c=DB.getConnection();PreparedStatement s=c.prepareStatement("SELECT id,password FROM admins WHERE username=?")){
   s.setString(1,u);try(ResultSet r=s.executeQuery()){if(r.next()){int adminId=r.getInt("id");String v=r.getString("password");boolean ok;try{ok=v.startsWith("$2a$")||v.startsWith("$2b$")?BCrypt.checkpw(pw,v):v.equals(pw);}catch(Exception ex){ok=false;}if(ok){q.getSession().setAttribute("admin",true);q.getSession().setAttribute("adminId",adminId);q.getSession().setAttribute("adminName",u);p.sendRedirect(q.getContextPath()+"/admin/missions");return;}}}
   p.setContentType("text/html;charset=UTF-8");p.getWriter().print(View.page("Admin Login","<p class='error'>Invalid admin credentials.</p>"+form(),q.getContextPath()));
  }catch(Exception ex){throw new IOException(ex);}
 }
 private String form(){return "<form method='post'><label>Admin username</label><input name='username' required><label>Password</label><input type='password' name='password' required><button>Admin login</button></form>";}
 protected void doGet(HttpServletRequest q,HttpServletResponse p)throws IOException{p.setContentType("text/html;charset=UTF-8");p.getWriter().print(View.page("Admin Login",form(),q.getContextPath()));}
}