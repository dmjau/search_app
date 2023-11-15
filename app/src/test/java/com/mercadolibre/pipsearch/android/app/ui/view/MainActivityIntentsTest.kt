package com.mercadolibre.pipsearch.android.app.ui.view

import android.content.ComponentName
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.mercadolibre.pipsearch.android.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class MainActivityIntentsTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var intentsRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun testMainActivityIntentStartCartActivityWhenIconIsClicked() {
        // when
        onView(withId(R.id.pip_main_header_cart_icon)).perform(click())

        // then
        Intents.intended(IntentMatchers.hasComponent(ComponentName(InstrumentationRegistry.getInstrumentation().context, CartActivity::class.java)))
    }
}
