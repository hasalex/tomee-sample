package fr.sewatech.javaee.example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class SingletonTest {

    @Deployment
    public static Archive<?> createDeployment() {
        JavaArchive libJar = ShrinkWrap.create(JavaArchive.class, "lib.jar")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addClasses(MySingleton.class, HashCodeCollector.class);

        JavaArchive ejbJar = ShrinkWrap.create(JavaArchive.class, "ejb.jar")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("ejb-jar.xml", "ejb-jar.xml")
                .addClasses(StartupSingleton.class);

        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(SingletonTest.class, MyServletContextListener.class)
                .addAsWebResource(new File("src/main/webapp/index.html"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return ShrinkWrap.create(EnterpriseArchive.class, "test.ear")
                .addAsLibraries(libJar)
                .addAsModule(ejbJar)
                .addAsModule(war)
                .addAsManifestResource("application-with-ejb-jar.xml", "application.xml");
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
