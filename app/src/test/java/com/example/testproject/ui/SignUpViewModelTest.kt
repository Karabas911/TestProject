package com.example.testproject.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testproject.MainCoroutineRule
import com.example.testproject.getOrAwaitValueTest
import com.example.testproject.model.User
import com.example.testproject.prefs.FakePrefs
import com.example.testproject.repo.FakeAuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SignUpViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SignUpViewModel

    private lateinit var repository: FakeAuthRepository

    @Before
    fun setUp() {
        repository = FakeAuthRepository()
        viewModel = SignUpViewModel(repository, FakePrefs())
    }

    @Test
    fun `user doesn't provide Email, error ERROR_REASON_EMAIL`() {
        viewModel.registerNewUser("", "12345", "password", "password")
        val result = viewModel.getSighUpEvent().getOrAwaitValueTest()
        assert(result.status == Status.ERROR
                && result.errorReason == SignUpViewModel.ERROR_REASON_EMAIL)
    }

    @Test
    fun `user doesn't provide valid Email, error ERROR_REASON_EMAIL`() {
        viewModel.registerNewUser("dfsgsdfgdsfgdsfg", "12345", "password", "password")
        val result = viewModel.getSighUpEvent().getOrAwaitValueTest()
        assert(result.status == Status.ERROR
                && result.errorReason == SignUpViewModel.ERROR_REASON_EMAIL)
    }

    @Test
    fun `user with provided Email is registered, error ERROR_REASON_EMAIL`()  = runBlockingTest {
        val user = User(1,"test@gmail.com", "12345", "password")
        repository.saveUserData(user)
        viewModel.registerNewUser("test@gmail.com", "12345", "password", "password")
        val result = viewModel.getSighUpEvent().getOrAwaitValueTest()
        assert(result.status == Status.ERROR
                && result.errorReason == SignUpViewModel.ERROR_REASON_EMAIL)
    }

    @Test
    fun `user doesn't provide Phone, error ERROR_REASON_PHONE`() {
        viewModel.registerNewUser("test@gmail.com", "", "password", "password")
        val result = viewModel.getSighUpEvent().getOrAwaitValueTest()
        assert(result.status == Status.ERROR
                && result.errorReason == SignUpViewModel.ERROR_REASON_PHONE)
    }
}