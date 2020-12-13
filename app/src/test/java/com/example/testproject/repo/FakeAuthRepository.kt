package com.example.testproject.repo

import com.example.testproject.model.User

class FakeAuthRepository: AuthRepository {

    private val userList = mutableListOf<User>()

    override suspend fun findByEmail(email: String): User? {
        userList.forEach {user ->
            if(user.email == email) return user
        }
        return null
    }

    override suspend fun saveUserData(user: User): Long {
        userList.add(user)
        return (userList.size - 1).toLong()
    }

    override suspend fun findById(userId: Int): User {
        userList.forEach {user ->
            if(user.id == userId) return user
        }
        return User(0, "default@gmail.com", "phone", "password")
    }
}