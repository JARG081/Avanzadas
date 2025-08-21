package com.mycompany.actividad1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class ConexionBD {
    public Connection conectar(){
        Connection con = null;
        try{
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:file:C:\\Users\\josem\\OneDrive\\Documentos\\NetBeansProjects\\actividad1\\src\\main\\java\\com\\mycompany\\actividad1\\bd");
            System.out.println("Conexion exitosa ");
        }catch(Exception ex){
            System.out.println("Conexion fallida "+ ex);
        }
        return con;
    }
    public ResultSet consultar()
{
    Connection con = conectar();
    ResultSet rs = null;
    try{
        PreparedStatement ps = con.prepareStatement("select * from Estudiante");
        rs = ps.executeQuery();
    }catch(Exception ex){
            System.err.println("Error en consulta "+ ex);
    }finally{
    
        try{
            con.close();
        }catch(Exception ex){
        }
    }
    return rs;
}

public static void main(String[] args){
    ConexionBD con = new ConexionBD();
    ResultSet rs = con.consultar();
    
    try{
        while(rs.next()){
            JOptionPane.showMessageDialog(null,rs.getString(1)+", "+rs.getString(2)+", "+rs.getString(3));
            }
    }catch(Exception ex){}
}
}


