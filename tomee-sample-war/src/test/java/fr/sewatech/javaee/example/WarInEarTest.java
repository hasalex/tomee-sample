package fr.sewatech.javaee.example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(Arquillian.class)
public class WarInEarTest {

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(WarInEarTest.class.getPackage())
                .addAsWebResource(new File("src/main/webapp/index.html"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"));

//        System.out.println(war.toString(true));

        return ShrinkWrap.create(EnterpriseArchive.class, "test.ear")
                .addAsModule(war)
                .addAsManifestResource("application.xml");
    }

    @Test
    public void testItNow() throws Exception {
    }
}
