package org.designwizard.archmodule;

@ArchModule("controller")
public class Aplicacao {
    public Aplicacao() {}

    public static void index() {
        Livro livro = new Livro("Harry Potter");
        View.print(livro);
    }
}
