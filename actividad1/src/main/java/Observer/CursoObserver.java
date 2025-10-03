/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observer;

import com.mycompany.actividad1.model.Curso;

/**
 *
 * @author josem
 */
public interface CursoObserver {
    void onCursoCreado(Curso curso);
}
