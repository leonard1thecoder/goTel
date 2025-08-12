//package later 
package org.airpenthouse.GoTel.util;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


class Connect implements Callable<Connection> {

    private static Connect instance;
    private final Connection connection;


    private Connect() {
        String url = PropertiesUtilManager.getPropertiesValue("jdbc.url");
        String username = PropertiesUtilManager.getPropertiesValue("jdbc.username");
        try {
            connection = DriverManager.getConnection(url, username, "");
            Log.info("Testing Connection");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }

    @Override
    public Connection call() {
        return connection;
    }

    private final static AtomicBoolean connectionStatus = new AtomicBoolean(false);


    public Future<Connection> getDB_data() {
        ExecutorService service = null;
        Future<Connection> connectionFuture = null;

        try {
            service = Executors.newCachedThreadPool();
            connectionFuture = service.submit(getInstance());
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