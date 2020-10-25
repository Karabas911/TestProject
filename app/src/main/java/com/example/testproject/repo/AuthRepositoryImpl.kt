package com.example.testproject.repo

import com.example.testproject.database.UserDao
import com.example.testproject.model.User

class AuthRepositoryImpl(private val userDao: UserDao) : AuthRepository {

    override suspend fun findByEmail(email: String): User? {
        return userDao.findByEmail(email)
    }

}