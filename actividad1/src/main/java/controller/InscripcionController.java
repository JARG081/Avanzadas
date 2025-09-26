package controller;

import com.mycompany.actividad1.model.Inscripcion;
import service.InscripcionService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) { this.service = service; }

    private static Integer parseInt (String s,String campo){ try{ return Integer.valueOf(s.trim()); }catch(Exception e){ throw new IllegalArgumentException(campo+" inválido: "+s); } }
    private static Double  parseId  (String s,String campo){ try{ Double v=Double.valueOf(s.trim()); if(v<0) throw new RuntimeException(); return v; }catch(Exception e){ throw new IllegalArgumentException(campo+" inválido: "+s); } }

    public boolean insertar(String cursoIdTxt, String estIdTxt, String anioTxt, String semestreTxt) {
        return service.insertar(
            parseId(cursoIdTxt,"ID Curso"),
            parseId(estIdTxt,"ID Estudiante"),
            parseInt(anioTxt,"Año"),
            parseInt(semestreTxt,"Semestre")
        );
    }

    public Inscripcion buscar(String cursoIdTxt, String estIdTxt, String anioTxt, String semestreTxt) {
        return service.buscar(
            parseId(cursoIdTxt,"ID Curso"),
            parseId(estIdTxt,"ID Estudiante"),
            parseInt(anioTxt,"Año"),
            parseInt(semestreTxt,"Semestre")
        );
    }

    public boolean actualizar(String cOld,String eOld,String aOld,String sOld,
                              String cNew,String eNew,String aNew,String sNew) {
        return service.actualizar(
            parseId (cOld,"ID Curso (actual)"),
            parseId (eOld,"ID Estudiante (actual)"),
            parseInt(aOld,"Año (actual)"),
            parseInt(sOld,"Semestre (actual)"),
            parseId (cNew,"ID Curso (nuevo)"),
            parseId (eNew,"ID Estudiante (nuevo)"),
            parseInt(aNew,"Año (nuevo)"),
            parseInt(sNew,"Semestre (nuevo)")
        );
    }

    public boolean eliminar(String cursoIdTxt, String estIdTxt, String anioTxt, String semestreTxt) {
        return service.eliminar(
            parseId(cursoIdTxt,"ID Curso"),
            parseId(estIdTxt,"ID Estudiante"),
            parseInt(anioTxt,"Año"),
            parseInt(semestreTxt,"Semestre")
        );
    }

    public List<Inscripcion> listar() { return service.listar(); }

    public DefaultTableModel modeloTablaTodos() {
        DefaultTableModel m = new DefaultTableModel(new Object[]{
            "Curso (ID)", "Curso (Nombre)", "Estudiante (ID)", "Estudiante", "Año", "Semestre"
        }, 0) { @Override public boolean isCellEditable(int r,int c){return false;} };

        for (Inscripcion i : listar()) {
            String estNombre = (i.getEstudiante()==null) ? "" :
                (i.getEstudiante().getNombres()+" "+i.getEstudiante().getApellidos());
            m.addRow(new Object[]{
                i.getCurso()==null? null : i.getCurso().getID(),
                i.getCurso()==null? ""   : i.getCurso().getNombre(),
                i.getEstudiante()==null? null : i.getEstudiante().getId(),
                estNombre,
                i.getAnio(),
                i.getSemestre()
            });
        }
        return m;
    }
}
