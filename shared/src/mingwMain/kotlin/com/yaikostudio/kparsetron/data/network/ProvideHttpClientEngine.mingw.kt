package com.yaikostudio.kparsetron.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.winhttp.WinHttp

actual fun provideHttpClientEngine(): HttpClientEngine = WinHttp.create()
