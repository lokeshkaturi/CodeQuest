package com.codequest;
import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.*;import java.sql.*;
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse p)throws IOException{if(Auth.userId(q)==null){p.sendRedirect("login");return;}String body="";
  try(Connection c=DB.getConnection();PreparedStatement s=c.prepareStatement("SELECT username,total_xp,player_level FROM users WHERE id=?")){s.setInt(1,Auth.userId(q));try(ResultSet r=s.executeQuery()){if(r.next())body="<p class='muted'>Welcome back, "+View.esc(r.getString("username"))+"!</p><div class='grid'><div class='card'><p class='muted'>TOTAL XP</p><h2>"+r.getInt("total_xp")+"</h2></div><div class='card'><p class='muted'>PLAYER LEVEL</p><h2>"+r.getInt("player_level")+"</h2></div></div><div class='card'><h2>Ready for your next challenge?</h2><p class='muted'>Practice Java, SQL and DSA. Correct answers earn experience points.</p><a class='btn' href='missions'>Explore missions</a></div>";}}
  catch(Exception ex){throw new IOException(ex);}p.setContentType("text/html;charset=UTF-8");p.getWriter().print(View.page("Student Dashboard",body,q.getContextPath()));}
}