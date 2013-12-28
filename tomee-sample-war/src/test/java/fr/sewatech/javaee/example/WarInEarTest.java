package fr.sewatech.javaee.example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.io.File;

@RunWith(Arquillian.class)
public class WarInEarTest {

    @Deployment
    public static EnterpriseArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(MySingleton.class, HashCodeCollector.class)
                .addClasses(StartupSingleton.class)
                .addClasses(MyServletContextListener.class)
                .addClasses(MyServlet.class)
                .addClasses(WarInEarTest.class)
                .addAsWebResource(new File("src/main/webapp/index.html"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println(war.toString(true));

        return ShrinkWrap.create(EnterpriseArchive.class, "test.ear")
                .addAsModule(war)
                .addAsManifestResource("application-without-ejb-jar.xml", "application.xml");
    }

    @Inject BeanManager beanManager;
    @Inject MySingleton mySingleton;

    @Test
    public void testItNow() throws Exception {
        String codes = beanManager.hashCode() + "/" + mySingleton.getClass().getClassLoader().hashCode() + "/" + mySingleton.hashCode();
        System.out.println(codes);
    }
}
