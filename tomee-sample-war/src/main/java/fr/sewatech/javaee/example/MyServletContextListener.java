package fr.sewatech.javaee.example;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

public class MyServletContextListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(MyServletContextListener.class.getName());

    @Inject MySingleton mySingleton;
    @Inject BeanManager beanManager;

	@Override
    public void contextDestroyed(ServletContextEvent arg0) {
	}
 
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
        HashCodeCollector.listener = beanManager.hashCode() + "/" + mySingleton.getClass().getClassLoader().hashCode() + "/" + mySingleton.hashCode();
        LOGGER.info("=====> " + HashCodeCollector.listener);
	}
}