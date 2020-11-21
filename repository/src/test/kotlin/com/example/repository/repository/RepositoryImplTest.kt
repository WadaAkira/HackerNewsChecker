package com.example.repository.repository

import com.example.dto.News
import com.example.repository.repository.api.HackerNewsApi
import com.example.repository.repository.database.HackerNewsDao
import com.example.repository.repository.database.HackerNewsDatabase
import com.example.repository.repository.impl.RepositoryImpl
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import retrofit2.Retrofit
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RepositoryImplTest : Spek({
    lateinit var database: HackerNewsDatabase
    lateinit var retrofit: Retrofit
    lateinit var hackerNewsApi: HackerNewsApi
    lateinit var hackerNewsDao: HackerNewsDao
    lateinit var news: News

    val testNewsId = 10
    val exception = Exception("Test failed.")

    beforeEachTest {
        database = mockk()
        retrofit = mockk()
        hackerNewsApi = mockk()
        hackerNewsDao = mockk()
        news = mockk()

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

            val repository =
                RepositoryImpl(
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

            val repository =
                RepositoryImpl(
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
            } returns news

            val repository =
                RepositoryImpl(
                    database,
                    retrofit
                )

            val result = runBlocking {
                repository.loadNews(testNewsId)
            }

            assertEquals(news, result)

            coVerify {
                hackerNewsApi.loadNews(testNewsId)
            }
        }
        it("Failed") {
            coEvery {
                hackerNewsApi.loadNews(testNewsId)
            } throws exception

            val repository =
                RepositoryImpl(
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
                hackerNewsDao.insert(news)
            } returns Unit

            val repository =
                RepositoryImpl(
                    database,
                    retrofit
                )

            runBlocking {
                repository.insertNews(news)
            }

            coVerify {
                hackerNewsDao.insert(news)
            }
        }
        it("Failed") {
            coEvery {
                hackerNewsDao.insert(news)
            } throws exception

            val repository =
                RepositoryImpl(
                    database,
                    retrofit
                )

            val isCaught = try {
                runBlocking {
                    repository.insertNews(news)
                }

                false
            } catch (e: Throwable) {
                true
            }

            assertTrue { isCaught }

            coVerify {
                hackerNewsDao.insert(news)
            }
        }
    }

    describe("#loadHistoryList") {
        it("Success") {
            val testList = listOf(news, news, news)
            coEvery {
                hackerNewsDao.loadList()
            } returns testList

            val repository =
                RepositoryImpl(
                    database,
                    retrofit
                )

            val result = runBlocking {
                repository.loadHistoryList()
            }

            assertEquals(testList, result)

            coVerify {
                hackerNewsDao.loadList()
            }
        }
        it("Failed") {
            coEvery {
                hackerNewsDao.loadList()
            } throws exception

            val repository =
                RepositoryImpl(
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
                hackerNewsDao.delete(news)
            } returns Unit

            val repository =
                RepositoryImpl(
                    database,
                    retrofit
                )

            runBlocking {
                repository.deleteHistory(news)
            }

            coVerify {
                hackerNewsDao.delete(news)
            }
        }
        it("Failed") {
            coEvery {
                hackerNewsDao.delete(news)
            } throws exception

            val repository =
                RepositoryImpl(
                    database,
                    retrofit
                )

            val isCaught = try {
                runBlocking {
                    repository.deleteHistory(news)
                }
                false
            } catch (e: Throwable) {
                true
            }

            assertTrue { isCaught }

            coVerify {
                hackerNewsDao.delete(news)
            }
        }
    }
})