package com.mycompany.actividad1;

import com.mycompany.actividad1.factory.app.AppFactory;
import controller.*;
import dto.CursoDTO;
import ui.Pantalla;

import javax.swing.*;
import java.awt.GraphicsEnvironment;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import ui.PantallaInscripcion;


public class Main {
    private static volatile ui.Pantalla UI;
    private static volatile ui.PantallaInscripcion UI2;     
    private static final long BOOT_T0 = System.nanoTime();
    
    private static void log(String msg){
        long ms = (System.nanoTime() - BOOT_T0) / 1_000_000;
        System.out.println("[BOOT +" + ms + " ms] " + msg);
    }
    private static final boolean DEBUG = true;
    private static void dbg(String msg){ if (DEBUG) System.out.println("[DBG] " + msg); }


    private static final AppFactory factory = new AppFactory();
    private static final PersonaController personaController       = factory.personaController();
    private static final ProfesorController profesorController     = factory.profesorController();
    private static final EstudianteController estudianteController = factory.estudianteController();
    private static final FacultadController facultadController     = factory.facultadController();
    private static final ProgramaController programaController     = factory.programaController();
    private static final CursoController cursoController           = factory.cursoController();
    private static final CursosInscritosController inscController  = factory.cursosInscritosController();


    public static void main(String[] args) {
        log("Iniciando aplicación… DB activa=" + factory.activeDb());
        log("Hora del servidor: " + factory.domain().databaseAdapter().getServerTime());
          factory.cursoService().addObserver(curso -> {
            final String prog = (curso.getPrograma()!=null)
                ? (curso.getPrograma().getNombre()!=null && !curso.getPrograma().getNombre().isBlank()
                    ? curso.getPrograma().getNombre()
                    : String.valueOf(curso.getPrograma().getId()))
                : "(sin programa)";

            System.out.println("→ Curso registrado: ID=" + curso.getID()
                + ", Nombre=" + curso.getNombre()
                + ", Programa=" + prog
                + ", Activo=" + (Boolean.TRUE.equals(curso.getActivo()) ? "Sí" : "No"));

            javax.swing.SwingUtilities.invokeLater(() -> {
              try {
                if (UI != null) {
                  javax.swing.JOptionPane.showMessageDialog(
                      UI,
                      "Curso registrado:\n" +
                      "ID: " + curso.getID() + "\n" +
                      "Nombre: " + curso.getNombre() + "\n" +
                      "Programa: " + prog + "\n" +
                      "Activo: " + (Boolean.TRUE.equals(curso.getActivo()) ? "Sí" : "No"));
                  UI.recargarTodo();
                }
                if (UI2 != null) UI2.recargarTodo();
              } catch (Exception ex) {
                System.out.println("[OBS] Error refrescando UI: " + ex.getMessage());
              }
            });
          });

            // Hilo consola
            Thread consoleThread = new Thread(() -> {
                try {
                    if (System.console() != null || GraphicsEnvironment.isHeadless()) {
                        log("Consola: iniciando menús…");
                        runConsoleMenus();
                    } else {
                        log("Consola (stdin): iniciando menús…");
                        runConsoleMenus();
                    }
                    log("Consola: finalizada.");
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }, "console-menu");
            consoleThread.start();
            //Hilo GUI
            launchGUI();

        }

            // ========== GUI ==========
        private static void launchGUI() {
            log("Preparando GUI (EDT)...");
            SwingUtilities.invokeLater(() -> {
              try {
                // Instancia única de controladores desde la misma factory
                Pantalla p = new Pantalla(
                    factory.personaController(),
                    factory.profesorController(),
                    factory.facultadController(),
                    factory.programaController(),
                    factory.cursoController(),
                    factory.estudianteController()
                );
                UI = p;
                p.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                p.setLocationRelativeTo(null);
                p.setVisible(true);

                PantallaInscripcion w = new PantallaInscripcion(
                    factory.personaController(),
                    factory.profesorController(),
                    factory.facultadController(),
                    factory.programaController(),
                    factory.cursoController(),
                    factory.estudianteController()
                );
                UI2 = w;
                w.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                w.setLocationRelativeTo(null);
                w.setVisible(true);

                if (UI  != null) UI.recargarTodo();
                if (UI2 != null) UI2.recargarTodo();
              } catch (Throwable t) {
                t.printStackTrace();
              }
            });
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
            System.out.println("[0] Volver / Salir de la consola (la GUI sigue)");
            String op = ask(sc, "Opción: ");

            switch (op) {
                case "1": submenuPersona(sc); break;
                case "2": submenuProfesor(sc); break;
                case "3": submenuEstudiante(sc); break;
                case "4": submenuFacultad(sc); break;
                case "5": submenuPrograma(sc); break;
                case "6": submenuCurso(sc); break;
                case "7": submenuInscripcion(sc); break;
                case "0": return;
                default:  System.out.println("Opción inválida.");
            }
        }
    }

        private static void refreshUIAsync() {
        Pantalla p  = UI;
        PantallaInscripcion p2 = UI2;
        if (p != null || p2 != null) {
            System.out.println("[UI] refreshUIAsync() solicitado");
            SwingUtilities.invokeLater(() -> {
                try {
                    if (p  != null) { System.out.println("[UI] -> Pantalla.recargarTodo()"); p.recargarTodo(); }
                    if (p2 != null) { System.out.println("[UI] -> PantallaInscripcion.recargarTodo()"); p2.recargarTodo(); }
                } catch (Exception ex) {
                    System.out.println("× Error refrescando UI: " + ex.getMessage());
                }
            });
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
                case "0": System.out.println("¡Hasta luego!"); return;
                default:  System.out.println("Opción inválida.");
            }
        }
    }

    // ========== Persona: acciones ==========
    private static void personaCrear(Scanner sc) {
        try {
            String id        = askNumericDouble(sc, "ID (entero): ");
            String nombres   = askNonEmpty(sc, "Nombres: ");
            String apellidos = askNonEmpty(sc, "Apellidos: ");
            String email     = askEmail(sc, "Email: ");

            dto.PersonaDTO dto = new dto.PersonaDTO(Double.valueOf(id), nombres, apellidos, email);
            personaController.insertar(dto);

            listarPersonas();
            refreshUIAsync();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void personaBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Persona ---");
        try {
            String id = askNumericDouble(sc, "ID (entero): ");
            dto.PersonaDTO p = personaController.buscar(id);  
            if (p == null) System.out.println("No se encontró persona con ID " + id);
            else System.out.println("Resultado: " + p);
            listarPersonas();
            refreshUIAsync();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }


    private static void personaEditar(Scanner sc) {
        System.out.println("\n--- Editar Persona ---");
        try {
            String id = askNumeric(sc, "ID (entero) a editar: ");
            dto.PersonaDTO actual = personaController.buscar(id);   
            if (actual == null) { System.out.println("No existe persona con ID " + id); return; }

            String nombres   = askDefault(sc, "Nombres [" + actual.getNombres() + "]: ", actual.getNombres());
            String apellidos = askDefault(sc, "Apellidos [" + actual.getApellidos() + "]: ", actual.getApellidos());
            String email     = askDefaultEmail(sc, "Email [" + actual.getEmail() + "]: ", actual.getEmail());

            dto.PersonaDTO dto = new dto.PersonaDTO(Double.valueOf(id), nombres, apellidos, email);
            boolean ok = personaController.actualizar(dto);        

            System.out.println(ok ? " Persona actualizada." : "× No se pudo actualizar.");
            listarPersonas();
            refreshUIAsync();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }


    private static void personaEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Persona ---");
        try {
            String id = askNumeric(sc, "ID (entero) a eliminar: ");
            dto.PersonaDTO p = personaController.buscar(id);       
            if (p == null) { System.out.println("No existe persona con ID " + id); return; }

            System.out.println("Se eliminará: " + p);
            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { 
                System.out.println("Operación cancelada."); 
                return; 
            }

            boolean ok = personaController.eliminar(id);            
            System.out.println(ok ? " Persona eliminada." : "× No se pudo eliminar.");
            listarPersonas();
            refreshUIAsync();
        } catch (Exception e) {
            System.out.println("× Error: " + e.getMessage());
        }
    }


    private static void listarPersonas() {
        try {
            var lista = personaController.listar();                  
            System.out.println("\n=== Personas en BD ===");
            if (lista == null || lista.isEmpty()) { 
                System.out.println("(sin registros)"); 
                return; 
            }
            for (dto.PersonaDTO p : lista) {
                System.out.println(" - ID=" + p.getId() + ", " + p.getNombres() + " " + p.getApellidos() + " <" + p.getEmail() + ">");
            }
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
                case "0": System.out.println("¡Hasta luego!"); return;
                default:  System.out.println("Opción inválida.");
            }
        }
    }

    private static void profesorCrear(Scanner sc) {
    System.out.println("\n--- Crear Profesor ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero): ");
            String contrato  = askNonEmpty(sc, "Contrato (p.ej. Cátedra/Tiempo completo): ");

            dto.ProfesorDTO dto = new dto.ProfesorDTO(Double.valueOf(idPersona), contrato);
            profesorController.insertar(dto);
            System.out.println(" Profesor creado.");
            profesorListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void profesorBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Profesor ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero): ");
            dto.ProfesorDTO p = profesorController.buscar(idPersona);
            if (p == null) System.out.println("No existe profesor con ID persona " + idPersona);
            else System.out.println("Resultado: " + p);
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void profesorEditar(Scanner sc) {
        System.out.println("\n--- Editar Profesor ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero) a editar: ");
            dto.ProfesorDTO actual = profesorController.buscar(idPersona);
            if (actual == null) { System.out.println("No existe profesor con ID persona " + idPersona); return; }

            String contrato = askDefault(sc, "Contrato [" + actual.getContrato() + "]: ", actual.getContrato());

            dto.ProfesorDTO dto = new dto.ProfesorDTO(Double.valueOf(idPersona), contrato);
            boolean ok = profesorController.actualizar(dto);

            System.out.println(ok ? "Profesor actualizado." : "× No se pudo actualizar.");
            profesorListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void profesorEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Profesor ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero) a eliminar: ");
            dto.ProfesorDTO actual = profesorController.buscar(idPersona);
            if (actual == null) { System.out.println("No existe profesor con ID persona " + idPersona); return; }

            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { System.out.println("Cancelado."); return; }

            boolean ok = profesorController.eliminar(idPersona);
            System.out.println(ok ? "Profesor eliminado." : "× No se pudo eliminar.");
            profesorListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void profesorListar() {
        try {
            javax.swing.table.DefaultTableModel modelo = profesorController.modeloTablaTodos();
            System.out.println("\n=== Profesores en BD ===");
            if (modelo.getRowCount() == 0) { System.out.println("(sin registros)"); return; }
            for (int i = 0; i < modelo.getRowCount(); i++) {
                System.out.println(" - ID Persona=" + modelo.getValueAt(i,0) + ", Contrato=" + modelo.getValueAt(i,1));
            }
        } catch (Exception e) { System.out.println("× Error listando profesores: " + e.getMessage()); }
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
                case "0": System.out.println("¡Hasta luego!"); return;
                default:  System.out.println("Opción inválida.");
            }
        }
    }

    private static void estudianteCrear(Scanner sc) {
        System.out.println("\n--- Crear Estudiante ---");
        try {
            String idPersona  = askNumeric(sc, "ID Persona (entero): ");
            String codigo     = askNonEmpty(sc, "Código: ");
            String idPrograma = ask(sc, "ID Programa (opcional, Enter para dejar vacío): ").trim();

            Double idPers = Double.valueOf(idPersona);
            Double idProg = idPrograma.isEmpty()? null : Double.valueOf(idPrograma);

            dto.EstudianteDTO dto = new dto.EstudianteDTO(idPers, codigo, idProg);
            estudianteController.insertar(dto);

            System.out.println(" Estudiante creado.");
            estudianteListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void estudianteBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Estudiante ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero): ");
            dto.EstudianteDTO e = estudianteController.buscar(idPersona);
            if (e == null) System.out.println("No existe estudiante con ID persona " + idPersona);
            else {
                System.out.println("Resultado: ID Persona=" + e.getIdPersona()
                    + ", Código=" + e.getCodigo()
                    + ", Programa=" + (e.getProgramaNombre()==null? "" : e.getProgramaNombre()));
            }
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void estudianteEditar(Scanner sc) {
        System.out.println("\n--- Editar Estudiante ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero) a editar: ");
            dto.EstudianteDTO actual = estudianteController.buscar(idPersona);
            if (actual == null) { System.out.println("No existe estudiante con ID persona " + idPersona); return; }

            String codigo = askDefault(sc, "Código [" + actual.getCodigo() + "]: ", actual.getCodigo());
            String idPrograma = ask(sc, "ID Programa (opcional, Enter para dejar vacío" +
                    (actual.getIdPrograma()!=null? ", actual="+actual.getIdPrograma() : "") + "): ").trim();

            Double idPers = Double.valueOf(idPersona);
            Double idProg = idPrograma.isEmpty()? null : Double.valueOf(idPrograma);

            dto.EstudianteDTO dto = new dto.EstudianteDTO(idPers, codigo, idProg);
            boolean ok = estudianteController.actualizar(dto);

            System.out.println(ok ? "Estudiante actualizado." : "× No se pudo actualizar.");
            estudianteListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void estudianteEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Estudiante ---");
        try {
            String idPersona = askNumeric(sc, "ID Persona (entero) a eliminar: ");
            dto.EstudianteDTO actual = estudianteController.buscar(idPersona);
            if (actual == null) { System.out.println("No existe estudiante con ID persona " + idPersona); return; }

            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { System.out.println("Cancelado."); return; }

            boolean ok = estudianteController.eliminar(idPersona);
            System.out.println(ok ? "Estudiante eliminado." : "× No se pudo eliminar.");
            estudianteListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void estudianteListar() {
        try {
            var lista = estudianteController.listar();
            System.out.println("\n=== Estudiantes en BD ===");
            if (lista == null || lista.isEmpty()) { System.out.println("(sin registros)"); return; }
            for (dto.EstudianteDTO e : lista) {
                System.out.println(" - ID Persona=" + e.getIdPersona()
                    + ", Código=" + e.getCodigo()
                    + ", Programa=" + (e.getProgramaNombre()==null? "" : e.getProgramaNombre()));
            }
        } catch (Exception e) { System.out.println("× Error listando estudiantes: " + e.getMessage()); }
    }


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
                case "0": System.out.println("¡Hasta luego!"); return;
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
            dto.FacultadDTO dto = facultadController.buildFromStrings(id, nombre, decanoId);
            facultadController.insertar(dto);
            System.out.println("Facultad creada.");
            facultadListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void facultadBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Facultad ---");
        try {
            String id = askNumericDouble(sc, "ID (entero): ");
            dto.FacultadDTO f = facultadController.buscar(id);
            if (f == null) System.out.println("No existe facultad con ID " + id);
            else System.out.println("Resultado: ID=" + f.getId() + ", Nombre=" + f.getNombre() +
                                    ", Decano=" + (f.getDecanoNombre()==null? "" : f.getDecanoNombre()));
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void facultadEditar(Scanner sc) {
        System.out.println("\n--- Editar Facultad ---");
        try {
            String id = askNumeric(sc, "ID (entero) a editar: ");
            dto.FacultadDTO actual = facultadController.buscar(id);
            if (actual == null) { System.out.println("No existe facultad con ID " + id); return; }

            String nombre = askDefault(sc, "Nombre [" + actual.getNombre() + "]: ", actual.getNombre());
            String decanoId = askDefault(sc, "ID Decano [" + (actual.getDecanoId()==null? "" : actual.getDecanoId()) + "]: ",
                                         actual.getDecanoId()==null? "" : String.valueOf(actual.getDecanoId()));
            dto.FacultadDTO dto = facultadController.buildFromStrings(id, nombre, decanoId);
            boolean ok = facultadController.actualizar(dto);
            System.out.println(ok ? "Facultad actualizada." : "× No se pudo actualizar.");
            facultadListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void facultadEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Facultad ---");
        try {
            String id = askNumeric(sc, "ID (entero) a eliminar: ");
            dto.FacultadDTO actual = facultadController.buscar(id);
            if (actual == null) { System.out.println("No existe facultad con ID " + id); return; }

            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { System.out.println("Cancelado."); return; }

            boolean ok = facultadController.eliminar(id);
            System.out.println(ok ? "Facultad eliminada." : "× No se pudo eliminar.");
            facultadListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
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
        } catch (Exception e) { System.out.println("× Error listando facultades: " + e.getMessage()); }
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
                case "0": System.out.println("¡Hasta luego!"); return;
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

            dto.ProgramaDTO dto = programaController.buildFromStrings(id, nombre, duracion, registro, idFacultad);
            programaController.insertar(dto);

            System.out.println("Programa creado.");
            programaListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void programaBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Programa ---");
        try {
            String id = askNumericDouble(sc, "ID (entero): ");
            dto.ProgramaDTO p = programaController.buscar(id);
            if (p == null) System.out.println("No existe programa con ID " + id);
            else System.out.println("Resultado: ID=" + p.getId() + ", Nombre=" + p.getNombre() +
                                    ", Duración=" + p.getDuracion() +
                                    ", Facultad=" + (p.getFacultadNombre()==null? "" : p.getFacultadNombre()));
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void programaEditar(Scanner sc) {
        System.out.println("\n--- Editar Programa ---");
        try {
            String id = askNumeric(sc, "ID (entero) a editar: ");
            dto.ProgramaDTO actual = programaController.buscar(id);
            if (actual == null) { System.out.println("No existe programa con ID " + id); return; }

            String nombre = askDefault(sc, "Nombre [" + actual.getNombre() + "]: ", actual.getNombre());
            String dur = askDefault(sc, "Duración [" + actual.getDuracion() + "]: ", String.valueOf(actual.getDuracion()));
            String reg = askDefault(sc, "Fecha registro [" + actual.getRegistro() + "]: ", actual.getRegistro());
            String idFac = askDefault(sc, "ID Facultad [" + (actual.getIdFacultad()==null?"":actual.getIdFacultad()) + "]: ",
                                      actual.getIdFacultad()==null? "" : String.valueOf(actual.getIdFacultad()));

            dto.ProgramaDTO dto = programaController.buildFromStrings(id, nombre, dur, reg, idFac);
            boolean ok = programaController.actualizar(dto);
            System.out.println(ok ? "Programa actualizado." : "× No se pudo actualizar.");
            programaListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void programaEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Programa ---");
        try {
            String id = askNumeric(sc, "ID (entero) a eliminar: ");
            dto.ProgramaDTO actual = programaController.buscar(id);
            if (actual == null) { System.out.println("No existe programa con ID " + id); return; }

            String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { System.out.println("Cancelado."); return; }

            boolean ok = programaController.eliminar(id);
            System.out.println(ok ? "Programa eliminado." : "× No se pudo eliminar.");
            programaListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("× Error: " + e.getMessage()); }
    }

    private static void programaListar() {
        try {
            javax.swing.table.DefaultTableModel modelo = programaController.modeloTablaTodos();
            System.out.println("\n=== Programas en BD ===");
            if (modelo.getRowCount() == 0) { System.out.println("(sin registros)"); return; }
            for (int i = 0; i < modelo.getRowCount(); i++)
                System.out.println(" - ID=" + modelo.getValueAt(i,0) + ", Nombre=" + modelo.getValueAt(i,1) +
                                   ", Duración=" + modelo.getValueAt(i,2) + ", Facultad=" + modelo.getValueAt(i,4));
        } catch (Exception e) { System.out.println("× Error listando programas: " + e.getMessage()); }
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
            case "0": System.out.println("¡Hasta luego!"); return;
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

        cursoController.insertar(id, nombre, idPrograma, activoBool);
        System.out.println("Curso creado.");
        cursoListar();
        refreshUIAsync();
    } catch (Exception e) {
        System.out.println("× Error: " + e.getMessage());
    }
}

private static void cursoBuscar(Scanner sc) {
    System.out.println("\n--- Buscar Curso ---");
    try {
        String id = askNumericDouble(sc, "ID (entero): ");
        CursoDTO dto = cursoController.buscarDTO(id);
        if (dto == null) {
            System.out.println("No existe curso con ID " + id);
        } else {
            System.out.println("Resultado:");
            System.out.println(" - ID: " + dto.getId());
            System.out.println(" - Nombre: " + dto.getNombre());
            System.out.println(" - Programa: " + (dto.getProgramaNombre()==null? "" : dto.getProgramaNombre()));
            System.out.println(" - Activo: " + (Boolean.TRUE.equals(dto.getActivo()) ? "Sí" : "No"));
        }
    } catch (Exception e) {
        System.out.println("× Error: " + e.getMessage());
    }
}

private static void cursoEditar(Scanner sc) {
    System.out.println("\n--- Editar Curso ---");
    try {
        String id = askNumeric(sc, "ID (entero) a editar: ");
        CursoDTO actual = cursoController.buscarDTO(id);
        if (actual == null) { 
            System.out.println("No existe curso con ID " + id); 
            return; 
        }

        String nombre = askDefault(sc, "Nombre [" + actual.getNombre() + "]: ", actual.getNombre());
        String idPrograma = ask(sc, "ID Programa (Enter para dejar vacío"
            + (actual.getProgramaId()!=null ? ", actual=" + actual.getProgramaId() : "")
            + "): ").trim();
        String activo = ask(sc, "¿Activo? (S/N) [" + (Boolean.TRUE.equals(actual.getActivo())? "S":"N") + "]: ")
            .trim().toLowerCase(Locale.ROOT);

        boolean activoBool = activo.isEmpty() ? Boolean.TRUE.equals(actual.getActivo())
                         : (activo.equals("s") || activo.equals("si") || activo.equals("sí"));

        boolean ok = cursoController.actualizar(id, nombre, idPrograma, activoBool);
        System.out.println(ok ? "Curso actualizado." : "No se pudo actualizar.");
        cursoListar();
        refreshUIAsync();
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

private static void cursoEliminar(Scanner sc) {
    System.out.println("\n--- Eliminar Curso ---");
    try {
        String id = askNumeric(sc, "ID (entero) a eliminar: ");
        CursoDTO actual = cursoController.buscarDTO(id);
        if (actual == null) { 
            System.out.println("No existe curso con ID " + id); 
            return; 
        }
        String conf = ask(sc, "¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
        if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { 
            System.out.println("Cancelado."); 
            return; 
        }

        boolean ok = cursoController.eliminar(id);
        System.out.println(ok ? "Curso eliminado." : "× No se pudo eliminar.");
        cursoListar();
        refreshUIAsync();
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

private static void cursoListar() {
    try {
        List<CursoDTO> lista = cursoController.listarDTO();
        System.out.println("\n=== Cursos en BD ===");
        if (lista == null || lista.isEmpty()) { 
            System.out.println("(sin registros)"); 
            return; 
        }
        for (CursoDTO dto : lista) {
            System.out.println(" - ID=" + dto.getId()
                + ", Nombre=" + dto.getNombre()
                + ", Programa=" + (dto.getProgramaNombre()==null? "" : dto.getProgramaNombre())
                + ", Activo=" + (Boolean.TRUE.equals(dto.getActivo())? "Sí":"No"));
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
                case "0": System.out.println("¡Hasta luego!"); return;
                default:  System.out.println("Opción inválida.");
            }
        }
    }

    private static void inscripcionCrear(Scanner sc) {
        System.out.println("\n--- Crear Inscripción ---");
        try {
            var dto = inscController.build(
                askNumeric(sc,"ID Curso (entero): "),
                askNumeric(sc,"ID Estudiante (entero): "),
                askNumeric(sc,"Año: "),
                askNumeric(sc,"Semestre (1 o 2): ")
            );
            boolean ok = inscController.insertar(dto);
            System.out.println(ok ? "Inscripción creada." : "No se pudo crear.");
            inscripcionListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void inscripcionBuscar(Scanner sc) {
        System.out.println("\n--- Buscar Inscripción ---");
        try {
            var key = inscController.build(
                askNumeric(sc,"ID Curso (entero): "),
                askNumeric(sc,"ID Estudiante (entero): "),
                askNumeric(sc,"Año: "),
                askNumeric(sc,"Semestre (1 o 2): ")
            );
            var dto = inscController.buscar(key);
            if (dto == null) System.out.println("No existe inscripción con esos datos");
            else System.out.println("Resultado: Curso="+(dto.getCursoNombre()==null? "" : dto.getCursoNombre())+
                    ", Estudiante="+(dto.getEstudianteNombre()==null? "" : dto.getEstudianteNombre())+
                    ", Año="+dto.getAnio()+", Semestre="+dto.getSemestre());
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void inscripcionEditar(Scanner sc) {
        System.out.println("\n--- Editar Inscripción ---");
        try {
            System.out.println("Datos actuales:");
            var oldDto = inscController.build(
                askNumeric(sc,"ID Curso actual: "),
                askNumeric(sc,"ID Estudiante actual: "),
                askNumeric(sc,"Año actual: "),
                askNumeric(sc,"Semestre actual: ")
            );
            System.out.println("Nuevos datos:");
            var newDto = inscController.build(
                askNumeric(sc,"ID Curso nuevo: "),
                askNumeric(sc,"ID Estudiante nuevo: "),
                askNumeric(sc,"Año nuevo: "),
                askNumeric(sc,"Semestre nuevo: ")
            );
            boolean ok = inscController.actualizar(oldDto, newDto);
            System.out.println(ok ? "Inscripción actualizada." : "No se pudo actualizar.");
            inscripcionListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void inscripcionEliminar(Scanner sc) {
        System.out.println("\n--- Eliminar Inscripción ---");
        try {
            var key = inscController.build(
                askNumeric(sc,"ID Curso (entero): "),
                askNumeric(sc,"ID Estudiante (entero): "),
                askNumeric(sc,"Año: "),
                askNumeric(sc,"Semestre (1 o 2): ")
            );
            String conf = ask(sc,"¿Confirmar? (S/N): ").trim().toLowerCase(Locale.ROOT);
            if (!conf.equals("s") && !conf.equals("si") && !conf.equals("sí")) { System.out.println("Cancelado."); return; }
            boolean ok = inscController.eliminar(key);
            System.out.println(ok ? "Inscripción eliminada." : "No se pudo eliminar.");
            inscripcionListar();
            refreshUIAsync();
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void inscripcionListar() {
        var modelo = inscController.modeloTablaTodos();
        System.out.println("\n=== Inscripciones en BD ===");
        if (modelo.getRowCount() == 0) {
            System.out.println("(sin registros)");
            return;
        }
        for (int i = 0; i < modelo.getRowCount(); i++) {
            System.out.println(" - Curso=" + modelo.getValueAt(i,1)
                    + ", Estudiante=" + modelo.getValueAt(i,3)
                    + ", Año=" + modelo.getValueAt(i,4)
                    + ", Semestre=" + modelo.getValueAt(i,5));
        }
    }



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
    dbg("Leído (non-empty): '" + s + "' para " + prompt);
    return s;
    }

    private static String askEmail(Scanner sc, String prompt) {
    String s = ask(sc, prompt);
    while (s.isEmpty() || !s.contains("@")) {
        System.out.println("  -> Email inválido.");
        s = ask(sc, prompt);
    }
    dbg("Leído (email): '" + s + "'");
    return s;
    }

    private static String askDefault(Scanner sc, String prompt, String def) {
        String s = ask(sc, prompt);
        return null;
    }

    private static String askDefaultEmail(Scanner sc, String prompt, String def) {
        String s = ask(sc, prompt);
        if (s.isEmpty()) return def;
        while (!s.contains("@")) {
            System.out.println("  -> Email inválido.");
            s = ask(sc, prompt);
            if (s.isEmpty()) return def;
        }
        return null;
    }

    private static String askNumeric(Scanner sc, String prompt) {
        String s = ask(sc, prompt);
        while (!s.matches("\\d+")) {
            System.out.println("  -> Debe ser número entero positivo.");
            s = ask(sc, prompt);
        }
        return null;
    }
    private static String askNumericDouble(Scanner sc, String prompt) {
    String s = ask(sc, prompt);
    while (!s.matches("\\d+(\\.\\d+)?")) {
        System.out.println("  -> Debe ser un número (entero o decimal positivo).");
        s = ask(sc, prompt);
    }
    dbg("ID leído: '" + s + "'");
    return s;
    }


}
