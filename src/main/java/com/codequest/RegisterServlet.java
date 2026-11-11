package com.codequest;
import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.*;import java.sql.*;import org.mindrot.jbcrypt.BCrypt;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
 protected void doPost(HttpServletRequest q,HttpServletResponse p)throws IOException{
  p.setContentType("text/html;charset=UTF-8");String u=q.getParameter("username"),e=q.getParameter("email"),pw=q.getParameter("password");
  if(u==null||e==null||pw==null||u.isBlank()||e.isBlank()||pw.length()<6){p.getWriter().print("Enter username, email and password (at least 6 characters). <a href='register'>Back</a>");return;}
  try(Connection c=DB.getConnection();PreparedStatement s=c.prepareStatement("INSERT INTO users(username,email,password) VALUES(?,?,?)")){
   s.setString(1,u.trim());s.setString(2,e.trim());s.setString(3,BCrypt.hashpw(pw,BCrypt.gensalt()));s.executeUpdate();p.sendRedirect("login?registered=1");
  }catch(SQLIntegrityConstraintViolationException ex){p.getWriter().print("Username or email already exists. <a href='register'>Try again</a>");}
  catch(Exception ex){throw new IOException(ex);}
 }
 protected void doGet(HttpServletRequest q,HttpServletResponse p)throws IOException{p.setContentType("text/html;charset=UTF-8");p.getWriter().print(View.page("Create account","<form method='post'><label>Username</label><input name='username' required maxlength='50'><label>Email</label><input type='email' name='email' required><label>Password (min 6 characters)</label><input type='password' name='password' minlength='6' required><button>Create account</button></form><p><a href='"+q.getContextPath()+"/login'>Already registered? Login</a></p>",q.getContextPath()));}
}