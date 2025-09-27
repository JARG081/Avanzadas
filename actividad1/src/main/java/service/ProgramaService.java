package service;

import com.mycompany.actividad1.model.Facultad;
import com.mycompany.actividad1.model.Programa;
import repository.ProgramaRepository;
import java.util.List;
import java.util.Objects;

public class ProgramaService {

    private final ProgramaRepository repo;

    public ProgramaService(ProgramaRepository repo) {
        this.repo = Objects.requireNonNull(repo);
    }

    private static void validarIdPos(Double id) {
        if (id == null || id < 0) throw new IllegalArgumentException("ID de programa inválido");
    }
    private static void validarNombre(String s) {
        if (s == null || s.isBlank()) throw new IllegalArgumentException("Nombre es obligatorio");
    }
    private static int validarDuracion(String s) {
        try {
            int v = Integer.parseInt(s.trim());
            if (v <= 0) throw new IllegalArgumentException("Duración debe ser > 0");
            return v;
        } catch (Exception e) {
            throw new IllegalArgumentException("Duración inválida");
        }
    }
        private static java.sql.Date parseFechaSql(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            return java.sql.Date.valueOf(s.trim()); // espera "YYYY-MM-DD"
        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha (YYYY-MM-DD) inválida");
        }
    }

    public void registrar(Double id, String nombre, String duracionStr, String registroStr, Double idFacultad) {
        validarIdPos(id);
        validarNombre(nombre);
        int duracion = validarDuracion(duracionStr);
        java.sql.Date reg = parseFechaSql(registroStr);

        Facultad f = null;
        if (idFacultad != null) {
            if (idFacultad < 0) throw new IllegalArgumentException("ID Facultad inválido");
            f = new Facultad();//coonstructor no se puede arrancar con los tipos dados
            f.setID(idFacultad);
        }

        Programa p = new Programa();
        p.setId(id);
        p.setNombre(nombre.trim());
        p.setDuracion(duracion);
        p.setRegistro(reg);
        p.setFacultad(f);

        repo.insertar(p);
    }

    public boolean actualizar(Double id, String nombre, String duracionStr, String registroStr, Double idFacultad) {
        validarIdPos(id);
        validarNombre(nombre);
        int duracion = validarDuracion(duracionStr);
        java.sql.Date reg = parseFechaSql(registroStr);

        Facultad f = null;
        if (idFacultad != null) {
            if (idFacultad < 0) throw new IllegalArgumentException("ID Facultad inválido");
            f = new Facultad();//coonstructor no se puede arrancar con los tipos dados
            f.setID(idFacultad);
        }

        Programa p = new Programa();
        p.setId(id);
        p.setNombre(nombre.trim());
        p.setDuracion(duracion);
        p.setRegistro(reg);
        p.setFacultad(f);

        return repo.actualizar(p);
    }

    public boolean eliminar(Double id) {
        validarIdPos(id);
        return repo.eliminar(id);
    }

    public Programa buscar(Double id) {
        validarIdPos(id);
        return repo.buscarPorId(id);
    }

    public List<Programa> listar() {
        return repo.listar();
    }
}
