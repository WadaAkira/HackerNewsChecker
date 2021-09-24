package com.example.hackernewschecker.repository

import com.example.hackernewschecker.dto.News
import com.example.hackernewschecker.repository.api.HackerNewsApi
import com.example.hackernewschecker.repository.data.RepositoryNews
import com.example.hackernewschecker.repository.database.HackerNewsDao
import com.example.hackernewschecker.repository.database.HackerNewsDatabase
import com.example.hackernewschecker.repository.impl.RepositoryImpl
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import retrofit2.Retrofit
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RepositoryImplTest : Spek({
    lateinit var database: HackerNewsDatabase
    lateinit var retrofit: Retrofit
    lateinit var hackerNewsApi: HackerNewsApi
    lateinit var hackerNewsDao: HackerNewsDao

    val testNewsId = 10
    val testIntValue = 100
    val testString = "Test"
    val testRepositoryNews = RepositoryNews(
        id = testNewsId,
        by = testString,
        descendants = testIntValue,
        kids = listOf(testIntValue),
        score = testIntValue,
        time = testIntValue,
        title = testString,
        type = testString,
        url = testString
    )
    val testNews = News(
        id = testNewsId,
        by = testString,
        descendants = testIntValue,
        kids = listOf(testIntValue),
        score = testIntValue,
        time = testIntValue,
        title = testString,
        type = testString,
        url = testString
    )
    val exception = Exception("Test failed.")

    beforeEachTest {
        database = mockk()
        retrofit = mockk()
        hackerNewsApi = mockk()
        hackerNewsDao = mockk()

        every {
            retrofit.create(HackerNewsApi::class.java)
        } returns hackerNewsApi

        every {
            database.hackerNewsDao()
        } returns hackerNewsDao
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

            val repository = RepositoryImpl(
                database,
                retrofit
            )

            val result = runBlocking {
                repository.loadCurrentNewsIdList()
            }

            assertEquals(testList, result)

            coVerify {
                hackerNewsApi.loadCurrentNewsIdList()
            }
        }
        it("Failed") {
            coEvery {
                hackerNewsApi.loadCurrentNewsIdList()
            } throws exception

            val repository = RepositoryImpl(
                database,
                retrofit
            )

            val isCaught = try {
                runBlocking {
                    repository.loadCurrentNewsIdList()
                }

                false
            } catch (e: Throwable) {
                true
            }

            assertTrue { isCaught }

            coVerify {
                hackerNewsApi.loadCurrentNewsIdList()
            }
        }
    }

    describe("#loadNews") {
        it("Success") {
            coEvery {
                hackerNewsApi.loadNews(testNewsId)
            } returns testRepositoryNews

            val repository = RepositoryImpl(
                database,
                retrofit
            )

            val result = runBlocking {
                repository.loadNews(testNewsId)
            }

            assertEquals(testNewsId, result.id)
            assertEquals(testString, result.by)
            assertEquals(testIntValue, result.descendants)
            assertEquals(testIntValue, result.score)
            assertEquals(testIntValue, result.time)
            assertEquals(testString, result.title)
            assertEquals(testString, result.type)
            assertEquals(testString, result.url)

            val kids = result.kids
            assertNotNull(kids)
            assertTrue { kids.isNotEmpty() }
            assertTrue { kids.size == 1 }
            assertEquals(testIntValue, kids[0])

            coVerify {
                hackerNewsApi.loadNews(testNewsId)
            }
        }
        it("Failed") {
            coEvery {
                hackerNewsApi.loadNews(testNewsId)
            } throws exception

            val repository = RepositoryImpl(
                database,
                retrofit
            )

            val isCaught = try {
                runBlocking {
                    repository.loadNews(testNewsId)
                }

                false
            } catch (e: Throwable) {
                true
            }

            assertTrue { isCaught }

            coVerify {
                hackerNewsApi.loadNews(testNewsId)
            }
        }
    }

    describe("#insertNews") {
        it("Success") {
            coEvery {
                hackerNewsDao.insert(any() as RepositoryNews)
            } returns Unit

            val repository = RepositoryImpl(
                database,
                retrofit
            )

            runBlocking {
                repository.insertNews(testNews)
            }

            coVerify {
                hackerNewsDao.insert(any() as RepositoryNews)
            }
        }
        it("Failed") {
            coEvery {
                hackerNewsDao.insert(any() as RepositoryNews)
            } throws exception

            val repository = RepositoryImpl(
                database,
                retrofit
            )

            val isCaught = try {
                runBlocking {
                    repository.insertNews(testNews)
                }

                false
            } catch (e: Throwable) {
                true
            }

            assertTrue { isCaught }

            coVerify {
                hackerNewsDao.insert(any() as RepositoryNews)
            }
        }
    }

    describe("#loadHistoryList") {
        it("Success") {
            val testList = listOf(testRepositoryNews, testRepositoryNews, testRepositoryNews)
            coEvery {
                hackerNewsDao.loadList()
            } returns testList

            val repository = RepositoryImpl(
                database,
                retrofit
            )

            val result = runBlocking {
                repository.loadHistoryList()
            }

            assertTrue { result.isNotEmpty() }
            assertTrue { result.size == 3 }

            val item = result[0]
            assertEquals(testNewsId, item.id)
            assertEquals(testString, item.by)
            assertEquals(testIntValue, item.descendants)
            assertEquals(testIntValue, item.score)
            assertEquals(testIntValue, item.time)
            assertEquals(testString, item.title)
            assertEquals(testString, item.type)
            assertEquals(testString, item.url)

            val kids = item.kids
            assertNotNull(kids)
            assertTrue { kids.isNotEmpty() }
            assertTrue { kids.size == 1 }
            assertEquals(testIntValue, kids[0])

            coVerify {
                hackerNewsDao.loadList()
            }
        }
        it("Failed") {
            coEvery {
                hackerNewsDao.loadList()
            } throws exception

            val repository = RepositoryImpl(
                database,
                retrofit
            )

            val isCaught = try {
                runBlocking {
                    repository.loadHistoryList()
                }
                false
            } catch (e: Throwable) {
                true
            }

            assertTrue { isCaught }

            coVerify {
                hackerNewsDao.loadList()
            }
        }
    }

    describe("#deleteHistory") {
        it("Success") {
            coEvery {
                hackerNewsDao.delete(any() as RepositoryNews)
            } returns Unit

            val repository = RepositoryImpl(
                database,
                retrofit
            )

            runBlocking {
                repository.deleteHistory(testNews)
            }

            coVerify {
                hackerNewsDao.delete(any() as RepositoryNews)
            }
        }
        it("Failed") {
            coEvery {
                hackerNewsDao.delete(any() as RepositoryNews)
            } throws exception

            val repository = RepositoryImpl(
                database,
                retrofit
            )

            val isCaught = try {
                runBlocking {
                    repository.deleteHistory(testNews)
                }
                false
            } catch (e: Throwable) {
                true
            }

            assertTrue { isCaught }

            coVerify {
                hackerNewsDao.delete(any() as RepositoryNews)
            }
        }
    }
})