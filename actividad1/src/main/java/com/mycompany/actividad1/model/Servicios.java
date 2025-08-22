package model;

import java.util.ArrayList;
import java.util.List;


public interface Servicios {
    public String imprimirPosicion(int posicion);
    public Integer cantidadActual();
    public List<String> imprimirListado();
}

class CursosInscritos implements Servicios {
    private List<String> cursos = new ArrayList<>();

    @Override
    public String imprimirPosicion(int posicion) {
        if (posicion >= 0 && posicion < cursos.size()) {
            return cursos.get(posicion);
        }
        return "Posición inválida";
    }

    @Override
    public Integer cantidadActual() {
        return cursos.size();
    }

    @Override
    public List<String> imprimirListado() {
        return cursos;
    }

}

class CursosProfesores implements Servicios {
    private List<String> profesores = new ArrayList<>();

    @Override
    public String imprimirPosicion(int posicion) {
        if (posicion >= 0 && posicion < profesores.size()) {
            return profesores.get(posicion);
        }
        return "Posición inválida";
    }

    @Override
    public Integer cantidadActual() {
        return profesores.size();
    }

    @Override
    public List<String> imprimirListado() {
        return profesores;
    }

}