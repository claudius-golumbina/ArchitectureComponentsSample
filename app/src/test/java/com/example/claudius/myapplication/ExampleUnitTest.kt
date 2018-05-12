package com.example.claudius.myapplication

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *)<String>()(
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var mockServer: MockWebServer

    @Before
    fun setup() {
        mockServer = MockWebServer()
    }

    @Test
    fun httpTest() {
        mockServer.enqueue(MockResponse().setBody("hello world!"))
        mockServer.start()
        val baseUrl = mockServer.url("/chat")

        val client = OkHttpClient()
        val request = Request.Builder()
                .url(baseUrl)
                .build()
        val response = client.newCall(request).execute()

        assertThat(response.body()?.string(), equalTo("hello world!"))

        val recordedRequest = mockServer.takeRequest()
        assertThat(recordedRequest.path, equalTo("/chat"))
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}
