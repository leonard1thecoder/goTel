package org.airpenthouse.GoTel.util;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@NoArgsConstructor
@Component
public class CommonEntityMethod {


    public PreparedStatement databaseConfig(String query) throws ExecutionException, InterruptedException, TimeoutException, SQLException {
        Future<Connection> connect = Connect.getDB_data();
        assert connect != null;
        return connect.get(15, TimeUnit.SECONDS).prepareStatement(query);
    }
}
