package com.codequest;
import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.*;import java.sql.*;
@WebServlet("/submit")
public class SubmitServlet extends HttpServlet{
 protected void doPost(HttpServletRequest q,HttpServletResponse p)throws IOException{if(Auth.userId(q)==null){p.sendRedirect("login");return;}int mid;try{mid=Integer.parseInt(q.getParameter("missionId"));}catch(Exception e){p.sendRedirect("missions");return;}String answer=q.getParameter("answer");boolean correct=false;int xp=0;String title="";
  try(Connection c=DB.getConnection()){c.setAutoCommit(false);try(PreparedStatement s=c.prepareStatement("SELECT title,correct_answer,xp_reward FROM missions WHERE id=?")){s.setInt(1,mid);try(ResultSet r=s.executeQuery()){if(!r.next()){c.rollback();p.sendRedirect("missions");return;}title=r.getString("title");correct=answer!=null&&answer.trim().equalsIgnoreCase(r.getString("correct_answer").trim());xp=r.getInt("xp_reward");}}
   try(PreparedStatement s=c.prepareStatement("INSERT INTO mission_submissions(user_id,mission_id,answer,is_correct) VALUES(?,?,?,?)")){s.setInt(1,Auth.userId(q));s.setInt(2,mid);s.setString(3,answer);s.setBoolean(4,correct);s.executeUpdate();}
   if(correct)try(PreparedStatement s=c.prepareStatement("UPDATE users SET total_xp=total_xp+?,player_level=FLOOR((total_xp+?)/200)+1 WHERE id=?")){s.setInt(1,xp);s.setInt(2,xp);s.setInt(3,Auth.userId(q));s.executeUpdate();}
   c.commit();
  }catch(Exception ex){throw new IOException(ex);}
  p.setContentType("text/html;charset=UTF-8");String body="<div class='card'><h2>"+(correct?"Correct answer! 🎉":"Not quite — keep practicing.")+"</h2><p>"+(correct?"You earned "+xp+" XP.":"No XP awarded this time.")+"</p><a class='btn' href='missions'>Back to missions</a> <a class='btn' href='dashboard'>Dashboard</a></div>";p.getWriter().print(View.page("Answer Result",body,q.getContextPath()));
 }
}