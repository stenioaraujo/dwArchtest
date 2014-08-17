package org.designwizard.archmodule;

@ArchModule("view")
public class View {
    public static void print(Livro livro) {
        System.out.println(livro.getTitulo());
    }
}
