package com.sunil.app.data.mapper

import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.domain.entity.movies.MovieEntity


/**
 * Extension function to convert a MovieEntity to a MovieDbData.
 *
 * This function takes a [MovieEntity] object and maps its properties to a new [MovieDbData] object.
 * This is typically used when you need to store movie data in the local database.
 *
 * @receiver The [MovieEntity] object to be converted.
 * @return A new[MovieDbData] object with the mapped properties.
 */
fun MovieEntity.toMovieDbData(): MovieDbData = MovieDbData(
    id = this.id, // Map the id from MovieEntity to MovieDbData
    imageUrl = this.imageUrl, // Map the imageUrl from MovieEntity to MovieDbData
    description = this.description, // Map the description from MovieEntity to MovieDbData
    title = this.title, // Map the title from MovieEntity to MovieDbData
    category = this.category, // Map the category from MovieEntity to MovieDbData
    backgroundImageUrl = this.backgroundImageUrl // Map the backgroundImageUrl from MovieEntity to MovieDbData
)

/**
 * Extension function to convert a MovieDbData to a MovieEntity.
 *
 * This function takes a [MovieDbData] object and maps its properties to a new [MovieEntity] object.
 * This is typically used when you retrieve movie data from the local database and need to use it
 * in the domain layer.
 *
 * @receiver The [MovieDbData] object to be converted.
 * @return A new [MovieEntity] object with the mapped properties.
 */
fun MovieDbData.toMovieEntity(): MovieEntity = MovieEntity(
    id = this.id, // Map the id from MovieDbData to MovieEntity
    imageUrl = this.imageUrl, // Map the imageUrl from MovieDbData to MovieEntity
    description = this.description, // Map the description from MovieDbData to MovieEntity
    title = this.title, // Map the title from MovieDbData to MovieEntity
    category = this.category, // Map the category from MovieDbData to MovieEntity
    backgroundImageUrl = this.backgroundImageUrl // Map the backgroundImageUrl from MovieDbData to MovieEntity
)
