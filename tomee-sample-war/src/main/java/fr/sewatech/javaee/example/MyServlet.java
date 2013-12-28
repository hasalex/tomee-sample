package fr.sewatech.javaee.example;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Alexis Hassler
 */
@WebServlet(urlPatterns = "/my", loadOnStartup = 1)
public class MyServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(MyServlet.class.getName());

    @Inject BeanManager beanManager;
    @Inject MySingleton mySingleton;


    @Override
    public void init() throws ServletException {
        HashCodeCollector.servlet = beanManager.hashCode() + "/" + mySingleton.getClass().getClassLoader().hashCode() + "/" + mySingleton.hashCode();
        LOGGER.info("=====> " + HashCodeCollector.listener);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashCodeCollector.servlet = beanManager.hashCode() + "/" + mySingleton.getClass().getClassLoader().hashCode() + "/" + mySingleton.hashCode();
        LOGGER.info("=====> " + HashCodeCollector.listener);
        response.getWriter().write(HashCodeCollector.listener);
    }
}
