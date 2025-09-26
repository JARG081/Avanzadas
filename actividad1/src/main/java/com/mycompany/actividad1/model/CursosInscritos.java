package com.mycompany.actividad1.model;

import com.mycompany.actividad1.dao.InscripcionJdbcRepository;
import repository.InscripcionRepository;

import java.util.ArrayList;
import java.util.List;

public class CursosInscritos implements Servicios {

    private final List<Inscripcion> listado = new ArrayList<>();
    // Sustituimos el DAO antiguo por el Repository
    private final InscripcionRepository repo = new InscripcionJdbcRepository();

    public void cargarDatos() {
        listado.clear();
        // El repo.listar() ya maneja SQL internamente (RuntimeException si falla)
        listado.addAll(repo.listar());
    }

    public boolean inscribirCurso(Inscripcion i) {
        if (i == null || i.getCurso() == null || i.getEstudiante() == null
                || i.getAnio() == null || i.getSemestre() == null) return false;

        // Usamos Double/Integer directamente (nada de intValue())
        Double cursoId = i.getCurso().getID();
        Double estId   = i.getEstudiante().getId();
        Integer anio   = i.getAnio();
        Integer sem    = i.getSemestre();

        if (repo.existe(cursoId, estId, anio, sem)) return false; // ya existe

        boolean ok = repo.insertar(i);
        if (ok) listado.add(i);
        return ok;
    }

    public boolean eliminar(Inscripcion i) {
        if (i == null || i.getCurso() == null || i.getEstudiante() == null
                || i.getAnio() == null || i.getSemestre() == null) return false;

        boolean ok = repo.eliminar(
            i.getCurso().getID(),
            i.getEstudiante().getId(),
            i.getAnio(),
            i.getSemestre()
        );
        if (ok) listado.remove(i);
        return ok;
    }

    public boolean actualizar(Inscripcion nueva, Inscripcion viejaClave) {
        if (nueva == null || viejaClave == null) return false;

        boolean ok = repo.actualizar(
            nueva,
            viejaClave.getCurso().getID(),
            viejaClave.getEstudiante().getId(),
            viejaClave.getAnio(),
            viejaClave.getSemestre()
        );
        if (ok) {
            int idx = listado.indexOf(viejaClave);
            if (idx >= 0) listado.set(idx, nueva);
        }
        return ok;
    }

    public boolean guardarInformacion(Inscripcion i) {
        return inscribirCurso(i);
    }

    // ===== Implementación de Servicios =====
    @Override
    public String imprimirPosicion(int posicion) {
        if (posicion < 0 || posicion >= listado.size()) return "(fuera de rango)";
        return listado.get(posicion).toString();
    }

    @Override
    public Integer cantidadActual() {
        return listado.size();
    }

    @Override
    public List<String> imprimirListado() {
        List<String> r = new ArrayList<>();
        for (Inscripcion i : listado) r.add(i.toString());
        return r;
    }

    public List<Inscripcion> getListado() { return listado; }
}
