package com.mycompany.actividad1;

import com.mycompany.actividad1.adapter.DatabaseAdapter;
import com.mycompany.actividad1.factory.InfraFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static DatabaseAdapter adapter;
    
    static {
        InfraFactory factory = new InfraFactory();
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
