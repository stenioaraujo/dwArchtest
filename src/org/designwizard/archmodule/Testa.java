package org.designwizard.archmodule;

import java.io.IOException;

import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

public class Testa {
	public static void main(String[] args) throws IOException, InexistentEntityException {
		DesignWizard dw = new DesignWizard("classes\\");
		
		ClassNode classe = dw.getClass("org.designwizard.archmodule.Cliente");
		
		String cliente = "ArchModule.cliente";
		String banco = "ArchModule.banco";
		
		System.out.println(
			cliente + " não depende de " + banco + "\n" +
			dw.getModule(cliente).cannotDependOn(banco)	
		);
	}
}
