package com.pravin.listners;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import com.pravin.dblog4j.util.DBConnectioManager;

/**
 * Application Lifecycle Listener implementation class AppContextListner
 *
 */
@WebListener
public class AppContextListner implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();

        String url = context.getInitParameter("dbURL");
        String name = context.getInitParameter("dbUser");
        String passw = context.getInitParameter("dbPassword");

        try {
            DBConnectioManager connectioManager = new DBConnectioManager(url, name, passw);
            context.setAttribute("DBConnection", connectioManager.getConnection());
            System.out.println("DB Connection initialized succefully");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println(e);
        }
        // initialize log4j
        String log4jConfig = context.getInitParameter("log4j-config");
        if (log4jConfig == null) {
            System.err.println("No log4j-config init param, initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        } else {
            String webAppPath = context.getRealPath("/");
            String log4jProp = webAppPath + log4jConfig;
            File log4jConfigFile = new File(log4jProp);
            if (log4jConfigFile.exists()) {
                System.out.println("Initializing log4j with: " + log4jProp);
                DOMConfigurator.configure(log4jProp);
            } else {
                System.err.println(log4jProp + " file not found, initializing log4j with BasicConfigurator");
                BasicConfigurator.configure();
            }
        }
        System.out.println("log4j configured properly");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        Connection connection = (Connection) servletContextEvent.getServletContext().getAttribute("DBConnection");
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
