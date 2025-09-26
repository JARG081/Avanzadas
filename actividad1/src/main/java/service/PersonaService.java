package service;

import com.mycompany.actividad1.model.Persona;
import repository.PersonaRepository;

import java.util.List;
import java.util.Objects;

public class PersonaService {

    private final PersonaRepository repo;

    public PersonaService(PersonaRepository repo) {
        this.repo = Objects.requireNonNull(repo, "repo requerido");
    }

    // ===== Helpers de validación =====
    private static void validarEmailObligatorio(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    private static void validarNombreObligatorio(String s, String campo) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException(campo + " es obligatorio");
        }
    }

    private static void validarIdPositivo(Double id) {
        if (id == null) {
            throw new IllegalArgumentException("ID es obligatorio");
        }
        if (id < 0) {
            throw new IllegalArgumentException("ID debe ser positivo");
        }
    }

    // ===== Operaciones =====

    // El repo.insertar(...) es void → este método también
    public void registrar(Double id, String nombres, String apellidos, String email) {
        validarIdPositivo(id);
        validarNombreObligatorio(nombres, "Nombres");
        validarNombreObligatorio(apellidos, "Apellidos");
        validarEmailObligatorio(email);

        Persona p = new Persona(id, nombres.trim(), apellidos.trim(), email.trim());
        repo.insertar(p); // void
    }

    public Persona buscar(Double id) {
        validarIdPositivo(id);
        return repo.buscarPorId(id);
    }

    public boolean actualizar(Double id, String nombres, String apellidos, String email) {
        validarIdPositivo(id);
        validarNombreObligatorio(nombres, "Nombres");
        validarNombreObligatorio(apellidos, "Apellidos");
        validarEmailObligatorio(email);

        Persona p = new Persona(id, nombres.trim(), apellidos.trim(), email.trim());
        return repo.actualizar(p);
    }

    public boolean eliminar(Double id) {
        validarIdPositivo(id);
        return repo.eliminar(id);
    }

    public List<Persona> listar() {
        return repo.listar();
    }
}
