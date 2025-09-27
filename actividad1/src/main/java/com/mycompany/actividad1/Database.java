package com.mycompany.actividad1;

import com.mycompany.actividad1.adapter.DatabaseAdapter;
import com.mycompany.actividad1.factory.domain.DomainFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static DatabaseAdapter adapter;
    
    static {
        DomainFactory factory = new DomainFactory();
        adapter = factory.databaseAdapter();
        adapter.initDatabase();
    }
    
    public static Connection getConnection() throws SQLException {
        return adapter.getConnection();
    }
    
    public static void initDatabase() {
        adapter.initDatabase();
    }
    
    public static String getDatabaseType() {
        return adapter.getDatabaseType();
    }
}
