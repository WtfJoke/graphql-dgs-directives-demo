package com.github.wtfjoke.dgsDirectivesExamples.graphql.mutations

import com.github.wtfjoke.generated.types.Rating
import com.github.wtfjoke.generated.types.RatingInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument


@DgsComponent
class RatingMutation {

    @DgsMutation
    fun addRating(@InputArgument ratingInput: RatingInput): Rating? {
        val stars = ratingInput.stars
        require(stars >= 1) { "Stars must be 1-5" }
        val title = ratingInput.title
        println("Rated $title with $stars stars")
        return Rating(stars)
    }
}