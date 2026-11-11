package com.codequest;
import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.*;import java.sql.*;
@WebServlet("/missions")
public class MissionsServlet extends HttpServlet{
 protected void doGet(HttpServletRequest q,HttpServletResponse p)throws IOException{if(Auth.userId(q)==null){p.sendRedirect("login");return;}StringBuilder b=new StringBuilder("<p class='muted'>Choose an answer and submit. XP is awarded for a correct answer.</p><div class='grid'>");
  try(Connection c=DB.getConnection();PreparedStatement s=c.prepareStatement("SELECT id,title,description,category,difficulty,xp_reward FROM missions ORDER BY id DESC");ResultSet r=s.executeQuery()){while(r.next()){b.append("<div class='card'><span class='pill'>").append(View.esc(r.getString("category"))).append("</span><span class='pill'>").append(View.esc(r.getString("difficulty"))).append("</span><h2>").append(View.esc(r.getString("title"))).append("</h2><p>").append(View.esc(r.getString("description"))).append("</p><p class='muted'>Reward: ").append(r.getInt("xp_reward")).append(" XP</p><a class='btn' href='mission?id=").append(r.getInt("id")).append("'>Start mission</a></div>");}}
  catch(Exception ex){throw new IOException(ex);}b.append("</div>");p.setContentType("text/html;charset=UTF-8");p.getWriter().print(View.page("Programming Missions",b.toString(),q.getContextPath()));}
}