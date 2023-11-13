package com.mercadolibre.pipsearch.android.app.ui.view

import android.content.ComponentName
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
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
class CartActivityIntentsTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(CartActivity::class.java)

    @Rule
    @JvmField
    var intentsRule = IntentsTestRule(CartActivity::class.java)

    @Test
    fun testMainActivityIntentStartCartActivityWhenIconIsClicked() {
        // when
        onView(ViewMatchers.withId(R.id.pip_cart_header_back)).perform(ViewActions.click())

        // then
        Intents.intended(IntentMatchers.hasComponent(ComponentName(InstrumentationRegistry.getInstrumentation().context, MainActivity::class.java)))
    }
}