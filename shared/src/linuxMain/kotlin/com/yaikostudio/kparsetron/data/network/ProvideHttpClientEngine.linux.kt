package com.yaikostudio.kparsetron.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.curl.Curl

actual fun provideHttpClientEngine(): HttpClientEngine = Curl.create()
