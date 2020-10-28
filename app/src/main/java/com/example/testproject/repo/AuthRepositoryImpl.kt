package com.example.testproject.repo

import com.example.testproject.database.UserDao
import com.example.testproject.model.User

class AuthRepositoryImpl(private val userDao: UserDao) : AuthRepository {

    override suspend fun findByEmail(email: String): User? {
        return userDao.findByEmail(email)
    }

    override suspend fun saveUserData(user: User): Long {
        return userDao.insert(user)
    }

    override suspend fun findById(userId: Int): User {
        return userDao.findById(userId)
    }

}