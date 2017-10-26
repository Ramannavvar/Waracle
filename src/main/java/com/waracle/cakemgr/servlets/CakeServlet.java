package com.waracle.cakemgr.servlets;

import com.waracle.cakemgr.beans.CakeEntity;
import com.waracle.cakemgr.persistent.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/cakes")
public class CakeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CakeServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<CakeEntity> list = session.createCriteria(CakeEntity.class).list();
        session.close();
        
        req.setAttribute("cakes", list);
        getServletConfig().getServletContext().getRequestDispatcher("/cakes.jsp").forward(req ,resp);
        
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	CakeEntity entity = new CakeEntity();
    	entity.setTitle(req.getParameter("addCakeTitle"));
    	entity.setDescription(req.getParameter("addCakeDesc"));
    	entity.setImage(req.getParameter("addCakeImg"));
    	
    	logger.info("Post attributes: " + entity.getTitle() + " " + entity.getDescription() + " " + entity.getImage());
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(entity);
            logger.info("adding cake entity");
            session.getTransaction().commit();
        } catch (ConstraintViolationException ex) {

        }
        session.close();
        req.getAttribute("cakes");
        
        Session session2 = HibernateUtil.getSessionFactory().openSession();
        List<CakeEntity> list = session2.createCriteria(CakeEntity.class).list();
        session2.close();
        
        req.setAttribute("cakes", list);        
        req.getSession().setAttribute("message", "Cake Added");
        getServletConfig().getServletContext().getRequestDispatcher("/cakes.jsp").forward(req ,resp);
	
    }
}
