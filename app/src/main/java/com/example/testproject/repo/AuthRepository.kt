package com.example.testproject.repo

import com.example.testproject.model.User

interface AuthRepository {

    suspend fun findByEmail(email: String): User?

}