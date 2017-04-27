package com.cmcti.richard.sample.peeper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Nguyen Trong Cuong (cuong.nt1@cmctelecom.vn)
 * @since 4/27/17
 */
public class PeeperServletContextListener implements ServletContextListener {

    public static final DataStore DATA_STORE = new DataStore();

    public void contextDestroyed(ServletContextEvent sce)  {
        try {
            DATA_STORE.close();
        } catch (Exception e) {
            throw new RuntimeException("Can not close datastore", e);
        }
    }

    public void contextInitialized(ServletContextEvent sce)  {
        try {
            DATA_STORE.init();
        } catch (Exception e) {
            throw new RuntimeException("Can not initialize datastore", e);
        }
    }
}