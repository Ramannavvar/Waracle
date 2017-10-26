package com.waracle.cakemgr.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.json.JSONObject;

import com.waracle.cakemgr.beans.CakeEntity;
import com.waracle.cakemgr.persistent.HibernateUtil;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		Session session = HibernateUtil.getSessionFactory().openSession();
        List<CakeEntity> list = session.createCriteria(CakeEntity.class).list();
        session.close();
        StringBuilder builder = new StringBuilder();
        for (CakeEntity entity : list) {
        	JSONObject obj = new JSONObject(entity);
        	builder.append(obj.toString());
        }
        response.setContentType("application/json");
        response.setHeader("Content-disposition","attachment; filename=cakes.txt");
        String responseStr = "{\"data\":[" + builder + "]}";
        PrintWriter out = response.getWriter();
        out.println(responseStr);
        out.flush();
        out.close();        
    }
}
