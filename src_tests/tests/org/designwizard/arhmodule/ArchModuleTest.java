package tests.org.designwizard.arhmodule;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.ModuleNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

import junit.framework.TestCase;

/**
 * @author stenioaraujo
 * This class tests all the classes of the implementation about ArchTests.
 */
public class ArchModuleTest extends TestCase {

	private DesignWizard dw;
	private ModuleNode model, controller, view;
	private ClassNode livro, aplicacao, saida, object;
	
	@Override
	protected void setUp() throws Exception {
		dw = new DesignWizard("classes"+File.separator+"org"+File.separator+"designwizard"+File.separator+"archmodule");		
		
		livro = dw.getClass("org.designwizard.archmodule.Livro");
		aplicacao = dw.getClass("org.designwizard.archmodule.Aplicacao");
		saida = dw.getClass("org.designwizard.archmodule.View");
		object = dw.getClass("java.lang.Object");
		
		model = dw.getModule("ArchModule.model");
		controller = dw.getModule("ArchModule.controller");
		view = dw.getModule("ArchModule.view");
	}
	
	public void testGetModule() throws InexistentEntityException {
		assertEquals(model, livro.getModule());
		assertEquals(controller, aplicacao.getModule());
		assertEquals(view, saida.getModule());
	}
	
	public void testModuleDependeces() {
		assertTrue(model.cannotDependOn(controller));
		assertFalse(model.cannotDependOn(view));
		
		assertTrue(view.cannotDependOn(controller));
		assertTrue(view.dependOn(model));
		
		assertTrue(controller.dependOn(view));
		assertTrue(controller.dependOn(model));
	}
	
	public void testClassesInsideModule() {
		Set<ClassNode> classesController, classesModel, classesView;
		
		classesController = new HashSet<ClassNode>();
		classesController.add(aplicacao);
		
		classesModel = new HashSet<ClassNode>();
		classesModel.add(livro);
		
		classesView = new HashSet<ClassNode>();
		classesView.add(saida);

		assertEquals(controller.getAllClasses(), classesController);
		assertEquals(model.getAllClasses(), classesModel);
		assertEquals(view.getAllClasses(), classesView);
	}
	
	public void testCalleeClasses() throws InexistentEntityException {
		Set<ClassNode> calledByController = new HashSet<ClassNode>();
		
		calledByController.add(livro);
		calledByController.add(saida);
		calledByController.add(object); //java.lang.Object
		
		assertEquals(controller.getCalleeClasses(), calledByController);
	}
	
	public void testCallerClasses() {
		Set<ClassNode> calleeModel = new HashSet<ClassNode>();
		
		calleeModel.add(aplicacao);
		calleeModel.add(livro);
		calleeModel.add(saida);
		
		assertEquals(model.getCallerClasses(), calleeModel);
	}
	
	public void testCalleeAndCallerModules() {
		Set<ModuleNode> calledByController = new HashSet<ModuleNode>();
		
		calledByController.add(view);
		calledByController.add(model);
		
		assertTrue(controller.getCallerModules().isEmpty());
		assertEquals(controller.getCalleeModules(), calledByController);
	}
}
