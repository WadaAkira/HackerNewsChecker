package com.example.hackernewschecker.usecase.repository.impl

import com.example.hackernewschecker.usecase.repository.Repository

class RepositoryImpl():Repository {
    override suspend fun loadCurrentNewsIdList(): List<Int> {
        // https://hacker-news.firebaseio.com/v0/topstories.json
        TODO("Not yet implemented")
    }
}