package Observer;

import com.mycompany.actividad1.model.Curso;

public class ConsoleCursoObserver implements CursoObserver {
    @Override
    public void onCursoCreado(Curso curso) {
        String prog = "(sin programa)";
        if (curso.getPrograma() != null) {
            prog = curso.getPrograma().getNombre() != null && !curso.getPrograma().getNombre().isBlank()
                   ? curso.getPrograma().getNombre()
                   : String.valueOf(curso.getPrograma().getId());
        }
        System.out.println("=== Observer: Curso creado ===");
        System.out.println("ID     : " + curso.getID());
        System.out.println("Nombre : " + curso.getNombre());
        System.out.println("Programa: " + prog);
        System.out.println("Activo : " + (Boolean.TRUE.equals(curso.getActivo()) ? "Sí" : "No"));
    }
}
