# GraphQL DGS Directives Demo
Repo to demonstrate using directives in GraphQL with [DGS Framework](https://netflix.github.io/dgs/)


## Howto start

1. Execute `./gradlew bootRun` (or `gradlew.bat bootRun` in Windows)
2. Open [localhost:8080/graphiql](http://localhost:8080/graphiql)
3. Execute any query/mutation of your choice using [graphiql](https://github.com/graphql/graphiql) or click on any of the sample queries below :)

### Sample queries
- [Valid Rating](http://localhost:8080/graphiql?query=mutation%20%7B%0A%20%20addRating(rating%3A%20%7Btitle%3A%20%22Inception%22%2C%20stars%3A%205%7D)%7B%0A%20%20%20%20stars%0A%20%20%7D%0A%7D%0A)
- [Invalid stars rating](http://localhost:8080/graphiql?query=mutation%20%7B%0A%20%20addRating(rating%3A%20%7Btitle%3A%20%22Inception%22%2C%20stars%3A%2010%7D)%7B%0A%20%20%20%20stars%0A%20%20%7D%0A%7D%0A)
- [Invalid title while rating](http://localhost:8080/graphiql?query=mutation%20%7B%0A%20%20addRating(rating%3A%20%7Btitle%3A%20%22%22%2C%20stars%3A%205%7D)%7B%0A%20%20%20%20stars%0A%20%20%7D%0A%7D%0A)

## F.A.Q

>How are the directives registered?

Most directives (Range/NotBlank...) are from the library [graphql-java/graphql-java-extended-validation](https://github.com/graphql-java/graphql-java-extended-validation).  
They get registered with DGS in [AddExtendedValidationDirectiveWiring](src/main/kotlin/com/github/wtfjoke/dgsDirectivesExamples/graphql/directives/wirings/AddExtendedValidationDirectiveWiring.kt)

>Why can't I find the code for Movie/Rating?  

I use the [DGS Codegen Plugin](https://netflix.github.io/dgs/generating-code-from-schema/) to automatically generate them from the defined schema during the build.

>What do I need to do for a custom directive?  

You need to implement the interface `SchemaDirectiveWiring`, register it using `@DgsRuntimeWiring` and implement/override the method for each directiveLocation you implement (eg `onInputObjectType`, `onField` etc...)  

See [StringCaseDirective](src/main/kotlin/com/github/wtfjoke/dgsDirectivesExamples/graphql/directives/StringCaseDirective.kt) as an example of a custom directive.
