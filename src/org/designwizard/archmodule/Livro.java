package org.designwizard.archmodule;

@ArchModule("model")
public class Livro {
    private String titulo;
    
    public Livro (String titulo) {
        this.titulo = titulo;
    }
    
    public String getTitulo() {
        return this.titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    // aqui é o que não pode
    public void print() {
        View.print(this);
    }
}
