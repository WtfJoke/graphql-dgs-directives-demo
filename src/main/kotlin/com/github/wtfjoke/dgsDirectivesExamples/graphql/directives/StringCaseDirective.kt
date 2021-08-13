package com.github.wtfjoke.dgsDirectivesExamples.graphql.directives

import com.github.wtfjoke.generated.types.Case
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsRuntimeWiring
import graphql.schema.DataFetcherFactories
import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaDirectiveWiring
import graphql.schema.idl.SchemaDirectiveWiringEnvironment
import java.util.function.BiFunction


@DgsComponent
class StringCaseDirective : SchemaDirectiveWiring {

    companion object {
        private const val NAME = "StringCase"
        private const val ARGUMENT_NAME = "case"
    }

    @DgsRuntimeWiring
    fun runtimeWiring(builder: RuntimeWiring.Builder): RuntimeWiring.Builder {
        return builder.directiveWiring(StringCaseDirective())
    }

    override fun onField(env: SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition>): GraphQLFieldDefinition {
        val field = env.element
        if (field.getDirective(NAME) == null) {
            return field
        }
        val casingAsString = field.getDirective(NAME).getArgument(ARGUMENT_NAME).value as String
        val casing = Case.valueOf(casingAsString)

        val parentType = env.fieldsContainer

        // build a data fetcher that transforms the given value to uppercase/lowercase
        val originalFetcher = env.codeRegistry.getDataFetcher(parentType, field)
        val dataFetcher = DataFetcherFactories.wrapDataFetcher(originalFetcher, transformFieldValue(casing))

        // now change the field definition to use the new uppercase/lowercase data fetcher
        env.codeRegistry.dataFetcher(parentType, field, dataFetcher)
        return field
    }

    fun transformFieldValue(casing: Case): BiFunction<DataFetchingEnvironment, Any, Any> {
        val casingTransformer = { _: DataFetchingEnvironment, value: Any ->
            var casedValue = value
            if (value is String) {
                casedValue = when (casing) {
                    Case.LOWER -> value.lowercase()
                    Case.UPPER -> value.uppercase()
                }
            }
            casedValue
        }

        return BiFunction(casingTransformer)
    }
}