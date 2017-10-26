package com.waracle.cakemgr.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemgr.beans.CakeEntity;
import com.waracle.cakemgr.persistent.HibernateUtil;
import com.waracle.cakemgr.utils.ReadProperties;

@WebServlet(urlPatterns = {"/"}, loadOnStartup = 1)
public class RootServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	private static final Logger logger = Logger.getLogger(RootServlet.class);
	
	
	@Override
    public void init() throws ServletException {
        super.init();
        logger.info("init started, downloading cake json");
        
		String url = ReadProperties.readProperty("cake");
        
        try {
        	InputStream inputStream = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }

            logger.info("parsing cake json");
            JsonParser parser = new JsonFactory().createParser(buffer.toString());
            if (JsonToken.START_ARRAY != parser.nextToken()) {
                throw new Exception("bad token");
            }

            JsonToken nextToken = parser.nextToken();
            while(nextToken == JsonToken.START_OBJECT) {
            	logger.info("creating cake entity");

                CakeEntity cakeEntity = new CakeEntity();
                logger.info(parser.nextFieldName());
                cakeEntity.setTitle(parser.nextTextValue());

                logger.info(parser.nextFieldName());
                cakeEntity.setDescription(parser.nextTextValue());

                logger.info(parser.nextFieldName());
                cakeEntity.setImage(parser.nextTextValue());

                persistData(cakeEntity);

                nextToken = parser.nextToken();
                logger.info(nextToken);

                nextToken = parser.nextToken();
                logger.info(nextToken);
            }

        } catch (Exception ex) {
            throw new ServletException(ex);
        }

        logger.info("init finished");
    }

    private void persistData(CakeEntity entity) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(entity);
            logger.info("adding cake entity");
            session.getTransaction().commit();
        } catch (ConstraintViolationException ex) {

        }
        session.close();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Session session = HibernateUtil.getSessionFactory().openSession();
        List<CakeEntity> list = session.createCriteria(CakeEntity.class).list();
        session.close();
        response.getWriter().println("Cakes available are: ");

        for (CakeEntity entity : list) {
        	response.getWriter().println("title\" : " + entity.getTitle() + ", ");
        	response.getWriter().println("desc\" : " + entity.getDescription() + ",");
        	response.getWriter().println("image\" : " + entity.getImage());
        }
        
    }
	
}
