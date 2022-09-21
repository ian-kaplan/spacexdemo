package com.example.spacex

import com.example.spacex.launchFeed.LaunchFeedAction
import com.example.spacex.launchFeed.LaunchFeedInteractor
import com.example.spacex.launchFeed.LaunchFeedRepo
import com.example.spacex.launchFeed.LaunchFeedResult
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LaunchFeedInteractorTest {

    val repo = mock<LaunchFeedRepo>()
    var interactor = LaunchFeedInteractor(repo)
    lateinit var testObserver: TestObserver<LaunchFeedResult>

    @Before
    fun setup() {
        val resultRelayTest: PublishRelay<LaunchFeedResult> =
            PublishRelay.create<LaunchFeedResult>()
        val actionRelayTest: PublishRelay<LaunchFeedAction> =
            PublishRelay.create<LaunchFeedAction>()
        val actionStreamTest = actionRelayTest.hide()

        interactor.actionStream = actionStreamTest
        interactor.resultsRelay = resultRelayTest
        testObserver = interactor.resultsRelay.test()
    }

    @Test
    fun addition_isCorrect() {

        /* whenever(repo.()).thenReturn(Single.just(listOf(
         ))))*/
        interactor.start()

        //testObserver.assertValues(GiphyFeedLoadingResult, GiphyFeedLoadedResult(listOf()))

        testObserver.values()
    }
}