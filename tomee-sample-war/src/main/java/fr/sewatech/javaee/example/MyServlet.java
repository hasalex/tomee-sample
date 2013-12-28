package fr.sewatech.javaee.example;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexis Hassler
 */
@WebServlet(urlPatterns = "/my")
public class MyServlet extends HttpServlet {
    @Inject BeanManager beanManager;
    @Inject MySingleton mySingleton;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write(beanManager.hashCode() + "/" + mySingleton.hashCode());
    }
}
