package com.example.colours_ao_test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.colours_ao_test.online_repo.OnlineWordRepository
import com.example.colours_ao_test.view_model.ColoursActivityViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ColoursActivityViewModelUnitTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesRule = MainCoroutinesRule()


    @MockK
    private lateinit var onlineWordRepository: OnlineWordRepository

    private lateinit var viewModel : ColoursActivityViewModel

    @SpyK
    var loadingObserver = Observer<ColoursActivityViewModel.LoadingState>{ }

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = ColoursActivityViewModel(onlineWordRepository)

        viewModel.loadingState.observeForever(loadingObserver)
    }

    @Test
    fun ` when a list of words recieved the list,  shown the loading state is success`() {
        coEvery {
            onlineWordRepository.getRandomNames()
        } returns listOf()

        //when
        viewModel.getWords()

        //then
        Assert.assertTrue(viewModel.loadingState.value is ColoursActivityViewModel.LoadingState.OnSuccess)
        verify { loadingObserver.onChanged(any()) }
    }

    @Test
    fun `when getRandom names and on failure is called `() {

        //when
        viewModel.getWords()

        //then
        Assert.assertTrue(viewModel.loadingState.value is ColoursActivityViewModel.LoadingState.OnFailure)
        verify { loadingObserver.onChanged(any()) }
    }
}
