package com.shawn.mvvm_cathybk.home

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.shawn.mvvm_cathybk.main.home.HomePagingSource
import com.shawn.mvvm_cathybk.main.home.HomeViewModel
import com.shawn.network.model.*
import com.shawn.network.repository.AttractionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Flow

@OptIn(ExperimentalCoroutinesApi::class)
class homeUnitTest {
    val mockRepository = mockk<AttractionRepository>()


    // 創建測試用的 Attractions
    val attraction1 = mockk<Attraction>(relaxed = true)
    val attraction2 = mockk<Attraction>(relaxed = true)
    val attraction3 = mockk<Attraction>(relaxed = true)

    // Replace the default Main dispatcher with a test one
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `load returns expected results`() = runTest {
        // Mock the backend response
        val mockBackend = mockk<AttractionRepository>()
        val mockResponse = listOf(attraction1, attraction2, attraction3)
        coEvery { mockBackend.service.getAttractions(any(), any()) } returns AttractionModel(
            "",
            mockResponse
        )

        // Create the paging source and load data
        val pagingSource = HomePagingSource(mockBackend, "en")
        val params = PagingSource.LoadParams.Refresh<Int>(null, 2, false)
        val result = pagingSource.load(params)

        // Verify the result
        assertTrue(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page<Int, Attraction>
        assertEquals(null, pageResult.prevKey)
        assertEquals(2, pageResult.nextKey)
        assertEquals(mockResponse.size, pageResult.data.size)
    }


    @Test
    fun `load returns error results`() = runTest {
        // Mock the backend response
        val mockBackend = mockk<AttractionRepository>()
        coEvery { mockBackend.service.getAttractions(any(), any()) } throws Exception("Something went wrong")


        // Create the paging source and load data
        val pagingSource = HomePagingSource(mockBackend, "en")
        val params = PagingSource.LoadParams.Refresh<Int>(null, 2, false)
        val result = pagingSource.load(params)

        // Verify the result
        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals("Something went wrong", (result as PagingSource.LoadResult.Error).throwable.message)
    }
}