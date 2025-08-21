    package com.mycompany.actividad1;

    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.ResultSet;
    import java.sql.Statement;

    public class Actividad1{

        public static void main(String[] args) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            try {
                String url = "jdbc:h2:file:C:\\Users\\josem\\OneDrive\\Documentos\\NetBeansProjects\\actividad1\\src\\main\\java\\com\\mycompany\\actividad1\\bd"; 
                String user = "sa";
                String password = "";
                Class.forName("org.h2.Driver");
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("✅ Conexión exitosa a la base de datos H2");
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM PROGRAMA ");

                // Mostrar resultados
                while (rs.next()) {
                    System.out.println("Fila encontrada: " + rs.getString(1));
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception e) {}
                try { if (stmt != null) stmt.close(); } catch (Exception e) {}
                try { if (conn != null) conn.close(); } catch (Exception e) {}
            }
        }
    }
