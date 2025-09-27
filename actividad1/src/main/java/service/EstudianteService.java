package service;

import com.mycompany.actividad1.model.Estudiante;
import com.mycompany.actividad1.model.Programa;

import java.util.List;
import java.util.Objects;
import repository.EstudianteRepository;

public class EstudianteService {

    private final EstudianteRepository repo;

    public EstudianteService(EstudianteRepository repo) {
        this.repo = Objects.requireNonNull(repo, "repo requerido");
    }

    private static void validarId(Double id) {
        if (id == null) throw new IllegalArgumentException("ID Persona es obligatorio");
        if (id < 0) throw new IllegalArgumentException("ID Persona debe ser positivo");
    }
    private static void validarCodigo(String codigo) {
        if (codigo == null || codigo.isBlank())
            throw new IllegalArgumentException("Código es obligatorio");
    }

    private static Programa programaFrom(String idProgramaStr) {
        if (idProgramaStr == null || idProgramaStr.isBlank()) return null;
        Double idProg;
        try {
            idProg = Double.valueOf(idProgramaStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID Programa inválido");
        }
        Programa p = new Programa();
        p.setId(idProg);
        return p;
    }

    public void registrar(Double idPersona, String codigo, String idProgramaStr,
                          String nombres, String apellidos, String email) {
        validarId(idPersona);
        validarCodigo(codigo);
        Estudiante e = new Estudiante(idPersona, nombres, apellidos, email, codigo.trim(), programaFrom(idProgramaStr));
        repo.insertar(e);
    }

    public Estudiante buscar(Double idPersona) {
        validarId(idPersona);
        return repo.buscarPorIdPersona(idPersona);
    }

    public boolean actualizar(Double idPersona, String codigo, String idProgramaStr,
                              String nombres, String apellidos, String email) {
        validarId(idPersona);
        validarCodigo(codigo);
        Estudiante e = new Estudiante(idPersona, nombres, apellidos, email, codigo.trim(), programaFrom(idProgramaStr));
        return repo.actualizar(e);
    }

    public boolean eliminar(Double idPersona) {
        validarId(idPersona);
        return repo.eliminar(idPersona);
    }

    public List<Estudiante> listar() {
        return repo.listar();
    }
}
