package com.example.githubuser.data.remote

import com.example.githubuser.data.model.ResponseUserGithub
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GithubService {

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGithub(): MutableList<ResponseUserGithub.Item>
}