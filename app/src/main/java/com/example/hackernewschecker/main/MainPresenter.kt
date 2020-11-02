package com.example.hackernewschecker.main

import com.example.hackernewschecker.usecase.HackerNewsUseCase
import kotlinx.coroutines.Job
import javax.inject.Inject

class MainPresenter @Inject constructor(val useCase: HackerNewsUseCase) : MainContract.Presenter {
    private lateinit var view: MainContract.View
    private val jobList = mutableListOf<Job>()

    override fun setup(view: MainContract.View) {
        this.view = view
    }

    override fun loadPage() {
        TODO("Not yet implemented")
    }

    override fun openNewsSite(url: String) {
        view.transitNewsSite(url)
    }

    override fun stop() {
        jobList.forEach {
            it.cancel()
        }
    }
}