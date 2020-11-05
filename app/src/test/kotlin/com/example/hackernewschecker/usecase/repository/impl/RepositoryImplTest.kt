package com.example.hackernewschecker.usecase.repository.impl

import com.example.hackernewschecker.usecase.repository.api.HackerNewsApi
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class RepositoryImplTest : Spek({
    lateinit var retrofit: Retrofit
    lateinit var hackerNewsApi: HackerNewsApi
    lateinit var loadCurrentNewsIdListCall: Call<List<Int>>
    lateinit var loadCurrentNewsIdListResponse: Response<List<Int>>

    beforeEachTest {
        retrofit = mockk()
        hackerNewsApi = mockk()
        loadCurrentNewsIdListCall = mockk()
        loadCurrentNewsIdListResponse = mockk()
    }

    afterEachTest {
        unmockkAll()
    }

    describe("#loadCurrentNewsIdList") {
        it("Success") {
            val testList = listOf(
                1, 2, 3
            )

            runBlocking {

            }
        }
    }
})