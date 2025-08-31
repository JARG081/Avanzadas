package com.mycompany.actividad1;
import javax.swing.SwingUtilities;
import ui.Pantalla;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pantalla pantalla = new Pantalla();
            pantalla.setVisible(true);
            pantalla.setLocationRelativeTo(null);
        });
    }
}
