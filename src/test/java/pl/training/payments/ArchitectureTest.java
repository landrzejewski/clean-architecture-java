package pl.training.payments;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameMatching;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ArchitectureTest {

    @Test
    void layer_dependencies_are_respected() {
        var classes = new ClassFileImporter()
                .withImportOption(new DoNotIncludeTests())
                .importPackages("pl.training.payments");

        /*noClasses().that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat().resideInAPackage("..application..")
                .check(classes);*/

        var arch = layeredArchitecture().consideringAllDependencies()
                .layer("Adapters").definedBy("pl.training.payments.adapters..")
                .layer("Ports").definedBy("pl.training.payments.ports..")
                .layer("Application").definedBy("pl.training.payments.application..")
                .layer("Domain").definedBy("pl.training.payments.domain..")

                .whereLayer("Adapters")
                    .mayNotBeAccessedByAnyLayer()
                    .ignoreDependency(nameMatching(".*Configuration.*"), alwaysTrue())
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Adapters", "Ports");

        arch.check(classes);
    }

}
