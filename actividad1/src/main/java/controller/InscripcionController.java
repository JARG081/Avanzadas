package controller;

import com.mycompany.actividad1.dao.CursoDAO;
import com.mycompany.actividad1.dao.EstudianteDAO;
import com.mycompany.actividad1.dao.InscripcionDAO;
import com.mycompany.actividad1.model.Curso;
import com.mycompany.actividad1.model.Estudiante;
import com.mycompany.actividad1.model.Inscripcion;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class InscripcionController {

    private final InscripcionDAO dao = new InscripcionDAO();

    // Crear (insertar)
    public boolean insertar(String cursoIdTxt, String estIdTxt, String anioTxt, String semestreTxt) throws Exception {
        int cursoId = parseInt(cursoIdTxt);
        long estId = parseLong(estIdTxt);
        int anio = parseInt(anioTxt);
        int semestre = parseInt(semestreTxt);

        // Validar existencia de curso y estudiante
        CursoDAO cdao = new CursoDAO();
        Curso curso = cdao.buscarPorId(cursoId);
        if (curso == null) throw new IllegalArgumentException("No existe Curso con ID " + cursoId);

        EstudianteDAO edao = new EstudianteDAO();
        Estudiante est = edao.buscarPorIdPersona(estId); // ya lo definimos
        if (est == null) throw new IllegalArgumentException("No existe Estudiante (persona) con ID " + estId);

        // Regla: no duplicar PK
        if (dao.existe(cursoId, estId, anio, semestre))
            throw new IllegalArgumentException("Ya existe esta inscripción.");

        Inscripcion i = new Inscripcion(curso, anio, semestre, est);
        return dao.insertar(i);
    }

    // Buscar por PK
    public Inscripcion buscar(String cursoIdTxt, String estIdTxt, String anioTxt, String semestreTxt) throws Exception {
        int cursoId = parseInt(cursoIdTxt);
        long estId = parseLong(estIdTxt);
        int anio = parseInt(anioTxt);
        int semestre = parseInt(semestreTxt);
        return dao.buscar(cursoId, estId, anio, semestre);
    }

    // Actualizar (permite cambiar cualquier parte de la PK: requiere la PK vieja)
    public boolean actualizar(String cursoIdOld, String estIdOld, String anioOld, String semestreOld,
                              String cursoIdNew, String estIdNew, String anioNew, String semestreNew) throws Exception {

        int cOld = parseInt(cursoIdOld);
        long eOld = parseLong(estIdOld);
        int aOld = parseInt(anioOld);
        int sOld = parseInt(semestreOld);

        int cNew = parseInt(cursoIdNew);
        long eNew = parseLong(estIdNew);
        int aNew = parseInt(anioNew);
        int sNew = parseInt(semestreNew);

        // Validar existencia de curso y estudiante nuevos
        CursoDAO cdao = new CursoDAO();
        Curso c = cdao.buscarPorId(cNew);
        if (c == null) throw new IllegalArgumentException("No existe Curso con ID " + cNew);

        EstudianteDAO edao = new EstudianteDAO();
        Estudiante est = edao.buscarPorIdPersona(eNew);
        if (est == null) throw new IllegalArgumentException("No existe Estudiante (persona) con ID " + eNew);

        // Si la PK cambia, evitar duplicado
        boolean cambiaPK = (cOld != cNew) || (eOld != eNew) || (aOld != aNew) || (sOld != sNew);
        if (cambiaPK && dao.existe(cNew, eNew, aNew, sNew))
            throw new IllegalArgumentException("La nueva inscripción ya existe.");

        Inscripcion nueva = new Inscripcion(c, aNew, sNew, est);
        return dao.actualizar(nueva, cOld, eOld, aOld, sOld);
    }

    // Eliminar
    public boolean eliminar(String cursoIdTxt, String estIdTxt, String anioTxt, String semestreTxt) throws Exception {
        int cursoId = parseInt(cursoIdTxt);
        long estId = parseLong(estIdTxt);
        int anio = parseInt(anioTxt);
        int semestre = parseInt(semestreTxt);
        return dao.eliminar(cursoId, estId, anio, semestre);
    }

    // Listar + modelo de tabla
    public List<Inscripcion> listar() throws SQLException { return dao.listar(); }

    public DefaultTableModel modeloTablaTodos() throws SQLException {
        DefaultTableModel m = new DefaultTableModel(new Object[]{
            "Curso (ID)", "Curso (Nombre)", "Estudiante (ID)", "Estudiante", "Año", "Semestre"
        }, 0);
        for (Inscripcion i : listar()) {
            String estNombre = (i.getEstudiante()==null) ? "" :
                (i.getEstudiante().getNombres() + " " + i.getEstudiante().getApellidos());
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

    // utils parse
    private int parseInt(String s){ try{return Integer.parseInt(s.trim());}catch(Exception e){throw new IllegalArgumentException("Entero inválido: "+s);}}
    private long parseLong(String s){ try{return Long.parseLong(s.trim());}catch(Exception e){throw new IllegalArgumentException("Número (long) inválido: "+s);}}
}
