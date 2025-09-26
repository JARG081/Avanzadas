package service;

import com.mycompany.actividad1.model.Profesor;
import repository.ProfesorRepository;

import java.util.List;
import java.util.Objects;

public class ProfesorService {

    private final ProfesorRepository repo;

    public ProfesorService(ProfesorRepository repo) {
        this.repo = Objects.requireNonNull(repo, "repo requerido");
    }

    private static void validarId(Double id) {
        if (id == null) throw new IllegalArgumentException("ID Persona es obligatorio");
        if (id < 0) throw new IllegalArgumentException("ID Persona debe ser positivo");
    }
    private static void validarContrato(String contrato) {
        if (contrato == null || contrato.isBlank())
            throw new IllegalArgumentException("Contrato es obligatorio");
    }

    public void registrar(Double idPersona, String contrato) {
        validarId(idPersona);
        validarContrato(contrato);
        Profesor p = new Profesor(idPersona, contrato);
        repo.insertar(p); // void
    }

    public Profesor buscar(Double idPersona) {
        validarId(idPersona);
        return repo.buscarPorIdPersona(idPersona);
    }

    public boolean actualizar(Double idPersona, String contrato) {
        validarId(idPersona);
        validarContrato(contrato);
        return repo.actualizar(new Profesor(idPersona, contrato));
    }

    public boolean eliminar(Double idPersona) {
        validarId(idPersona);
        return repo.eliminar(idPersona);
    }

    public List<Profesor> listar() {
        return repo.listar();
    }
}
