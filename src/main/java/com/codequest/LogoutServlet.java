package com.codequest;
import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.*;
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{protected void doGet(HttpServletRequest q,HttpServletResponse p)throws IOException{q.getSession().invalidate();p.sendRedirect("login");}}