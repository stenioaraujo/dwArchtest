package org.designwizard.archmodule;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.designwizard.design.ModuleNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

public class ArchModuleTest {

	public static void main(String[] args) throws IOException, InexistentEntityException {
		DesignWizard dw = new DesignWizard("classes\\");
		ModuleNode module = dw.getClass("org.designwizard.archmodule.Aplicacao").getModule();
		
		System.out.println(module.getCalleeModules());
	}
}
