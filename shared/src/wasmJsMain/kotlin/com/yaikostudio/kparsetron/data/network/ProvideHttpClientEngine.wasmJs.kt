package com.yaikostudio.kparsetron.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js

actual fun provideHttpClientEngine(): HttpClientEngine = Js.create()
