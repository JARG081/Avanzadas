package com.mycompany.actividad1.model;

public class CursoProfesor {
   private Profesor profesor;
   private int año;
   private int semestre;
   private Curso curso;

   public CursoProfesor(Profesor profesor, int año, int semestre, Curso curso) {
      this.profesor = profesor;
      this.año = año;
      this.semestre = semestre;
      this.curso = curso;
   }

   public Profesor getProfesor() {
      return this.profesor;
   }

   public void setProfesor(Profesor profesor) {
      this.profesor = profesor;
   }

   public int getAño() {
      return this.año;
   }

   public void setAño(int año) {
      this.año = año;
   }

   public int getSemestre() {
      return this.semestre;
   }

   public void setSemestre(int semestre) {
      this.semestre = semestre;
   }

   public Curso getCurso() {
      return this.curso;
   }

   public void setCurso(Curso curso) {
      this.curso = curso;
   }

   public String toString() {
      return "CursoProfesor{profesor=" + this.profesor + ", año=" + this.año + ", semestre=" + this.semestre + ", curso=" + this.curso + "}";
   }
}
