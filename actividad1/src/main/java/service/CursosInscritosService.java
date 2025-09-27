package service;

import com.mycompany.actividad1.model.CursosInscritos;
import com.mycompany.actividad1.model.Curso;
import com.mycompany.actividad1.model.Estudiante;
import com.mycompany.actividad1.model.Inscripcion;
import repository.CursosInscritosRepository;

import java.util.List;
import java.util.Objects;

public class CursosInscritosService {

    private final CursosInscritosRepository repo;

    public CursosInscritosService(CursosInscritosRepository repo) {
        this.repo = Objects.requireNonNull(repo);
    }

    private static void validar(Double cursoId, Double estId, Integer anio, Integer semestre){
        if (cursoId == null || cursoId < 0) throw new IllegalArgumentException("ID Curso inválido");
        if (estId == null  || estId  < 0)  throw new IllegalArgumentException("ID Estudiante inválido");
        if (anio == null   || anio   <= 0) throw new IllegalArgumentException("Año inválido");
        if (semestre == null || (semestre != 1 && semestre != 2))
            throw new IllegalArgumentException("Semestre debe ser 1 o 2");
    }

    private static Inscripcion toInscripcion(CursosInscritos ci){
        Curso c = new Curso(); c.setID(ci.getCursoId()); c.setNombre(ci.getCursoNombre());
        Estudiante e = new Estudiante(); e.setId(ci.getEstudianteId());//me pide cambiar constructor
        Inscripcion i = new Inscripcion();
        i.setCurso(c); i.setEstudiante(e);
        i.setAnio(ci.getAnio()); i.setSemestre(ci.getSemestre());
        return i;
    }

    public void registrar(Double cursoId, Double estId, Integer anio, Integer semestre){
        validar(cursoId, estId, anio, semestre);
        if (repo.existe(cursoId, estId, anio, semestre))
            throw new IllegalArgumentException("Ya existe la inscripción");
        CursosInscritos ci = new CursosInscritos();
        ci.setCursoId(cursoId); ci.setEstudianteId(estId); ci.setAnio(anio); ci.setSemestre(semestre);
        repo.insertar(toInscripcion(ci));
    }

    public boolean actualizar(Double cursoIdOld, Double estIdOld, Integer anioOld, Integer semOld,
                              Double cursoIdNew, Double estIdNew, Integer anioNew, Integer semNew){
        validar(cursoIdOld, estIdOld, anioOld, semOld);
        validar(cursoIdNew, estIdNew, anioNew, semNew);
        CursosInscritos ci = new CursosInscritos();
        ci.setCursoId(cursoIdNew); ci.setEstudianteId(estIdNew); ci.setAnio(anioNew); ci.setSemestre(semNew);
        return repo.actualizar(toInscripcion(ci), cursoIdOld, estIdOld, anioOld, semOld);
    }

    public boolean eliminar(Double cursoId, Double estId, Integer anio, Integer semestre){
        validar(cursoId, estId, anio, semestre);
        return repo.eliminar(cursoId, estId, anio, semestre);
    }

    public Inscripcion buscar(Double cursoId, Double estId, Integer anio, Integer semestre){
        validar(cursoId, estId, anio, semestre);
        return repo.buscar(cursoId, estId, anio, semestre);
    }

    public List<Inscripcion> listar(){ return repo.listar(); }
}
