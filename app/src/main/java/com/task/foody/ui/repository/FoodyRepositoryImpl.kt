package com.task.foody.ui.repository

import com.task.foody.ui.database.FoodyDao
import com.task.foody.ui.database.dto.Account
import com.task.foody.ui.database.dto.Comment
import com.task.foody.ui.database.dto.CustomerLikeRestaurant
import com.task.foody.ui.database.dto.CustomerMessageRestaurant
import com.task.foody.ui.database.dto.Food

class FoodyRepositoryImpl(
    private val foodyDao: FoodyDao
): FoodyRepository {

    override suspend fun addAccount(account: Account): Long {
        return foodyDao.addAccount(account)
    }

    override suspend fun getAllAccounts(): List<Account> {
        return foodyDao.getAllAccounts()
    }

    override suspend fun addLike(likeRestaurant: CustomerLikeRestaurant): Long {
        return foodyDao.addLike(likeRestaurant)
    }

    override suspend fun deleteLike(likeRestaurant: CustomerLikeRestaurant) {
        foodyDao.deleteLike(likeRestaurant)
    }

    override suspend fun getLikedRestaurants(): List<CustomerLikeRestaurant> {
        return foodyDao.getLikedRestaurants()
    }

    override suspend fun addMessageForRestaurant(messageRestaurant: CustomerMessageRestaurant): Long {
        return foodyDao.addMessageForRestaurant(messageRestaurant)
    }

    override suspend fun getAllMessageForRestaurants(): List<CustomerMessageRestaurant> {
        return foodyDao.getAllMessageForRestaurants()
    }

    override suspend fun addFood(food: Food): Long {
        return foodyDao.addFood(food)
    }

    override suspend fun deleteFood(food: Food) {
        foodyDao.deleteFood(food)
    }

    override suspend fun getFoods(): List<Food> {
        return foodyDao.getFoods()
    }

    override suspend fun addComment(comment: Comment): Long {
        return foodyDao.addComment(comment)
    }

    override suspend fun getComments(): List<Comment> {
        return foodyDao.getComments()
    }


}