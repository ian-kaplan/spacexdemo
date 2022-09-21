package com.example.spacex

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.ViewFinder
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.spacex.baseIslands.BaseIslandActivity
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Field


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    public val islandActivityTestRule =
        ActivityTestRule<BaseIslandActivity>(BaseIslandActivity::class.java)

    @Test
    fun load_from_repo() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        waitUntilViewIsDisplayed(withId(R.id.launches))
        onView(withId(R.id.launches)).check(matches(isDisplayed()))
    }
}

/**
 * @param viewMatcher The matcher to find the view.
 * @param idleMatcher The matcher condition to be fulfilled to be considered idle.
 */
class ViewIdlingResource(
    private val viewMatcher: Matcher<View?>?,
    private val idleMatcher: Matcher<View?>?
) : IdlingResource {

    private var resourceCallback: IdlingResource.ResourceCallback? = null

    /**
     * {@inheritDoc}
     */
    override fun isIdleNow(): Boolean {
        val view: View? = getView(viewMatcher)
        val isIdle: Boolean = idleMatcher?.matches(view) ?: false
        if (isIdle) {
            resourceCallback?.onTransitionToIdle()
        }
        return isIdle
    }

    /**
     * {@inheritDoc}
     */
    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = resourceCallback
    }

    /**
     * {@inheritDoc}
     */
    override fun getName(): String? {
        return "$this ${viewMatcher.toString()}"
    }

    /**
     * Tries to find the view associated with the given [<].
     */
    private fun getView(viewMatcher: Matcher<View?>?): View? {
        return try {
            val viewInteraction = onView(viewMatcher)
            val finderField: Field? = viewInteraction.javaClass.getDeclaredField("viewFinder")
            finderField?.isAccessible = true
            val finder = finderField?.get(viewInteraction) as ViewFinder
            finder.view
        } catch (e: Exception) {
            null
        }
    }

}

/**
 * Waits for a matching View or throws an error if it's taking too long.
 */
fun waitUntilViewIsDisplayed(matcher: Matcher<View?>) {
    val idlingResource: IdlingResource = ViewIdlingResource(matcher, isDisplayed())
    try {
        IdlingRegistry.getInstance().register(idlingResource)
        // First call to onView is to trigger the idler.
        onView(withId(0)).check(doesNotExist())
    } finally {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}
