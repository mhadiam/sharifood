package com.task.foody.ui.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.foody.ui.database.dto.Account
import com.task.foody.ui.database.dto.Comment
import com.task.foody.ui.database.dto.CustomerLikeRestaurant
import com.task.foody.ui.database.dto.CustomerMessageRestaurant
import com.task.foody.ui.database.dto.Food

@Dao
interface FoodyDao {

    @Insert
    suspend fun addAccount(account: Account): Long

    @Query("Select * From Account")
    suspend fun getAllAccounts(): List<Account>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLike(customerLikeRestaurant: CustomerLikeRestaurant): Long

    @Delete
    suspend fun deleteLike(customerLikeRestaurant: CustomerLikeRestaurant)

    @Query("Select * From CustomerLikeRestaurant")
    suspend fun getLikedRestaurants(): List<CustomerLikeRestaurant>

    @Insert
    suspend fun addMessageForRestaurant(messageRestaurant: CustomerMessageRestaurant): Long

    @Query("Select * From CustomerMessageRestaurant")
    suspend fun getAllMessageForRestaurants(): List<CustomerMessageRestaurant>

    @Insert
    suspend fun addFood(food: Food): Long

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("Select * From Food")
    suspend fun getFoods(): List<Food>

    @Insert
    suspend fun addComment(comment: Comment): Long

    @Query("Select * From Comment")
    suspend fun getComments(): List<Comment>

}