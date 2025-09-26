package com.mycompany.actividad1.model;

import com.mycompany.actividad1.dao.InscripcionDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursosInscritos implements Servicios {

    private final List<Inscripcion> listado = new ArrayList<>();
    private final InscripcionDAO dao = new InscripcionDAO();

    public void cargarDatos() {
        listado.clear();
        try {
            listado.addAll(dao.listar());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean inscribirCurso(Inscripcion i) {
        if (i == null || i.getCurso() == null || i.getEstudiante() == null
                || i.getAnio() == null || i.getSemestre() == null) return false;
        try {
            if (dao.existe(
                i.getCurso().getID().intValue(),
                i.getEstudiante().getId().intValue(),
                i.getAnio(),
                i.getSemestre())) {
            return false;
            }
            boolean ok = dao.insertar(i);
            if (ok) listado.add(i);
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(Inscripcion i) {
        if (i == null || i.getCurso() == null || i.getEstudiante() == null
                || i.getAnio() == null || i.getSemestre() == null) return false;
        try {
           boolean ok = dao.eliminar(
                i.getCurso().getID().intValue(),
                i.getEstudiante().getId().intValue(),
                i.getAnio(),
                i.getSemestre()
            );
            if (ok) listado.remove(i);
            return ok;
        } catch (SQLException e) {//exception never trhown
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Inscripcion nueva, Inscripcion viejaClave) {
        if (nueva == null || viejaClave == null) return false;
        try {
            boolean ok = dao.actualizar(
                nueva,
                viejaClave.getCurso().getID().intValue(),
                viejaClave.getEstudiante().getId().intValue(),
                viejaClave.getAnio(),
                viejaClave.getSemestre()
            );
            if (ok) {
                // reemplazo lógico en memoria
                int idx = listado.indexOf(viejaClave);
                if (idx >= 0) listado.set(idx, nueva);
            }
            return ok;
        } catch (SQLException e) {//esception neve thrown
            e.printStackTrace();
            return false;
        }
    }

    public boolean guardarInformacion(Inscripcion i) { // alias de inscribir + persistir
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

    // Acceso de solo lectura si te hace falta
    public List<Inscripcion> getListado() { return listado; }
}
