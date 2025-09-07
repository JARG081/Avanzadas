package com.mycompany.actividad1;

import controller.PersonaController;
import com.mycompany.actividad1.model.Persona;
import ui.Pantalla;

import javax.swing.*;
import java.awt.GraphicsEnvironment;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    //se hizo el push?
    private static final PersonaController personaController = new PersonaController();

    public static void main(String[] args) {
        // Atajos opcionales por parámetro:
        //   --console  => forza consola
        //   --gui      => forza interfaz
        if (args.length > 0) {
            String a0 = args[0].toLowerCase(Locale.ROOT);
            if ("--console".equals(a0)) { runConsoleMenus(); return; }
            if ("--gui".equals(a0))     { launchGUI();       return; }
        }

        // Preguntar modo
        if (System.console() != null) {
            askModeInConsole();
        } else if (!GraphicsEnvironment.isHeadless()) {
            askModeInDialog();
        } else {
            // Sin UI disponible, ir a consola
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
            System.out.println("[4] Abrir Interfaz Gráfica");
            System.out.println("[0] Cerrar programa");
            String op = ask(sc, "Opción: ");

            switch (op) {
                case "1": submenuPersona(sc); break;
                case "2": submenuNoImplementado(sc, "Profesor"); break;   // por ahora
                case "3": submenuNoImplementado(sc, "Estudiante"); break; // por ahora
                case "4": launchGUI(); return;
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
            String id = askNumeric(sc, "ID (entero): ");
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
            String id = askNumeric(sc, "ID (entero): ");
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
}
