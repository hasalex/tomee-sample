package fr.sewatech.javaee.example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class StandaloneWarTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(MySingleton.class, HashCodeCollector.class)
                .addClasses(StartupSingleton.class)
                .addClasses(StandaloneWarTest.class, MyServletContextListener.class, MyServlet.class)
                .addAsWebResource(new File("src/main/webapp/index.html"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject BeanManager beanManager;
    @Inject MySingleton mySingleton;

    @Test
    public void testItNow() throws Exception {
        String codes = beanManager.hashCode() + "/" + mySingleton.getClass().getClassLoader().hashCode() + "/" + mySingleton.hashCode();
        System.out.println(codes);
        assertThat("listener", HashCodeCollector.listener, equalTo(codes));
        assertThat("singleton", HashCodeCollector.singleton, equalTo(codes));
    }
}