package arc_unit_tests;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class LayerTest {
  private JavaClasses importedClasses = new ClassFileImporter().importPackages("com.somn");
  
  @Test
  void simpleArchitectureLayersCheck() {
    Architectures.LayeredArchitecture architectures = layeredArchitecture()
        .layer("Controller").definedBy("..controller..")
        .layer("Service").definedBy("..service..")
        .layer("Mapper").definedBy("..mappers..")
        .layer("Repository").definedBy("..repository..")
    
        .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
        .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
        .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
        .whereLayer("Mapper").mayOnlyBeAccessedByLayers("Service");
    
    architectures.check(importedClasses);
  }
}
