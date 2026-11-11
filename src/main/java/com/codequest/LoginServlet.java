package com.codequest;
import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.*;import java.sql.*;import org.mindrot.jbcrypt.BCrypt;
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
 protected void doPost(HttpServletRequest q,HttpServletResponse p)throws IOException{
  String u=q.getParameter("username"),pw=q.getParameter("password");
  try(Connection c=DB.getConnection();PreparedStatement s=c.prepareStatement("SELECT id,password FROM users WHERE username=? OR email=?")){
   s.setString(1,u);s.setString(2,u);try(ResultSet r=s.executeQuery()){if(r.next()&&check(pw,r.getString("password"))){q.getSession().setAttribute("userId",r.getInt("id"));q.getSession().setAttribute("username",u);p.sendRedirect("dashboard");return;}}
   p.setContentType("text/html;charset=UTF-8");p.getWriter().print(View.page("Login","<p class='error'>Invalid username/email or password.</p>"+loginForm(),q.getContextPath()));
  }catch(Exception ex){throw new IOException(ex);}
 }
 private boolean check(String p,String stored){try{return stored.startsWith("$2a$")||stored.startsWith("$2b$")?BCrypt.checkpw(p,stored):stored.equals(p);}catch(Exception e){return false;}}
 static String loginForm(){return "<form method='post'><label>Username or email</label><input name='username' required><label>Password</label><input type='password' name='password' required><button>Login</button></form><p><a href='register'>Create account</a></p>";}
 protected void doGet(HttpServletRequest q,HttpServletResponse p)throws IOException{p.setContentType("text/html;charset=UTF-8");p.getWriter().print(View.page("Student Login",(q.getParameter("registered")!=null?"<p class='success'>Registration successful. Please log in.</p>":"")+loginForm(),q.getContextPath()));}
}