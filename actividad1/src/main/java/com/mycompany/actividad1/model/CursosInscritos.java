package model;

import java.util.ArrayList;
import java.util.List;

public class CursosInscritos {
    private List<Inscripcion> inscripciones;

    public CursosInscritos() {
        inscripciones = new ArrayList<>();
    }

    public void inscribirCurso(Inscripcion inscripcion){
        inscripciones.add(inscripcion);
        System.out.println("Curso inscrito: " + inscripcion.getCurso().getNombre());
    }

    public void eliminar(Inscripcion inscripcion){
        inscripciones.remove(inscripcion);
        System.out.println("Curso eliminado: " + inscripcion.getCurso().getNombre());
    }

    public void actualizar(Inscripcion inscripcion){
        inscripciones.set(inscripciones.indexOf(inscripcion), inscripcion);
        System.out.println("Curso actualizado: " + inscripcion.getCurso().getNombre());
    }

    public void guardarInformacion(Inscripcion inscripcion){
        //guardar en la BD
    }

    public String toString() {
        return "CursosInscritos{" +
                "inscripciones=" + inscripciones +
                '}';
    }

    public void cargarDatos(){
        //no se que hace esta función
    }

}
