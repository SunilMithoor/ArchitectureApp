package com.sunil.app.data.mapper

import com.sunil.app.data.local.db.entity.movies.MovieDbData
import com.sunil.app.domain.entity.movies.MovieEntity


/**
 * Created by Sunil on 13/01/2025
 **/

fun MovieEntity.toDbData() = MovieDbData(
    id = id,
    image = image,
    description = description,
    title = title,
    category = category,
    backgroundUrl = backgroundUrl
)
