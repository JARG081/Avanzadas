package controller;

import com.mycompany.actividad1.model.Inscripcion;
import service.CursosInscritosService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CursosInscritosController {

    private final CursosInscritosService service;

    public CursosInscritosController(CursosInscritosService service) { this.service = service; }

    private static Double pD(String s, String campo){
        if (s==null || s.isBlank()) throw new IllegalArgumentException(campo+" es obligatorio");
        try{
            Double v = Double.valueOf(s.trim());
            if (v < 0) throw new IllegalArgumentException(campo+" debe ser positivo");
            return v;
        }catch(NumberFormatException e){ throw new IllegalArgumentException(campo+" inválido"); }
    }
    private static Integer pI(String s, String campo){
        if (s==null || s.isBlank()) throw new IllegalArgumentException(campo+" es obligatorio");
        try{ return Integer.valueOf(s.trim()); }
        catch(NumberFormatException e){ throw new IllegalArgumentException(campo+" inválido"); }
    }

    public boolean insertar(String cursoId, String estId, String anio, String semestre){
    try {
        service.registrar(
            pD(cursoId,"ID Curso"),
            pD(estId,"ID Estudiante"),
            pI(anio,"Año"),
            pI(semestre,"Semestre")
        );
        return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean actualizar(String cursoIdOld, String estIdOld, String anioOld, String semOld,
                              String cursoIdNew, String estIdNew, String anioNew, String semNew){
        return service.actualizar(
            pD(cursoIdOld,"ID Curso"), pD(estIdOld,"ID Estudiante"), pI(anioOld,"Año"), pI(semOld,"Semestre"),
            pD(cursoIdNew,"ID Curso"), pD(estIdNew,"ID Estudiante"), pI(anioNew,"Año"), pI(semNew,"Semestre"));
    }

    public boolean eliminar(String cursoId, String estId, String anio, String semestre){
        return service.eliminar(pD(cursoId,"ID Curso"), pD(estId,"ID Estudiante"), pI(anio,"Año"), pI(semestre,"Semestre"));
    }

    public Inscripcion buscar(String cursoId, String estId, String anio, String semestre){
        return service.buscar(pD(cursoId,"ID Curso"), pD(estId,"ID Estudiante"), pI(anio,"Año"), pI(semestre,"Semestre"));
    }

    public List<Inscripcion> listar(){ return service.listar(); }

    public DefaultTableModel modeloTablaTodos() {
        DefaultTableModel m = new DefaultTableModel(
            new Object[]{"ID Curso","Curso","ID Est","Estudiante","Año","Semestre"}, 0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for (Inscripcion i : listar()) {
            m.addRow(new Object[]{
                i.getCurso()==null? null : i.getCurso().getID(),
                i.getCurso()==null? ""   : i.getCurso().getNombre(),
                i.getEstudiante()==null? null : i.getEstudiante().getId(),
                i.getEstudiante()==null? ""   : (i.getEstudiante().getNombres()+" "+i.getEstudiante().getApellidos()),
                i.getAnio(), i.getSemestre()
            });
        }
        return m;
    }
}
