package com.task.foody.ui.repository

import com.task.foody.ui.database.dto.Account
import com.task.foody.ui.database.dto.Comment
import com.task.foody.ui.database.dto.CustomerLikeRestaurant
import com.task.foody.ui.database.dto.CustomerMessageRestaurant
import com.task.foody.ui.database.dto.Food

interface FoodyRepository {

    suspend fun addAccount(account: Account): Long

    suspend fun getAllAccounts(): List<Account>

    suspend fun addLike(likeRestaurant: CustomerLikeRestaurant): Long

    suspend fun deleteLike(likeRestaurant: CustomerLikeRestaurant)

    suspend fun getLikedRestaurants(): List<CustomerLikeRestaurant>

    suspend fun addMessageForRestaurant(messageRestaurant: CustomerMessageRestaurant): Long

    suspend fun getAllMessageForRestaurants(): List<CustomerMessageRestaurant>

    suspend fun addFood(food: Food): Long

    suspend fun deleteFood(food: Food)

    suspend fun getFoods(): List<Food>

    suspend fun addComment(comment: Comment): Long

    suspend fun getComments(): List<Comment>

}