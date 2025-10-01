package controller;

import com.mycompany.actividad1.model.Estudiante;
import dto.EstudianteDTO;
import mapper.EstudianteMapper;
import service.EstudianteService;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EstudianteController {
    private final EstudianteService service;
    public EstudianteController(EstudianteService service) { this.service = service; }

    private static Double parseIdObl(String s, String campo){
        if (s==null || s.isBlank()) throw new IllegalArgumentException(campo+" es obligatorio");
        try {
            Double v = Double.valueOf(s.trim());
            if (v < 0) throw new IllegalArgumentException(campo+" debe ser positivo");
            return v;
        } catch (NumberFormatException e){ throw new IllegalArgumentException(campo+" inválido"); }
    }
    private static Double parseIdOpt(String s){
        if (s==null || s.isBlank()) return null;
        return parseIdObl(s, "ID Programa");
    }
    private static boolean empty(String s){ return s==null || s.isBlank(); }

    // ===== CRUD con DTO =====
    public void insertar(EstudianteDTO dto){
        if (empty(dto.getCodigo())) throw new IllegalArgumentException("Código es obligatorio");
        service.registrar(dto.getIdPersona(), dto.getCodigo(),
                dto.getIdPrograma()==null? "" : String.valueOf(dto.getIdPrograma()),
                dto.getNombres()==null? "" : dto.getNombres(),
                dto.getApellidos()==null? "" : dto.getApellidos(),
                dto.getEmail()==null? "" : dto.getEmail());
    }

    public boolean actualizar(EstudianteDTO dto){
        if (empty(dto.getCodigo())) throw new IllegalArgumentException("Código es obligatorio");
        // Recuperar actuales para no perder datos persona si service los requiere
        Estudiante actual = service.buscar(dto.getIdPersona());
        String nom = actual==null? "" : actual.getNombres();
        String ape = actual==null? "" : actual.getApellidos();
        String mail= actual==null? "" : actual.getEmail();
        return service.actualizar(dto.getIdPersona(), dto.getCodigo(),
                dto.getIdPrograma()==null? "" : String.valueOf(dto.getIdPrograma()),
                nom, ape, mail);
    }

    public boolean eliminar(String idPersonaTxt){ return service.eliminar(parseIdObl(idPersonaTxt,"ID Persona")); }

    public EstudianteDTO buscar(String idPersonaTxt){
        Estudiante e = service.buscar(parseIdObl(idPersonaTxt,"ID Persona"));
        return EstudianteMapper.toDTO(e);
    }

    public List<EstudianteDTO> listar(){ return EstudianteMapper.toDTOs(service.listar()); }

    public DefaultTableModel modeloTablaTodos(){
        DefaultTableModel m = new DefaultTableModel(
                new Object[]{"ID Persona","Nombres","Apellidos","Email","Código","Programa"}, 0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        for (EstudianteDTO e : listar()){
            m.addRow(new Object[]{
                e.getIdPersona(),
                e.getNombres(),
                e.getApellidos(),
                e.getEmail(),
                e.getCodigo(),
                e.getProgramaNombre()==null? "" : e.getProgramaNombre()
            });
        }
        return m;
    }

    // útil para la búsqueda en UI
    public DefaultTableModel modeloTablaDe(EstudianteDTO e){
        DefaultTableModel m = new DefaultTableModel(
                new Object[]{"ID Persona","Nombres","Apellidos","Email","Código","Programa"}, 0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        if (e!=null){
            m.addRow(new Object[]{
                e.getIdPersona(),
                e.getNombres(),
                e.getApellidos(),
                e.getEmail(),
                e.getCodigo(),
                e.getProgramaNombre()==null? "" : e.getProgramaNombre()
            });
        }
        return m;
    }
}
