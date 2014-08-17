package org.designwizard.design;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;

/**
* <code>ModuleNode</code> objects are constructed automatically by the <code>DesignWizard</code> class when classes
* are loaded. To get access to a desired module extracted, do not use the constructor of this class. Instead, use the
* class <code>DesignWizard</code> as it follows:
* 
* <blockquote><pre>
*  		DesignWizard dw = new DesignWizard("/home/user/application/classes");
*		ModuleNode c = dw.getModule("ArchModule.Module");
* </pre></blockquote>
* 
* Instances of the class <code>ModuleNode</code> represent module in 
* the code extracted.
*/
public class ModuleNode extends AbstractEntity {

	public ModuleNode(String entity) {
	
		super.name = entity;
		super.type = TypesOfEntities.MODULE;
		super.relations = new HashMap<TypesOfRelation,Set<Relation>>();
	
	}

	/**
	 * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the classes inside the module represented by this <code>ModuleNode</code>.
	 * @return a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the classes inside the module represented by this <code>ModuleNode</code>.
	 */
	public Set<ClassNode> getAllClasses() {
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		Set<Relation> relations = super.getRelations(TypesOfRelation.MODULE_OF);

		for (Relation relation: relations) {

			Entity node = relation.getCalledEntity();

			if (node.getTypeOfEntity().equals(TypesOfEntities.CLASS)) {
				
				feedBack.add((ClassNode) node);
			
			}
			
		}
	
		return feedBack;
	}
	
	/**
     * Verify if a <code>ModuleNode</code> Cannot depend On another <code>ModuleNode</code>
	 * @param fullQualifiedNameModuleCallee The name of Module
	 * @return If depends
	 */
	public boolean cannotDependOn(String fullQualifiedNameModule) {
		boolean feedBack = true;
		for (ModuleNode module: getCalleeModules()){
			if (module.getName().equals(fullQualifiedNameModule)) {
				feedBack = false;
			}
		}
		return feedBack;
	}
	
	/**
     * Verify if a <code>ModuleNode</code> Cannot depend On another <code>ModuleNode</code>
	 * @param fullQualifiedNameModuleCallee The name of Module
	 * @return If depends
	 */
	public boolean cannotDependOn(ModuleNode module) {
		return cannotDependOn(module.getName());
	}

	/**
     * Verify if a <code>ModuleNode</code> Depend On another <code>ModuleNode</code>
	 * @param fullQualifiedNameModuleCallee The name of Module
	 * @return If depends
	 */
	public boolean dependOn(String fullQualifiedNameModule) {
		return !cannotDependOn(fullQualifiedNameModule);
	}
	
	/**
     * Verify if a <code>ModuleNode</code> Depend On another <code>ModuleNode</code>
	 * @param fullQualifiedNameModuleCallee The name of Module
	 * @return If depends
	 */
	public boolean dependOn(ModuleNode module) {
		return dependOn(module.getName());
	}
	
	/**
	 * @return The <code>ModuleNode</code> represented by this Module.
	 */
	public ModuleNode getModule() {
		return this;
	}

	/**
	 * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the classes that are referenced by the classes inside the module represented by this <code>ModuleNode</code>.
	 */
	public Set<ModuleNode> getCalleeModules() {
		Set<ModuleNode> feedBack = new HashSet<ModuleNode>();
		
		for(Relation relation: getRelations(TypesOfRelation.MODULE_OF)) {
			ClassNode classe = (ClassNode) relation.getCalledEntity();
			for(ClassNode calledClass: classe.getCalleeClasses()) {
				if (calledClass.getModule() != null) {
					feedBack.add(calledClass.getModule());
				}
			}
		}
	
		return feedBack;
	}
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>ModuleNode</code> objects reflecting all
	 * the classes that reference the module represented by this <code>ModuleNode</code>.
	 */
	public Set<ModuleNode> getCallerModules() {		
		Set<ModuleNode> feedBack = new HashSet<ModuleNode>();
		
		for(Relation relation: getRelations(TypesOfRelation.MODULE_OF)) {
			ClassNode classe = (ClassNode) relation.getCalledEntity();
			for(ClassNode calledClass: classe.getCallerClasses()) {
				if (calledClass.getModule() != null) {
					feedBack.add(calledClass.getModule());
				}
			}
		}
	
		return feedBack;
	}
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the classes that reference the module represented by this <code>ModuleNode</code>.
	 */
	@Override
	public Set<ClassNode> getCallerClasses() {
		
		Set<ClassNode> classes = this.getAllClasses();
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		
		for(Relation relation: getRelations(TypesOfRelation.MODULE_OF)) {
			ClassNode classe = (ClassNode) relation.getCalledEntity();
			feedBack.addAll(classe.getCallerClasses());
		}
	
		return feedBack;
	
	}

	/**
	 * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the classes that are referenced by the classes inside the module represented by this <code>ModuleNode</code>.
	 */
	@Override
	public Set<ClassNode> getCalleeClasses() {
		
		Set<ClassNode> classes = this.getAllClasses();
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		
		for(Relation relation: getRelations(TypesOfRelation.MODULE_OF)) {
			ClassNode classe = (ClassNode) relation.getCalledEntity();
			feedBack.addAll(classe.getCalleeClasses());
		}
	
		return feedBack;
	
	}

	/**
	 * Returns a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
	 * the methods that reference the classes or interfaces inside the module represented by this <code>ModuleNode</code>.
	 */
	@Override
	public Set<MethodNode> getCallerMethods() {
	
		Set<ClassNode> classes = this.getAllClasses();
		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		
		for(ClassNode classNode: classes) {
		
			feedBack.addAll(classNode.getCallerMethods());
		
		}
	
		return feedBack;
	
	}
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
	 * the methods that referenced by the classes or interfaces inside the module represented by this <code>ModuleNode</code>.
	 */
	@Override
	public Set<MethodNode> getCalleeMethods() {
		
		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		Set<ClassNode> allClasses = this.getAllClasses();
		
		for (ClassNode c : allClasses) {
			
			feedBack.addAll(c.getCalleeMethods());
		}
		
		return feedBack;

	}
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>PackageNode</code> objects reflecting all
	 * the packages that reference the module represented by this <code>PackageNode</code>.
	 * @return a <code>java.util.Set</code> containing <code>PackageNode</code> objects reflecting all
	 * the packages that reference the module represented by this <code>PackageNode</code>.
	 */
	public Set<PackageNode> getCallerPackages() {
		
		Set<MethodNode> callers = this.getCallerMethods();
		Set<PackageNode> returnValue = new HashSet<PackageNode>();
		
		for (MethodNode m : callers) {
			returnValue.add(m.getPackage());
		}
		//FIXME lembrar dessa mudança: um pacote chama ele mesmo!
		returnValue.remove(this);
		
		return returnValue;
	
	}

	/**
	 * Returns a <code>java.util.Set</code> containing <code>PackageNode</code> objects reflecting all
	 * the packages that are referenced by the module represented by this <code>PackageNode</code>.
	 * @return a <code>java.util.Set</code> containing <code>PackageNode</code> objects reflecting all
	 * the packages that are referenced by the module represented by this <code>PackageNode</code>.
	 */
	public Set<PackageNode> getCalleePackages() {
		
		Set<ClassNode> called = this.getCalleeClasses();
		Set<PackageNode> returnValue = new HashSet<PackageNode>();
		
		for (ClassNode c : called) {
			returnValue.add(c.getPackage());
		}
		
		//FIXME lembrar dessa mudança: um pacote chama ele mesmo!
		returnValue.remove(this);
		
		return returnValue;
		
	}
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
	 * the methods inside the module represented by this <code>PackageNode</code>.
	 * @return a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
	 * the methods inside the module represented by this <code>PackageNode</code>.
	 */
	public Set<MethodNode> getAllMethods() {
		
		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		
		for(ClassNode classInsidePackage : this.getAllClasses())
			feedBack.addAll(classInsidePackage.getAllMethods());
		
		return feedBack;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	public boolean equals(Object other) {
		
		if (!(other instanceof ModuleNode)) return false;
		
		ModuleNode aux = (ModuleNode) other;
		
		return this.name.equals(aux.name);
		
	}

	@Override
	public ClassNode getClassNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String[]> getImpactOfAChange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public PackageNode getPackage() {
		// TODO Auto-generated method stub
		return null;
	}

}
