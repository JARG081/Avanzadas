package service;

import com.mycompany.actividad1.model.Curso;
import com.mycompany.actividad1.model.Estudiante;
import com.mycompany.actividad1.model.Inscripcion;
import repository.InscripcionRepository;

import java.util.List;
import java.util.Objects;

public class InscripcionService {

    private final InscripcionRepository repo;

    public InscripcionService(InscripcionRepository repo) {
        this.repo = Objects.requireNonNull(repo);
    }

    private static void validarIdPos(Double id, String campo) {
        if (id == null || id < 0) throw new IllegalArgumentException(campo + " inválido");
    }
    private static void validarIntPos(Integer v, String campo) {
        if (v == null || v < 0) throw new IllegalArgumentException(campo + " inválido");
    }

    public boolean insertar(Double idCurso, Double idEstudiante, Integer anio, Integer semestre) {
        validarIdPos(idCurso, "ID Curso");
        validarIdPos(idEstudiante, "ID Estudiante");
        validarIntPos(anio, "Año");
        validarIntPos(semestre, "Semestre");

        if (repo.existe(idCurso, idEstudiante, anio, semestre))
            throw new IllegalArgumentException("Ya existe esta inscripción.");

        Curso c = new Curso(); c.setID(idCurso);
        Estudiante e = new Estudiante(idEstudiante, "", "", "", "", null);
        Inscripcion i = new Inscripcion(c, anio, semestre, e);
        return repo.insertar(i);
    }

    public Inscripcion buscar(Double idCurso, Double idEstudiante, Integer anio, Integer semestre) {
        validarIdPos(idCurso, "ID Curso");
        validarIdPos(idEstudiante, "ID Estudiante");
        validarIntPos(anio, "Año");
        validarIntPos(semestre, "Semestre");
        return repo.buscar(idCurso, idEstudiante, anio, semestre);
    }

    public boolean actualizar(Double cOld, Double eOld, Integer aOld, Integer sOld,
                              Double cNew, Double eNew, Integer aNew, Integer sNew) {
        validarIdPos(cOld, "ID Curso (actual)");
        validarIdPos(eOld, "ID Estudiante (actual)");
        validarIntPos(aOld, "Año (actual)");
        validarIntPos(sOld, "Semestre (actual)");

        validarIdPos(cNew, "ID Curso (nuevo)");
        validarIdPos(eNew, "ID Estudiante (nuevo)");
        validarIntPos(aNew, "Año (nuevo)");
        validarIntPos(sNew, "Semestre (nuevo)");

        if (( !cOld.equals(cNew) || !eOld.equals(eNew) || !aOld.equals(aNew) || !sOld.equals(sNew) )
                && repo.existe(cNew, eNew, aNew, sNew)) {
            throw new IllegalArgumentException("La nueva inscripción ya existe.");
        }

        Curso c = new Curso(); c.setID(cNew);
        Estudiante e = new Estudiante(eNew, "", "", "", "", null);
        Inscripcion nueva = new Inscripcion(c, aNew, sNew, e);
        return repo.actualizar(nueva, cOld, eOld, aOld, sOld);
    }

    public boolean eliminar(Double idCurso, Double idEstudiante, Integer anio, Integer semestre) {
        validarIdPos(idCurso, "ID Curso");
        validarIdPos(idEstudiante, "ID Estudiante");
        validarIntPos(anio, "Año");
        validarIntPos(semestre, "Semestre");
        return repo.eliminar(idCurso, idEstudiante, anio, semestre);
    }

    public List<Inscripcion> listar() { return repo.listar(); }
}
