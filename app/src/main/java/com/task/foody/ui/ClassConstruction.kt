package com.task.foody.ui

import android.content.Context
import com.task.foody.ui.database.FoodyDao
import com.task.foody.ui.database.FoodyDataBase
import com.task.foody.ui.repository.FoodyRepository
import com.task.foody.ui.repository.FoodyRepositoryImpl

object ClassConstruction {

    var foodyRepository: FoodyRepository? = null

    fun getFoodyRepository(
        context: Context
    ): FoodyRepository {
        if(foodyRepository == null)
            foodyRepository = FoodyRepositoryImpl(
                foodyDao = getFoodyDao(context)
            )
        return foodyRepository!!
    }

    fun getFoodyDataBase(context: Context): FoodyDataBase {
        return FoodyDataBase.getInstance(context)
    }

    fun getFoodyDao(context: Context): FoodyDao {
        return getFoodyDataBase(context).FoodyDao()
    }

}