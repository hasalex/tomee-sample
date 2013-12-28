package fr.sewatech.javaee.example;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * @author Alexis Hassler
 */
@Singleton @LocalBean @Startup
public class StartupSingleton {
    private static final Logger LOGGER = Logger.getLogger(StartupSingleton.class.getName());

    @Inject BeanManager beanManager;
    @Inject MySingleton mySingleton;

    @PostConstruct
    public void init() {
        HashCodeCollector.singleton = beanManager.hashCode() + "/" + mySingleton.getClass().getClassLoader().hashCode() + "/" + mySingleton.hashCode();
        LOGGER.info("=====> " + HashCodeCollector.singleton);
    }
}
