package com.example.testproject.repo

import com.example.testproject.database.UserDao

class AuthRepositoryImpl(private val userDao: UserDao) : AuthRepository {

}