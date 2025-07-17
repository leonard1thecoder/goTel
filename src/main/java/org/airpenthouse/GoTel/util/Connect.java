//package later 
package org.airpenthouse.GoTel.util;


import java.sql.DriverManager;
import java.sql.Connection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


class Connect implements Callable<Connection> {

    private String url;
    private String password;
    public String username;

    private Connection connection;


    private Connect() {
        url = PropertiesUtilManager.getPropertiesValue("jdbc.url");
        username = PropertiesUtilManager.getPropertiesValue("jdbc.username");
    }

    @Override
    public Connection call() throws Exception {

        try {

            connection = DriverManager.getConnection(url, username, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    private final static AtomicBoolean connectionStatus = new AtomicBoolean(false);


    public static Future<Connection> getDB_data() {
        ExecutorService service = null;
        Future<Connection> connectionFuture = null;

        try {
            service = Executors.newCachedThreadPool();
            connectionFuture = service.submit(new Connect());
            connectionStatus.set(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (service != null && connectionStatus.get()) {
                service.shutdown();
                return connectionFuture;
            } else {
                return null;
            }
        }
    }
}