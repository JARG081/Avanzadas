package ui;

import com.mycompany.actividad1.factory.app.AppFactory;
import com.mycompany.actividad1.model.Curso;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author josem
 */
public class Observer extends javax.swing.JFrame {
    
    private final AppFactory factory;
    private DefaultTableModel modelo;

    public Observer(AppFactory factory) {
        this.factory = factory;
        initComponents();
        initTabla();
        setTitle("Observer de Cursos");
        setLocationByPlatform(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // Suscribirse al mismo CursoService compartido
        factory.cursoService().addObserver(curso ->
            SwingUtilities.invokeLater(() -> pintarUltimo(curso))
        );
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        cursoObserver = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cursoObserver.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(cursoObserver);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    private void initTabla() {
        modelo = (DefaultTableModel) cursoObserver.getModel();
        // Asegura cabeceras y cero filas
        modelo.setColumnIdentifiers(new Object[]{"ID","Nombre","Programa","Activo"});
        modelo.setRowCount(0);
    }

    private void pintarUltimo(Curso c) {
        final String prog = (c.getPrograma()==null)
            ? "(sin programa)"
            : (c.getPrograma().getNombre()!=null && !c.getPrograma().getNombre().isBlank()
                ? c.getPrograma().getNombre()
                : String.valueOf(c.getPrograma().getId()));

        // Mostrar SOLO el último: limpiar y agregar 1 fila
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
            c.getID(),
            c.getNombre(),
            prog,
            Boolean.TRUE.equals(c.getActivo()) ? "Sí" : "No"
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable cursoObserver;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
