package com.mycompany.actividad1;

import controller.PersonaController;
import controller.ProfesorController;
import controller.EstudianteController;
import controller.FacultadController;
import controller.ProgramaController;
import controller.CursoController;
import controller.InscripcionController;
import com.mycompany.actividad1.model.Persona;
import repository.PersonaRepository;
import com.mycompany.actividad1.dao.PersonaJdbcRepository;



import ui.Pantalla;

import javax.swing.*;
import java.awt.GraphicsEnvironment;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import service.PersonaService;

public class Main {


    private static final PersonaController personaController;
    private static final ProfesorController profesorController = new ProfesorController();
    private static final EstudianteController estudianteController = new EstudianteController();
    private static final FacultadController facultadController = new FacultadController();
    private static final ProgramaController programaController = new ProgramaController();
    private static final CursoController cursoController = new CursoController();
    private static final InscripcionController inscripcionController = new InscripcionController();
    static {
    PersonaRepository repo = new PersonaJdbcRepository();
    PersonaService service = new PersonaService(repo);
    personaController = new PersonaController(service);
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            String a0 = args[0].toLowerCase(Locale.ROOT);
            if ("--console".equals(a0)) { runConsoleMenus(); return; }
            if ("--gui".equals(a0))     { launchGUI();       return; }
        }

        if (System.console() != null) {
            askModeInConsole();
        } else if (!GraphicsEnvironment.isHeadless()) {
            askModeInDialog();
        } else {
            runConsoleMenus();
        }
        
    }

    // ========== GUI ==========
    private static void launchGUI() {
        SwingUtilities.invokeLater(() -> {
            Pantalla pantalla = new Pantalla();
            pantalla.setLocationRelativeTo(null);
            pantalla.setVisible(true);
        });
    }

    private static void askModeInConsole() {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Cómo deseas iniciar la aplicación?");
        System.out.println("[1] Interfaz gráfica");
        System.out.println("[2] Consola (menú de clases)");
        String opt = ask(sc, "Selecciona 1 o 2: ").trim();
        if ("2".equals(opt)) runConsoleMenus();
        else launchGUI();
    }

    private static void askModeInDialog() {
        Object[] options = {"Interfaz", "Consola"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "¿Cómo deseas iniciar la aplicación?",
                "Arranque",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice == 1) runConsoleMenus();
        else launchGUI();
    }

    // ========== MENÚS EN CONSOLA ==========
    private static void runConsoleMenus() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL (Consola) ===");
            System.out.println("[1] Persona");
            System.out.println("[2] Profesor");
            System.out.println("[3] Estudiante");
            System.out.println("[4] Facultad");
            System.out.println("[5] Programa");
            System.out.println("[6] Curso");
            System.out.println("[7] Inscripción");
            System.out.println("[8] Abrir Interfaz Gráfica");
            System.out.println("[0] Cerrar programa");
            String op = ask(sc, "Opción: ");

            switch (op) {
                case "1": submenuPersona(sc); break;
                case "2": submenuProfesor(sc); break;
                case "3": submenuEstudiante(sc); break;
                case "4": submenuFacultad(sc); break;
                case "5": submenuPrograma(sc); break;
                case "6": submenuCurso(sc); break;
                case "7": submenuInscripcion(sc); break;
                case "8": launchGUI(); return;
                case "0": System.out.println("¡Hasta luego!"); return;
                default:  System.out.println("Opción inválida.");
            }
        }
    }

    private static void submenuNoImplementado(Scanner sc, String modulo) {
        while (true) {
            System.out.println("\n=== " + modulo + " ===");
            System.out.println("[1] Crear");
            System.out.println("[2] Buscar");
            System.out.println("[3] Editar");
            System.out.println("[4] Eliminar");
            System.out.println("[5] Volver");
            System.out.println("[0] Cerrar programa");
            String op = ask(sc, "Opción: ");
            if ("5".equals(op)) return;
            if ("0".equals(op)) { System.out.println("¡Hasta luego!"); System.exit(0); }
            System.out.println("→ " + modulo + " por consola aún no implementado. Usa la interfaz o completa el controller/DAO.");
        }
    }

    // ===== Persona (FUNCIONAL) =====
    private static void submenuPersona(Scanner sc) {
        while (true) {
            System.out.println("\n=== Persona ===");
            System.out.println("[1] Crear");
            System.out.println("[2] Buscar");
            System.out.println("[3] Editar");
            System.out.println("[4] Eliminar");
            System.out.println("[5] Volver");
            System.out.println("[0] Cerrar programa");
            String op = ask(sc, "Opción: ");
            switch (op) {
                case "1": personaCrear(sc);   break;
                case "2": personaBuscar(sc);  break;
                case "3": personaEditar(sc);  break;
                case "4": personaEliminar(sc);break;
                case "5": return;
                case "0": System.out.println("¡Hasta luego!"); System.exit(0);
                default:  System.out.println("Opción inválida.");
            }
        }
    }

    // ========== Persona: acciones ==========
    private static void personaCrear(Scanner sc) {
        System.out.println("\n--- Crear Persona ---");
        try {
            String id = askNumericDouble(sc, "ID (entero): ");
            String nombres   = askNonEmpty(sc, "Nombres: ");
            String apellidos = askNonEmpty(sc, "Apellidos: ");
            String email     = askEmail(sc, "Email: ");

            personaController.insertar(id, nombres, apellidos, email);
            System.out.println("✔ Persona creada con éxito.");

            listarPersonas();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void personaBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Persona ---");
        try {
            String id = askNumericDouble(sc, "ID (entero): ");
            Persona p = personaController.buscar(id);
            if (p == null) {
                System.out.println("No se encontró persona con ID " + id);
            } else {
                System.out.println("Resultado: " + p);
            }
            // Mantener tabla “completa” equivalente: listamos todo
            listarPersonas();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void personaEditar(Scanner sc) {
        System.out.println("\n--- Editar Persona ---");
        try {
            String id = askNumeric(sc, "ID (entero) a editar: ");
            Persona actual = personaController.buscar(id);
            if (actual == null) {
                System.out.println("No existe persona con ID " + id);
                return;
            }
            System.out.println("Actual: " + actual);

            String nombres   = askDefault(sc, "Nombres [" + actual.getNombres() + "]: ", actual.getNombres());
            String apellidos = askDefault(sc, "Apellidos [" + actual.getApellidos() + "]: ", actual.getApellidos());
            String email     = askDefaultEmail(sc, "Email [" + actual.getEmail() + "]: ", actual.getEmail());

            boolean ok = personaController.actualizar(id, nombres, apellidos, email);
            System.out.println(ok ? "✔ Persona actualizada." : "× No se pudo actualizar.");

            listarPersonas();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void personaEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Persona ---");
        try {
            String id = askNumeric(sc, "ID (entero) a eliminar: ");
            Persona p = personaController.buscar(id);
            if (p == null) {
                System.out.println("No existe persona con ID " + id);
                return;
            }
            System.out.println("Se eliminará: " + p);
            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) {
                System.out.println("Operación cancelada.");
                return;
            }
            boolean ok = personaController.eliminar(id);
            System.out.println(ok ? "✔ Persona eliminada." : "× No se pudo eliminar.");
            listarPersonas();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void listarPersonas() {
        try {
            List<Persona> lista = personaController.listar();
            System.out.println("\n=== Personas en BD ===");
            if (lista == null || lista.isEmpty()) {
                System.out.println("(sin registros)");
                return;
            }
            for (Persona p : lista) System.out.println(" - " + p);
        } catch (Exception e) {
            System.out.println("× Error listando personas: " + e.getMessage());
        }
    }

    // ========== Profesor (CONSOLA) ==========
    private static void submenuProfesor(Scanner sc) {
        while (true) {
            System.out.println("\n=== Profesor ===");
            System.out.println("[1] Crear");
            System.out.println("[2] Buscar");
            System.out.println("[3] Editar");
            System.out.println("[4] Eliminar");
            System.out.println("[5] Listar todos");
            System.out.println("[6] Volver");
            System.out.println("[0] Cerrar programa");
            String op = ask(sc, "Opción: ");
            switch (op) {
                case "1": profesorCrear(sc);   break;
                case "2": profesorBuscar(sc);  break;
                case "3": profesorEditar(sc);  break;
                case "4": profesorEliminar(sc);break;
                case "5": profesorListar();    break;
                case "6": return;
                case "0": System.out.println("¡Hasta luego!"); System.exit(0);
                default:  System.out.println("Opción inválida.");
            }
        }
    }

    private static void profesorCrear(Scanner sc) {
        System.out.println("\n--- Crear Profesor ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero): ");
            String contrato  = askNonEmpty(sc, "Contrato (p.ej. Cátedra/Tiempo completo): ");
            profesorController.insertar(idPersona, contrato);
            System.out.println("✔ Profesor creado.");
            profesorListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void profesorBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Profesor ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero): ");
            com.mycompany.actividad1.model.Profesor p = profesorController.buscar(idPersona);
            if (p == null) System.out.println("No existe profesor con ID persona " + idPersona);
            else System.out.println("Resultado: ID Persona=" + p.getIdPersona() + ", Contrato=" + p.getContrato());
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void profesorEditar(Scanner sc) {
        System.out.println("\n--- Editar Profesor ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero) a editar: ");
            com.mycompany.actividad1.model.Profesor actual = profesorController.buscar(idPersona);
            if (actual == null) { System.out.println("No existe profesor con ID persona " + idPersona); return; }
            String contrato = askDefault(sc, "Contrato [" + actual.getContrato() + "]: ", actual.getContrato());
            boolean ok = profesorController.actualizar(idPersona, contrato);
            System.out.println(ok ? "✔ Profesor actualizado." : "× No se pudo actualizar.");
            profesorListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void profesorEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Profesor ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero) a eliminar: ");
            com.mycompany.actividad1.model.Profesor actual = profesorController.buscar(idPersona);
            if (actual == null) { System.out.println("No existe profesor con ID persona " + idPersona); return; }
            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { System.out.println("Cancelado."); return; }
            boolean ok = profesorController.eliminar(idPersona);
            System.out.println(ok ? "✔ Profesor eliminado." : "× No se pudo eliminar.");
            profesorListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void profesorListar() {
        try {
            javax.swing.table.DefaultTableModel modelo = profesorController.modeloTablaTodos();
            System.out.println("\n=== Profesores en BD ===");
            if (modelo.getRowCount() == 0) { System.out.println("(sin registros)"); return; }
            for (int i = 0; i < modelo.getRowCount(); i++) {
                System.out.println(" - ID Persona=" + modelo.getValueAt(i,0) + ", Contrato=" + modelo.getValueAt(i,1));
            }
        } catch (Exception e) {
            System.out.println("× Error listando profesores: " + e.getMessage());
        }
    }

    // ========== Estudiante (CONSOLA) ==========
    private static void submenuEstudiante(Scanner sc) {
        while (true) {
            System.out.println("\n=== Estudiante ===");
            System.out.println("[1] Crear");
            System.out.println("[2] Buscar");
            System.out.println("[3] Editar");
            System.out.println("[4] Eliminar");
            System.out.println("[5] Listar todos");
            System.out.println("[6] Volver");
            System.out.println("[0] Cerrar programa");
            String op = ask(sc, "Opción: ");
            switch (op) {
                case "1": estudianteCrear(sc);   break;
                case "2": estudianteBuscar(sc);  break;
                case "3": estudianteEditar(sc);  break;
                case "4": estudianteEliminar(sc);break;
                case "5": estudianteListar();    break;
                case "6": return;
                case "0": System.out.println("¡Hasta luego!"); System.exit(0);
                default:  System.out.println("Opción inválida.");
            }
        }
    }

    private static void estudianteCrear(Scanner sc) {
        System.out.println("\n--- Crear Estudiante ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero): ");
            String codigo    = askNonEmpty(sc, "Código: ");
            String idPrograma = ask(sc, "ID Programa (opcional, Enter para dejar vacío): ").trim();
            boolean ok = estudianteController.insertar(idPersona, codigo, idPrograma);
            System.out.println(ok ? "✔ Estudiante creado." : "× No se pudo crear.");
            estudianteListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void estudianteBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Estudiante ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero): ");
            com.mycompany.actividad1.model.Estudiante e = estudianteController.buscar(idPersona);
            if (e == null) System.out.println("No existe estudiante con ID persona " + idPersona);
            else System.out.println("Resultado: ID Persona=" + e.getId() + ", Código=" + e.getCodigo() +
                                    ", Programa=" + (e.getPrograma()==null? "" : e.getPrograma().getNombre()));
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void estudianteEditar(Scanner sc) {
        System.out.println("\n--- Editar Estudiante ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero) a editar: ");
            com.mycompany.actividad1.model.Estudiante actual = estudianteController.buscar(idPersona);
            if (actual == null) { System.out.println("No existe estudiante con ID persona " + idPersona); return; }
            String codigo = askDefault(sc, "Código [" + actual.getCodigo() + "]: ", actual.getCodigo());
            String idPrograma = ask(sc, "ID Programa (opcional, Enter para dejar vacío" +
                                         (actual.getPrograma()!=null? ", actual="+actual.getPrograma().getId() : "") +
                                         "): ").trim();
            boolean ok = estudianteController.actualizar(idPersona, codigo, idPrograma);
            System.out.println(ok ? "✔ Estudiante actualizado." : "× No se pudo actualizar.");
            estudianteListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void estudianteEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Estudiante ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero) a eliminar: ");
            com.mycompany.actividad1.model.Estudiante actual = estudianteController.buscar(idPersona);
            if (actual == null) { System.out.println("No existe estudiante con ID persona " + idPersona); return; }
            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { System.out.println("Cancelado."); return; }
            boolean ok = estudianteController.eliminar(idPersona);
            System.out.println(ok ? "✔ Estudiante eliminado." : "× No se pudo eliminar.");
            estudianteListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void estudianteListar() {
        try {
            java.util.List<?> lista = estudianteController.listar();
            System.out.println("\n=== Estudiantes en BD ===");
            if (lista == null || lista.isEmpty()) { System.out.println("(sin registros)"); return; }
            for (Object o : lista) {
                com.mycompany.actividad1.model.Estudiante e = (com.mycompany.actividad1.model.Estudiante) o;
                System.out.println(" - ID Persona=" + e.getId()
                    + ", Código=" + e.getCodigo()
                    + ", Programa=" + (e.getPrograma()==null? "" : e.getPrograma().getNombre()));
            }
        } catch (Exception e) {
            System.out.println("× Error listando estudiantes: " + e.getMessage());
        }
    }

    // ========== Facultad (CONSOLA) ==========
    private static void submenuFacultad(Scanner sc) {
        while (true) {
            System.out.println("\n=== Facultad ===");
            System.out.println("[1] Crear");
            System.out.println("[2] Buscar");
            System.out.println("[3] Editar");
            System.out.println("[4] Eliminar");
            System.out.println("[5] Listar todos");
            System.out.println("[6] Volver");
            System.out.println("[0] Cerrar programa");
            String op = ask(sc, "Opción: ");
            switch (op) {
                case "1": facultadCrear(sc);   break;
                case "2": facultadBuscar(sc);  break;
                case "3": facultadEditar(sc);  break;
                case "4": facultadEliminar(sc);break;
                case "5": facultadListar();    break;
                case "6": return;
                case "0": System.out.println("¡Hasta luego!"); System.exit(0);
                default:  System.out.println("Opción inválida.");
            }
        }
    }

        private static void facultadCrear(Scanner sc) {
            System.out.println("\n--- Crear Facultad ---");
            try {
                String id = askNumericDouble(sc, "ID (entero): ");
                String nombre = askNonEmpty(sc, "Nombre: ");
                String decanoId = askNumeric(sc, "ID Decano (entero): ");

                // ANTES: boolean ok = facultadController.insertar(...);
                facultadController.insertar(id, nombre, decanoId); // void

                System.out.println("✔ Facultad creada.");
                facultadListar();
            } catch (Exception e) {
                System.out.println("× Error: " + e.getMessage());
            }
        }


    private static void facultadBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Facultad ---");
        try {
            String id = askNumericDouble(sc, "ID (entero): ");
            com.mycompany.actividad1.model.Facultad f = facultadController.buscar(id);
            if (f == null) System.out.println("No existe facultad con ID " + id);
            else System.out.println("Resultado: ID=" + f.getID() + ", Nombre=" + f.getNombre() + 
                                    ", Decano=" + (f.getDecano()==null? "" : f.getDecano().getNombres() + " " + f.getDecano().getApellidos()));
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void facultadEditar(Scanner sc) {
        System.out.println("\n--- Editar Facultad ---");
        try {
            String id = askNumeric(sc, "ID (entero) a editar: ");
            com.mycompany.actividad1.model.Facultad actual = facultadController.buscar(id);
            if (actual == null) { System.out.println("No existe facultad con ID " + id); return; }
            String nombre = askDefault(sc, "Nombre [" + actual.getNombre() + "]: ", actual.getNombre());
            String decanoId = askDefault(sc, "ID Decano [" + (actual.getDecano()==null? "" : actual.getDecano().getId()) + "]: ", 
                                        actual.getDecano()==null? "" : actual.getDecano().getId().toString());
            boolean ok = facultadController.actualizar(id, nombre, decanoId);
            System.out.println(ok ? "✔ Facultad actualizada." : "× No se pudo actualizar.");
            facultadListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void facultadEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Facultad ---");
        try {
            String id = askNumeric(sc, "ID (entero) a eliminar: ");
            com.mycompany.actividad1.model.Facultad actual = facultadController.buscar(id);
            if (actual == null) { System.out.println("No existe facultad con ID " + id); return; }
            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { System.out.println("Cancelado."); return; }
            boolean ok = facultadController.eliminar(id);
            System.out.println(ok ? "✔ Facultad eliminada." : "× No se pudo eliminar.");
            facultadListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void facultadListar() {
        try {
            javax.swing.table.DefaultTableModel modelo = facultadController.modeloTablaTodas();
            System.out.println("\n=== Facultades en BD ===");
            if (modelo.getRowCount() == 0) { System.out.println("(sin registros)"); return; }
            for (int i = 0; i < modelo.getRowCount(); i++) {
                System.out.println(" - ID=" + modelo.getValueAt(i,0) + ", Nombre=" + modelo.getValueAt(i,1) + 
                                  ", Decano=" + modelo.getValueAt(i,3));
            }
        } catch (Exception e) {
            System.out.println("× Error listando facultades: " + e.getMessage());
        }
    }

    // ========== Programa (CONSOLA) ==========
    private static void submenuPrograma(Scanner sc) {
        while (true) {
            System.out.println("\n=== Programa ===");
            System.out.println("[1] Crear");
            System.out.println("[2] Buscar");
            System.out.println("[3] Editar");
            System.out.println("[4] Eliminar");
            System.out.println("[5] Listar todos");
            System.out.println("[6] Volver");
            System.out.println("[0] Cerrar programa");
            String op = ask(sc, "Opción: ");
            switch (op) {
                case "1": programaCrear(sc);   break;
                case "2": programaBuscar(sc);  break;
                case "3": programaEditar(sc);  break;
                case "4": programaEliminar(sc);break;
                case "5": programaListar();    break;
                case "6": return;
                case "0": System.out.println("¡Hasta luego!"); System.exit(0);
                default:  System.out.println("Opción inválida.");
            }
        }
    }

        private static void programaCrear(Scanner sc) {
            System.out.println("\n--- Crear Programa ---");
            try {
                String id = askNumericDouble(sc, "ID (entero): ");
                String nombre = askNonEmpty(sc, "Nombre: ");
                String duracion = askNumeric(sc, "Duración (años): ");
                String registro = ask(sc, "Fecha registro (YYYY-MM-DD): ").trim();
                String idFacultad = askNumeric(sc, "ID Facultad (entero): ");

                // ANTES: boolean ok = programaController.insertar(...);
                programaController.insertar(id, nombre, duracion, registro, idFacultad); // void

                System.out.println("✔ Programa creado.");
                programaListar();
            } catch (Exception e) {
                System.out.println("× Error: " + e.getMessage());
            }
        }


    private static void programaBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Programa ---");
        try {
            String id = askNumericDouble(sc, "ID (entero): ");
            com.mycompany.actividad1.model.Programa p = programaController.buscar(id);
            if (p == null) System.out.println("No existe programa con ID " + id);
            else System.out.println("Resultado: ID=" + p.getId() + ", Nombre=" + p.getNombre() + 
                                    ", Duración=" + p.getDuracion() + ", Facultad=" + (p.getFacultad()==null? "" : p.getFacultad().getNombre()));
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void programaEditar(Scanner sc) {
        System.out.println("\n--- Editar Programa ---");
        try {
            String id = askNumeric(sc, "ID (entero) a editar: ");
            com.mycompany.actividad1.model.Programa actual = programaController.buscar(id);
            if (actual == null) { System.out.println("No existe programa con ID " + id); return; }
            String nombre = askDefault(sc, "Nombre [" + actual.getNombre() + "]: ", actual.getNombre());
            String duracion = askDefault(sc, "Duración [" + actual.getDuracion() + "]: ", String.valueOf(actual.getDuracion()));
            String registro = askDefault(sc, "Fecha registro [" + actual.getRegistro() + "]: ", actual.getRegistro().toString());
            String idFacultad = askDefault(sc, "ID Facultad [" + (actual.getFacultad()==null? "" : actual.getFacultad().getID()) + "]: ", 
                                          actual.getFacultad()==null? "" : actual.getFacultad().getID().toString());
            boolean ok = programaController.actualizar(id, nombre, duracion, registro, idFacultad);
            System.out.println(ok ? "✔ Programa actualizado." : "× No se pudo actualizar.");
            programaListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void programaEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Programa ---");
        try {
            String id = askNumeric(sc, "ID (entero) a eliminar: ");
            com.mycompany.actividad1.model.Programa actual = programaController.buscar(id);
            if (actual == null) { System.out.println("No existe programa con ID " + id); return; }
            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { System.out.println("Cancelado."); return; }
            boolean ok = programaController.eliminar(id);
            System.out.println(ok ? "✔ Programa eliminado." : "× No se pudo eliminar.");
            programaListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void programaListar() {
        try {
            javax.swing.table.DefaultTableModel modelo = programaController.modeloTablaTodos();
            System.out.println("\n=== Programas en BD ===");
            if (modelo.getRowCount() == 0) { System.out.println("(sin registros)"); return; }
            for (int i = 0; i < modelo.getRowCount(); i++) {
                System.out.println(" - ID=" + modelo.getValueAt(i,0) + ", Nombre=" + modelo.getValueAt(i,1) + 
                                  ", Duración=" + modelo.getValueAt(i,2) + ", Facultad=" + modelo.getValueAt(i,4));
            }
        } catch (Exception e) {
            System.out.println("× Error listando programas: " + e.getMessage());
        }
    }

    // ========== Curso (CONSOLA) ==========
    private static void submenuCurso(Scanner sc) {
        while (true) {
            System.out.println("\n=== Curso ===");
            System.out.println("[1] Crear");
            System.out.println("[2] Buscar");
            System.out.println("[3] Editar");
            System.out.println("[4] Eliminar");
            System.out.println("[5] Listar todos");
            System.out.println("[6] Volver");
            System.out.println("[0] Cerrar programa");
            String op = ask(sc, "Opción: ");
            switch (op) {
                case "1": cursoCrear(sc);   break;
                case "2": cursoBuscar(sc);  break;
                case "3": cursoEditar(sc);  break;
                case "4": cursoEliminar(sc);break;
                case "5": cursoListar();    break;
                case "6": return;
                case "0": System.out.println("¡Hasta luego!"); System.exit(0);
                default:  System.out.println("Opción inválida.");
            }
        }
    }

        private static void cursoCrear(Scanner sc) {
            System.out.println("\n--- Crear Curso ---");
            try {
                String id = askNumericDouble(sc, "ID (entero): ");
                String nombre = askNonEmpty(sc, "Nombre: ");
                String idPrograma = ask(sc, "ID Programa (opcional, Enter para dejar vacío): ").trim();
                String activo = ask(sc, "¿Activo? (S/N): ").trim().toLowerCase(Locale.ROOT);
                boolean activoBool = activo.equals("s") || activo.equals("si") || activo.equals("sí");

                // ANTES: boolean ok = cursoController.insertar(...);
                cursoController.insertar(id, nombre, idPrograma, activoBool); // insertar es void

                System.out.println("✔ Curso creado.");
                cursoListar();
            } catch (Exception e) {
                System.out.println("× Error: " + e.getMessage());
            }
        }


    private static void cursoBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Curso ---");
        try {
            String id = askNumericDouble(sc, "ID (entero): ");
            com.mycompany.actividad1.model.Curso c = cursoController.buscar(id);
            if (c == null) System.out.println("No existe curso con ID " + id);
            else System.out.println("Resultado: ID=" + c.getID() + ", Nombre=" + c.getNombre() + 
                                    ", Programa=" + (c.getPrograma()==null? "" : c.getPrograma().getId()) + 
                                    ", Activo=" + c.getActivo());
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void cursoEditar(Scanner sc) {
        System.out.println("\n--- Editar Curso ---");
        try {
            String id = askNumeric(sc, "ID (entero) a editar: ");
            com.mycompany.actividad1.model.Curso actual = cursoController.buscar(id);
            if (actual == null) { System.out.println("No existe curso con ID " + id); return; }
            String nombre = askDefault(sc, "Nombre [" + actual.getNombre() + "]: ", actual.getNombre());
            String idPrograma = ask(sc, "ID Programa (opcional, Enter para dejar vacío" +
                                     (actual.getPrograma()!=null? ", actual="+actual.getPrograma().getId() : "") +
                                     "): ").trim();
            String activo = ask(sc, "¿Activo? (S/N) [" + (actual.getActivo()? "S" : "N") + "]: ").trim().toLowerCase(Locale.ROOT);
            boolean activoBool = activo.isEmpty() ? actual.getActivo() : (activo.equals("s") || activo.equals("si") || activo.equals("sí"));
            boolean ok = cursoController.actualizar(id, nombre, idPrograma, activoBool);
            System.out.println(ok ? "✔ Curso actualizado." : "× No se pudo actualizar.");
            cursoListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void cursoEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Curso ---");
        try {
            String id = askNumeric(sc, "ID (entero) a eliminar: ");
            com.mycompany.actividad1.model.Curso actual = cursoController.buscar(id);
            if (actual == null) { System.out.println("No existe curso con ID " + id); return; }
            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { System.out.println("Cancelado."); return; }
            boolean ok = cursoController.eliminar(id);
            System.out.println(ok ? "✔ Curso eliminado." : "× No se pudo eliminar.");
            cursoListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void cursoListar() {
        try {
            javax.swing.table.DefaultTableModel modelo = cursoController.modeloTablaTodos();
            System.out.println("\n=== Cursos en BD ===");
            if (modelo.getRowCount() == 0) { System.out.println("(sin registros)"); return; }
            for (int i = 0; i < modelo.getRowCount(); i++) {
                System.out.println(" - ID=" + modelo.getValueAt(i,0) + ", Nombre=" + modelo.getValueAt(i,1) + 
                                  ", Programa=" + modelo.getValueAt(i,2) + ", Activo=" + modelo.getValueAt(i,3));
            }
        } catch (Exception e) {
            System.out.println("× Error listando cursos: " + e.getMessage());
        }
    }

    // ========== Inscripción (CONSOLA) ==========
    private static void submenuInscripcion(Scanner sc) {
        while (true) {
            System.out.println("\n=== Inscripción ===");
            System.out.println("[1] Crear");
            System.out.println("[2] Buscar");
            System.out.println("[3] Editar");
            System.out.println("[4] Eliminar");
            System.out.println("[5] Listar todos");
            System.out.println("[6] Volver");
            System.out.println("[0] Cerrar programa");
            String op = ask(sc, "Opción: ");
            switch (op) {
                case "1": inscripcionCrear(sc);   break;
                case "2": inscripcionBuscar(sc);  break;
                case "3": inscripcionEditar(sc);  break;
                case "4": inscripcionEliminar(sc);break;
                case "5": inscripcionListar();    break;
                case "6": return;
                case "0": System.out.println("¡Hasta luego!"); System.exit(0);
                default:  System.out.println("Opción inválida.");
            }
        }
    }

    private static void inscripcionCrear(Scanner sc) {
        System.out.println("\n--- Crear Inscripción ---");
        try {
            String cursoId = askNumeric(sc, "ID Curso (entero): ");
            String estudianteId = askNumeric(sc, "ID Estudiante (entero): ");
            String anio = askNumeric(sc, "Año: ");
            String semestre = askNumeric(sc, "Semestre (1 o 2): ");
            boolean ok = inscripcionController.insertar(cursoId, estudianteId, anio, semestre);
            System.out.println(ok ? "✔ Inscripción creada." : "× No se pudo crear.");
            inscripcionListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void inscripcionBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Inscripción ---");
        try {
            String cursoId = askNumeric(sc, "ID Curso (entero): ");
            String estudianteId = askNumeric(sc, "ID Estudiante (entero): ");
            String anio = askNumeric(sc, "Año: ");
            String semestre = askNumeric(sc, "Semestre (1 o 2): ");
            com.mycompany.actividad1.model.Inscripcion i = inscripcionController.buscar(cursoId, estudianteId, anio, semestre);
            if (i == null) System.out.println("No existe inscripción con esos datos");
            else System.out.println("Resultado: Curso=" + (i.getCurso()==null? "" : i.getCurso().getNombre()) + 
                                    ", Estudiante=" + (i.getEstudiante()==null? "" : i.getEstudiante().getNombres() + " " + i.getEstudiante().getApellidos()) +
                                    ", Año=" + i.getAnio() + ", Semestre=" + i.getSemestre());
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void inscripcionEditar(Scanner sc) {
        System.out.println("\n--- Editar Inscripción ---");
        try {
            System.out.println("Datos actuales:");
            String cursoIdOld = askNumeric(sc, "ID Curso actual: ");
            String estudianteIdOld = askNumeric(sc, "ID Estudiante actual: ");
            String anioOld = askNumeric(sc, "Año actual: ");
            String semestreOld = askNumeric(sc, "Semestre actual: ");
            
            System.out.println("Nuevos datos:");
            String cursoIdNew = askNumeric(sc, "ID Curso nuevo: ");
            String estudianteIdNew = askNumeric(sc, "ID Estudiante nuevo: ");
            String anioNew = askNumeric(sc, "Año nuevo: ");
            String semestreNew = askNumeric(sc, "Semestre nuevo: ");
            
            boolean ok = inscripcionController.actualizar(cursoIdOld, estudianteIdOld, anioOld, semestreOld,
                                                         cursoIdNew, estudianteIdNew, anioNew, semestreNew);
            System.out.println(ok ? "✔ Inscripción actualizada." : "× No se pudo actualizar.");
            inscripcionListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void inscripcionEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Inscripción ---");
        try {
            String cursoId = askNumeric(sc, "ID Curso (entero): ");
            String estudianteId = askNumeric(sc, "ID Estudiante (entero): ");
            String anio = askNumeric(sc, "Año: ");
            String semestre = askNumeric(sc, "Semestre (1 o 2): ");
            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { System.out.println("Cancelado."); return; }
            boolean ok = inscripcionController.eliminar(cursoId, estudianteId, anio, semestre);
            System.out.println(ok ? "✔ Inscripción eliminada." : "× No se pudo eliminar.");
            inscripcionListar();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }

    private static void inscripcionListar() {
        try {
            javax.swing.table.DefaultTableModel modelo = inscripcionController.modeloTablaTodos();
            System.out.println("\n=== Inscripciones en BD ===");
            if (modelo.getRowCount() == 0) { System.out.println("(sin registros)"); return; }
            for (int i = 0; i < modelo.getRowCount(); i++) {
                System.out.println(" - Curso=" + modelo.getValueAt(i,1) + ", Estudiante=" + modelo.getValueAt(i,3) + 
                                  ", Año=" + modelo.getValueAt(i,4) + ", Semestre=" + modelo.getValueAt(i,5));
            }
        } catch (Exception e) {
            System.out.println("× Error listando inscripciones: " + e.getMessage());
        }
    }

    // ========== Helpers de entrada ==========
    private static String ask(Scanner sc, String prompt) {
        System.out.print(prompt);
        String s = sc.nextLine();
        return s == null ? "" : s.trim();
    }

    private static String askNonEmpty(Scanner sc, String prompt) {
        String s = ask(sc, prompt);
        while (s.isEmpty()) {
            System.out.println("  -> Campo requerido.");
            s = ask(sc, prompt);
        }
        return s;
    }

    private static String askEmail(Scanner sc, String prompt) {
        String s = ask(sc, prompt);
        while (s.isEmpty() || !s.contains("@")) {
            System.out.println("  -> Email inválido.");
            s = ask(sc, prompt);
        }
        return s;
    }

    private static String askDefault(Scanner sc, String prompt, String def) {
        String s = ask(sc, prompt);
        return s.isEmpty() ? def : s;
    }

    private static String askDefaultEmail(Scanner sc, String prompt, String def) {
        String s = ask(sc, prompt);
        if (s.isEmpty()) return def;
        while (!s.contains("@")) {
            System.out.println("  -> Email inválido.");
            s = ask(sc, prompt);
            if (s.isEmpty()) return def;
        }
        return s;
    }

    private static String askNumeric(Scanner sc, String prompt) {
        String s = ask(sc, prompt);
        while (!s.matches("\\d+")) {
            System.out.println("  -> Debe ser número entero positivo.");
            s = ask(sc, prompt);
        }
        return s;
    }
    private static String askNumericDouble(Scanner sc, String prompt) {
    String s = ask(sc, prompt);
    while (!s.matches("\\d+(\\.\\d+)?")) {
        System.out.println("  -> Debe ser un número (entero o decimal positivo).");
        s = ask(sc, prompt);
    }
    return s;
}

}
