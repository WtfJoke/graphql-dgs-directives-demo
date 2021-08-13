package com.github.wtfjoke.dgsDirectivesExamples.graphql.directives.wirings

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsRuntimeWiring
import graphql.schema.idl.RuntimeWiring
import graphql.validation.rules.OnValidationErrorStrategy
import graphql.validation.rules.ValidationRules
import graphql.validation.schemawiring.ValidationSchemaWiring

@DgsComponent
class AddExtendedValidationDirectiveWiring {
    @DgsRuntimeWiring
    fun addGraphqlJavaExtendedValidationDirectives(builder: RuntimeWiring.Builder): RuntimeWiring.Builder {
        // We register all the directives (see directives.graphqls) of graphql-java-extended-validation and let dgs know
        val validationRules: ValidationRules = ValidationRules.newValidationRules()
                .onValidationErrorStrategy(OnValidationErrorStrategy.RETURN_NULL)
                .build()

        // This will rewrite your data fetchers when rules apply to them so that they get validated
        val schemaWiring = ValidationSchemaWiring(validationRules)
        return builder.directiveWiring(schemaWiring)
    }
}