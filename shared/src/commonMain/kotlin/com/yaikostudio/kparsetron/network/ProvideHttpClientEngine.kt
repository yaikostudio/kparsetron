package com.yaikostudio.kparsetron.network

import io.ktor.client.engine.HttpClientEngine

expect fun provideHttpClientEngine(): HttpClientEngine
