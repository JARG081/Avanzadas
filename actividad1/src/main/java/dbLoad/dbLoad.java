package dbLoad;

import com.mycompany.actividad1.adapter.DatabaseAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class dbLoad {

    public static void init(DatabaseAdapter adapter) {
        if (adapter == null) return;
        adapter.initDatabase();
        String type = adapter.getDatabaseType();
        if ("Oracle".equalsIgnoreCase(type)) {
            seedOracle(adapter);
        } else if ("MySQL".equalsIgnoreCase(type)) {
            seedMySQL(adapter);
        } else {
            seedGeneric(adapter);
        }
    }

    /* ---------------- ORACLE ---------------- */
    private static void seedOracle(DatabaseAdapter adapter) {
        try (Connection conn = adapter.getConnection()) {
            // PERSONA (5)
            conn.createStatement().executeUpdate(
                "MERGE INTO PERSONA p " +
                "USING (SELECT 1 AS id, 'Juan' AS nombres, 'Perez' AS apellidos, 'juan@uni.com' AS email FROM dual) src " +
                "ON (p.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombres,apellidos,email) VALUES (src.id,src.nombres,src.apellidos,src.email)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO PERSONA p USING (SELECT 2 AS id, 'María' AS nombres, 'Gomez' AS apellidos, 'maria@uni.com' AS email FROM dual) src " +
                "ON (p.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombres,apellidos,email) VALUES (src.id,src.nombres,src.apellidos,src.email)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO PERSONA p USING (SELECT 3 AS id, 'Luis' AS nombres, 'Torres' AS apellidos, 'luis@uni.com' AS email FROM dual) src " +
                "ON (p.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombres,apellidos,email) VALUES (src.id,src.nombres,src.apellidos,src.email)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO PERSONA p USING (SELECT 4 AS id, 'Ana' AS nombres, 'Ruiz' AS apellidos, 'ana@uni.com' AS email FROM dual) src " +
                "ON (p.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombres,apellidos,email) VALUES (src.id,src.nombres,src.apellidos,src.email)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO PERSONA p USING (SELECT 5 AS id, 'Carlos' AS nombres, 'Ramos' AS apellidos, 'carlos@uni.com' AS email FROM dual) src " +
                "ON (p.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombres,apellidos,email) VALUES (src.id,src.nombres,src.apellidos,src.email)"
            );

            // FACULTAD (2)
            conn.createStatement().executeUpdate(
                "MERGE INTO FACULTAD f USING (SELECT 1 AS id, 'Ingenieria' AS nombre, 1 AS id_decano FROM dual) src " +
                "ON (f.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombre,id_decano) VALUES (src.id,src.nombre,src.id_decano)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO FACULTAD f USING (SELECT 2 AS id, 'Ciencias' AS nombre, 2 AS id_decano FROM dual) src " +
                "ON (f.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombre,id_decano) VALUES (src.id,src.nombre,src.id_decano)"
            );

            // PROGRAMA (2) -> registro como DATE
            conn.createStatement().executeUpdate(
                "MERGE INTO PROGRAMA pr USING (SELECT 1 AS id, 'Sistemas' AS nombre, 8 AS duracion, TO_DATE('2020-01-01','YYYY-MM-DD') AS registro, 1 AS id_facultad FROM dual) src " +
                "ON (pr.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombre,duracion,registro,id_facultad) VALUES (src.id,src.nombre,src.duracion,src.registro,src.id_facultad)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO PROGRAMA pr USING (SELECT 2 AS id, 'Matematicas' AS nombre, 6 AS duracion, TO_DATE('2019-03-15','YYYY-MM-DD') AS registro, 2 AS id_facultad FROM dual) src " +
                "ON (pr.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombre,duracion,registro,id_facultad) VALUES (src.id,src.nombre,src.duracion,src.registro,src.id_facultad)"
            );

            // PROFESOR (2) -> referencia PERSONA(1,2)
            conn.createStatement().executeUpdate(
                "MERGE INTO PROFESOR prf USING (SELECT 1 AS id, 1 AS id_persona, 'Trabajo' AS contrato FROM dual) src " +
                "ON (prf.id = src.id) WHEN NOT MATCHED THEN INSERT (id,id_persona,contrato) VALUES (src.id,src.id_persona,src.contrato)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO PROFESOR prf USING (SELECT 2 AS id, 2 AS id_persona, 'Servicios' AS contrato FROM dual) src " +
                "ON (prf.id = src.id) WHEN NOT MATCHED THEN INSERT (id,id_persona,contrato) VALUES (src.id,src.id_persona,src.contrato)"
            );

            // ESTUDIANTE (3) -> id_persona 3,4,5
            conn.createStatement().executeUpdate(
                "MERGE INTO ESTUDIANTE e USING (SELECT 3 AS id_persona, 1001 AS codigo, 1 AS id_programa, 1 AS activo, 4.2 AS promedio FROM dual) src " +
                "ON (e.id_persona = src.id_persona) WHEN NOT MATCHED THEN INSERT (id_persona,codigo,id_programa,activo,promedio) VALUES (src.id_persona,src.codigo,src.id_programa,src.activo,src.promedio)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO ESTUDIANTE e USING (SELECT 4 AS id_persona, 1002 AS codigo, 1 AS id_programa, 1 AS activo, 3.8 AS promedio FROM dual) src " +
                "ON (e.id_persona = src.id_persona) WHEN NOT MATCHED THEN INSERT (id_persona,codigo,id_programa,activo,promedio) VALUES (src.id_persona,src.codigo,src.id_programa,src.activo,src.promedio)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO ESTUDIANTE e USING (SELECT 5 AS id_persona, 1003 AS codigo, 2 AS id_programa, 1 AS activo, 4.5 AS promedio FROM dual) src " +
                "ON (e.id_persona = src.id_persona) WHEN NOT MATCHED THEN INSERT (id_persona,codigo,id_programa,activo,promedio) VALUES (src.id_persona,src.codigo,src.id_programa,src.activo,src.promedio)"
            );

            // CURSO (3) -> id_programa 1 o 2
            conn.createStatement().executeUpdate(
                "MERGE INTO CURSO c USING (SELECT 1 AS id, 'Algoritmos' AS nombre, 1 AS id_programa, 1 AS activo FROM dual) src " +
                "ON (c.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombre,id_programa,activo) VALUES (src.id,src.nombre,src.id_programa,src.activo)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO CURSO c USING (SELECT 2 AS id, 'Estructuras' AS nombre, 1 AS id_programa, 1 AS activo FROM dual) src " +
                "ON (c.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombre,id_programa,activo) VALUES (src.id,src.nombre,src.id_programa,src.activo)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO CURSO c USING (SELECT 3 AS id, 'Calculo' AS nombre, 2 AS id_programa, 1 AS activo FROM dual) src " +
                "ON (c.id = src.id) WHEN NOT MATCHED THEN INSERT (id,nombre,id_programa,activo) VALUES (src.id,src.nombre,src.id_programa,src.activo)"
            );

            // INSCRIPCION (4) -> en Oracle curso_id es PK según tu DDL; usamos curso_id 1..4
            conn.createStatement().executeUpdate(
                "MERGE INTO INSCRIPCION i USING (SELECT 1 AS curso_id, 3 AS estudiante_id, 2023 AS anio, 1 AS semestre FROM dual) src " +
                "ON (i.curso_id = src.curso_id) WHEN NOT MATCHED THEN INSERT (curso_id,estudiante_id,anio,semestre) VALUES (src.curso_id,src.estudiante_id,src.anio,src.semestre)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO INSCRIPCION i USING (SELECT 2 AS curso_id, 4 AS estudiante_id, 2023 AS anio, 1 AS semestre FROM dual) src " +
                "ON (i.curso_id = src.curso_id) WHEN NOT MATCHED THEN INSERT (curso_id,estudiante_id,anio,semestre) VALUES (src.curso_id,src.estudiante_id,src.anio,src.semestre)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO INSCRIPCION i USING (SELECT 3 AS curso_id, 5 AS estudiante_id, 2023 AS anio, 2 AS semestre FROM dual) src " +
                "ON (i.curso_id = src.curso_id) WHEN NOT MATCHED THEN INSERT (curso_id,estudiante_id,anio,semestre) VALUES (src.curso_id,src.estudiante_id,src.anio,src.semestre)"
            );
            conn.createStatement().executeUpdate(
                "MERGE INTO INSCRIPCION i USING (SELECT 4 AS curso_id, 3 AS estudiante_id, 2024 AS anio, 1 AS semestre FROM dual) src " +
                "ON (i.curso_id = src.curso_id) WHEN NOT MATCHED THEN INSERT (curso_id,estudiante_id,anio,semestre) VALUES (src.curso_id,src.estudiante_id,src.anio,src.semestre)"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* ---------------- MySQL ---------------- */
    private static void seedMySQL(DatabaseAdapter adapter) {
        try (Connection conn = adapter.getConnection()) {

            // PERSONA (5)
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO PERSONA (id,nombres,apellidos,email) VALUES (1,'Juan','Perez','juan@uni.com') " +
                    "ON DUPLICATE KEY UPDATE nombres=VALUES(nombres),apellidos=VALUES(apellidos),email=VALUES(email)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO PERSONA (id,nombres,apellidos,email) VALUES (2,'María','Gomez','maria@uni.com') " +
                    "ON DUPLICATE KEY UPDATE nombres=VALUES(nombres),apellidos=VALUES(apellidos),email=VALUES(email)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO PERSONA (id,nombres,apellidos,email) VALUES (3,'Luis','Torres','luis@uni.com') " +
                    "ON DUPLICATE KEY UPDATE nombres=VALUES(nombres),apellidos=VALUES(apellidos),email=VALUES(email)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO PERSONA (id,nombres,apellidos,email) VALUES (4,'Ana','Ruiz','ana@uni.com') " +
                    "ON DUPLICATE KEY UPDATE nombres=VALUES(nombres),apellidos=VALUES(apellidos),email=VALUES(email)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO PERSONA (id,nombres,apellidos,email) VALUES (5,'Carlos','Ramos','carlos@uni.com') " +
                    "ON DUPLICATE KEY UPDATE nombres=VALUES(nombres),apellidos=VALUES(apellidos),email=VALUES(email)")) {
                ps.executeUpdate();
            }

            // FACULTAD (2)
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO FACULTAD (id,nombre,id_decano) VALUES (1,'Ingenieria',1) " +
                    "ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),id_decano=VALUES(id_decano)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO FACULTAD (id,nombre,id_decano) VALUES (2,'Ciencias',2) " +
                    "ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),id_decano=VALUES(id_decano)")) {
                ps.executeUpdate();
            }

            // PROGRAMA (2)
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO PROGRAMA (id,nombre,duracion,registro,id_facultad) VALUES (1,'Sistemas',8,'2020-01-01',1) " +
                    "ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),duracion=VALUES(duracion),registro=VALUES(registro),id_facultad=VALUES(id_facultad)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO PROGRAMA (id,nombre,duracion,registro,id_facultad) VALUES (2,'Matematicas',6,'2019-03-15',2) " +
                    "ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),duracion=VALUES(duracion),registro=VALUES(registro),id_facultad=VALUES(id_facultad)")) {
                ps.executeUpdate();
            }

            // PROFESOR (2)
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO PROFESOR (id,id_persona,contrato) VALUES (1,1,'Trabajo') " +
                    "ON DUPLICATE KEY UPDATE id_persona=VALUES(id_persona),contrato=VALUES(contrato)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO PROFESOR (id,id_persona,contrato) VALUES (2,2,'Servicios') " +
                    "ON DUPLICATE KEY UPDATE id_persona=VALUES(id_persona),contrato=VALUES(contrato)")) {
                ps.executeUpdate();
            }

            // ESTUDIANTE (3)
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO ESTUDIANTE (id_persona,codigo,id_programa,activo,promedio) VALUES (3,1001,1,1,4.2) " +
                    "ON DUPLICATE KEY UPDATE codigo=VALUES(codigo),id_programa=VALUES(id_programa),activo=VALUES(activo),promedio=VALUES(promedio)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO ESTUDIANTE (id_persona,codigo,id_programa,activo,promedio) VALUES (4,1002,1,1,3.8) " +
                    "ON DUPLICATE KEY UPDATE codigo=VALUES(codigo),id_programa=VALUES(id_programa),activo=VALUES(activo),promedio=VALUES(promedio)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO ESTUDIANTE (id_persona,codigo,id_programa,activo,promedio) VALUES (5,1003,2,1,4.5) " +
                    "ON DUPLICATE KEY UPDATE codigo=VALUES(codigo),id_programa=VALUES(id_programa),activo=VALUES(activo),promedio=VALUES(promedio)")) {
                ps.executeUpdate();
            }

            // CURSO (3)
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO CURSO (id,nombre,id_programa,activo) VALUES (1,'Algoritmos',1,1) " +
                    "ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),id_programa=VALUES(id_programa),activo=VALUES(activo)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO CURSO (id,nombre,id_programa,activo) VALUES (2,'Estructuras',1,1) " +
                    "ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),id_programa=VALUES(id_programa),activo=VALUES(activo)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO CURSO (id,nombre,id_programa,activo) VALUES (3,'Calculo',2,1) " +
                    "ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),id_programa=VALUES(id_programa),activo=VALUES(activo)")) {
                ps.executeUpdate();
            }

            // INSCRIPCION (4) -> MySQL version has 'id' PK in your DDL, usamos id 1..4
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO INSCRIPCION (id,curso_id,estudiante_id,anio,semestre) VALUES (1,1,3,2023,1) " +
                    "ON DUPLICATE KEY UPDATE curso_id=VALUES(curso_id),estudiante_id=VALUES(estudiante_id),anio=VALUES(anio),semestre=VALUES(semestre)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO INSCRIPCION (id,curso_id,estudiante_id,anio,semestre) VALUES (2,2,4,2023,1) " +
                    "ON DUPLICATE KEY UPDATE curso_id=VALUES(curso_id),estudiante_id=VALUES(estudiante_id),anio=VALUES(anio),semestre=VALUES(semestre)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO INSCRIPCION (id,curso_id,estudiante_id,anio,semestre) VALUES (3,3,5,2023,2) " +
                    "ON DUPLICATE KEY UPDATE curso_id=VALUES(curso_id),estudiante_id=VALUES(estudiante_id),anio=VALUES(anio),semestre=VALUES(semestre)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO INSCRIPCION (id,curso_id,estudiante_id,anio,semestre) VALUES (4,4,3,2024,1) " +
                    "ON DUPLICATE KEY UPDATE curso_id=VALUES(curso_id),estudiante_id=VALUES(estudiante_id),anio=VALUES(anio),semestre=VALUES(semestre)")) {
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* ---------------- GENERIC FALLBACK (intento simple, idempotente) ---------------- */
    private static void seedGeneric(DatabaseAdapter adapter) {
        try (Connection conn = adapter.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO PERSONA (id,nombres,apellidos,email) VALUES (1,'Juan','Perez','juan@uni.com')")) {
                ps.executeUpdate();
            } catch (SQLException ignored) {}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
