package com.example.rma24projekat_19153

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputLayout
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.hamcrest.number.OrderingComparison.greaterThan

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestS2S {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(
        NovaBiljkaActivity::class.java
    )

    @Test
    fun validacija1() {
        onView(withId(R.id.nazivET)).perform(replaceText("Naziv Biljke"))
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica Biljke"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Medicinsko Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0)
            .perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        Thread.sleep(2000)

    }

    @Test
    fun testNazivBiljkeManjiOdDvaZnaka() {
        // Unos teksta "A" u EditText za unos naziva biljke
        onView(withId(R.id.nazivET)).perform(replaceText("A"))
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica Biljke"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Medicinsko Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())

        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Naziv biljke mora imati između 3 i 20 znakova")))

    }

}