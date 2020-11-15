package com.example.hackernewschecker.usecase.repository.impl

import com.example.hackernewschecker.usecase.repository.api.HackerNewsApi
import com.example.hackernewschecker.usecase.repository.database.HackerNewsDatabase
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.test.assertEquals

class RepositoryImplTest : Spek({
    lateinit var database: HackerNewsDatabase
    lateinit var retrofit: Retrofit
    lateinit var hackerNewsApi: HackerNewsApi
    lateinit var loadCurrentNewsIdListCall: Call<List<Int>>
    lateinit var loadCurrentNewsIdListResponse: Response<List<Int>>

    beforeEachTest {
        database = mockk()
        retrofit = mockk()
        hackerNewsApi = mockk()
        loadCurrentNewsIdListCall = mockk()
        loadCurrentNewsIdListResponse = mockk()

        every {
            retrofit.create(HackerNewsApi::class.java)
        } returns hackerNewsApi
    }

    afterEachTest {
        unmockkAll()
    }

    describe("#loadCurrentNewsIdList") {
        it("Success") {
            val testList = listOf(1, 2, 3)
            coEvery {
                hackerNewsApi.loadCurrentNewsIdList()
            } returns testList

            val repository = RepositoryImpl(database, retrofit)

            val result = runBlocking {
                repository.loadCurrentNewsIdList()
            }

            assertEquals(testList, result)

            coVerify {
                hackerNewsApi.loadCurrentNewsIdList()
            }
        }
    }
})