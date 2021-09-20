package uk.com.ers;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("uk.com.ers");

        noClasses()
            .that()
            .resideInAnyPackage("uk.com.ers.service..")
            .or()
            .resideInAnyPackage("uk.com.ers.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..uk.com.ers.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
