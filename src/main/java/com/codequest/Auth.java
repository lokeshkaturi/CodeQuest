package com.codequest;
import jakarta.servlet.*;import jakarta.servlet.http.*;import java.io.IOException;
public class Auth {
 public static Integer userId(HttpServletRequest r){Object x=r.getSession().getAttribute("userId");return x instanceof Integer?(Integer)x:null;}
 public static boolean admin(HttpServletRequest r){return Boolean.TRUE.equals(r.getSession().getAttribute("admin"));}
 public static void requireUser(HttpServletRequest r,HttpServletResponse s)throws IOException{if(userId(r)==null)s.sendRedirect(r.getContextPath()+"/login");}
 public static void requireAdmin(HttpServletRequest r,HttpServletResponse s)throws IOException{if(!admin(r))s.sendRedirect(r.getContextPath()+"/admin/login");}
}