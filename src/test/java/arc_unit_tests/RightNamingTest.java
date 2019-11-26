package arc_unit_tests;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class RightNamingTest {
  private JavaClasses importedClasses = new ClassFileImporter().importPackages("com.somn");
  
  @Test
  void controllerNamingRule() {
    ArchRule controllerNamingRule = classes()
        .that()
        .resideInAPackage("..controller..")
        .and()
        .areAnnotatedWith(RestController.class)
        .should()
        .haveSimpleNameContaining("Controller");
    
    controllerNamingRule.check(importedClasses);
  }
  
  @Test
  void dtoNamingRule() {
    ArchRule dtoNamingRule = classes()
        .that()
        .resideInAPackage("..dto..")
        .should()
        .haveSimpleNameContaining("DTO");
  
    dtoNamingRule.check(importedClasses);
  }
  
  @Test
  void exceptionNamingRule() {
    ArchRule exceptionNamingRule = classes()
        .that()
        .resideInAPackage("..exception..")
        .should()
        .haveSimpleNameContaining("Exception");
  
    exceptionNamingRule.check(importedClasses);
  }
  
  @Test
  void mappersNamingRule() {
    ArchRule mappersNamingRule = classes()
        .that()
        .resideInAPackage("..mappers..")
        .should()
        .haveSimpleNameContaining("Mapper");
  
    mappersNamingRule.check(importedClasses);
  }
  
  @Test
  void modelNamingRule() {
    ArchRule modelNamingRule = classes()
        .that()
        .resideInAPackage("..model..")
        .should()
        .haveSimpleNameContaining("Entity");
  
    modelNamingRule.check(importedClasses);
  }
  
  @Test
  void repositoryNamingRule() {
    ArchRule modelNamingRule = classes()
        .that()
        .resideInAPackage("..repository..")
        .should()
        .haveSimpleNameContaining("Repository");
    
    modelNamingRule.check(importedClasses);
  }
  
  @Test
  void securityNamingRule() {
    ArchRule serviceNamingRule = classes()
        .that()
        .resideInAPackage("..security..")
        .should()
        .haveSimpleNameContaining("Security");
    
    serviceNamingRule.check(importedClasses);
  }
  
  @Test
  void serviceNamingRule() {
    ArchRule serviceNamingRule = classes()
        .that()
        .resideInAPackage("..service..")
        .should()
        .haveSimpleNameContaining("Service");
  
    serviceNamingRule.check(importedClasses);
  }
}
