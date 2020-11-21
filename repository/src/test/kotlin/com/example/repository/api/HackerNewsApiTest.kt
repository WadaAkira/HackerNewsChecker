package com.example.repository.api

import com.example.repository.repository.api.HackerNewsApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HackerNewsApiTest : Spek({
    lateinit var mockWebServer: MockWebServer
    lateinit var api: HackerNewsApi

    val testNewsId = 10

    beforeEachTest {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(
                OkHttpClient()
                    .newBuilder()
                    .build()
            )
            .build()
            .create(HackerNewsApi::class.java)
    }

    afterEachTest {
        mockWebServer.shutdown()
    }

    describe("#loadCurrentNewsIdList") {
        it("Success") {
            val testFirstValue = 1
            val testSecondValue = 2
            val testThirdValue = 3
            val testList = listOf(testFirstValue, testSecondValue, testThirdValue)

            val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody("[$testFirstValue,$testSecondValue,$testThirdValue]")
            mockWebServer.enqueue(response)

            val result = runBlocking {
                api.loadCurrentNewsIdList()
            }

            assertTrue { result.isNotEmpty() }
            assertTrue { result.size == testList.size }
            assertEquals(testList[0], result[0])
            assertEquals(testList[1], result[1])
            assertEquals(testList[2], result[2])
        }
        it("Failed") {
            val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
            mockWebServer.enqueue(response)

            val isCaught = try {
                runBlocking {
                    api.loadCurrentNewsIdList()
                }
                false
            } catch (e: HttpException) {
                e.code() == HttpURLConnection.HTTP_INTERNAL_ERROR
            }

            assertTrue { isCaught }
        }
    }

    describe("#loadNews") {
        it("Success") {
            val testBy = "TestBy"
            val testDescendants = 10
            val testId = 11111111
            val testScore = 100
            val testTime = 1111111111
            val testTitle = "TestTitle"
            val testType = "TestType"
            val testUrl = "TestUrl"

            val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody("""{"by":"$testBy","descendants":$testDescendants,"id":$testId,"score":$testScore,"time":$testTime,"title":"$testTitle","type":"$testType","url":"$testUrl"}""")
            mockWebServer.enqueue(response)

            val result = runBlocking {
                api.loadNews(testNewsId)
            }

            assertEquals(testBy, result.by)
            assertEquals(testDescendants, result.descendants)
            assertEquals(testId, result.id)
            assertEquals(testScore, result.score)
            assertEquals(testTime, result.time)
            assertEquals(testTitle, result.title)
            assertEquals(testType, result.type)
            assertEquals(testUrl, result.url)
        }
        it("Failed") {
            val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            mockWebServer.enqueue(response)

            val isCaught = try {
                runBlocking {
                    api.loadNews(testNewsId)
                }
                false
            } catch (e: HttpException) {
                e.code() == HttpURLConnection.HTTP_BAD_REQUEST
            }

            assertTrue { isCaught }
        }
    }
})