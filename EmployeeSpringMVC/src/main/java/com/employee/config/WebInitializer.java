package com.employee.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This class replaces web.xml
 * It initializes the DispatcherServlet automatically
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Root configuration classes (usually for service/data layer)
     * Return null if not needed
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null; // We can add root config classes if needed
    }

    /**
     * Servlet configuration classes (MVC configuration)
     * This is where we specify our WebConfig class
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    /**
     * Servlet mappings - which URLs should be handled by DispatcherServlet
     * "/" means all requests
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    /**
     * Optional: Set servlet name
     */
    @Override
    protected String getServletName() {
        return "dispatcher";
    }
}