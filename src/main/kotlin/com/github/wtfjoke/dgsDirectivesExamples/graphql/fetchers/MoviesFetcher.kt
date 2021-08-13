package com.github.wtfjoke.dgsDirectivesExamples.graphql.fetchers

import com.github.wtfjoke.generated.types.Movie
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class MoviesFetcher {
    private val movies = listOf(
            Movie("Spider-Man: Into the Spider-Verse", 2018),
            Movie("Swiss Army Man", 2016),
            Movie("Inception", 2010),
            Movie("Tenet", 2020))

    @DgsQuery
    fun movies(@InputArgument titleFilter : String?): List<Movie> {
        return if(titleFilter != null) {
            movies.filter { it.title.contains(titleFilter) }
        } else {
            movies
        }
    }

}