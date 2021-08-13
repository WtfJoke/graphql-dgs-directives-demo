package com.github.wtfjoke.dgsDirectivesExamples.graphql.mutations

import com.github.wtfjoke.generated.types.Rating
import com.github.wtfjoke.generated.types.RatingInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument


@DgsComponent
class RatingMutation {

    @DgsMutation
    fun addRating(@InputArgument rating: RatingInput): Rating? {
        val stars = rating.stars
        println("Rated ${rating.title} with $stars stars")
        return Rating(stars)
    }
}