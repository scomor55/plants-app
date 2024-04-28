package com.example.rma24projekat_19153

import android.view.View
import android.widget.EditText
import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
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
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.Is
import org.hamcrest.number.OrderingComparison.greaterThan

import org.junit.Rule
import org.junit.Test
import org.junit.runner.Description
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
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0)
            .perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        Thread.sleep(2000)

    }
 // Test Naziv Biljke
    @Test
    fun testNazivBiljkePrazan() {
        // Unos teksta "A" u EditText za unos naziva biljke
        onView(withId(R.id.nazivET)).perform(replaceText(""))
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())

        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Naziv biljke mora imati između 2 i 20 znakova")))

    }

    @Test
    fun testNazivBiljkeManjiOdDvaZnaka() {
        // Unos teksta "A" u EditText za unos naziva biljke
        onView(withId(R.id.nazivET)).perform(replaceText("A"))
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())

        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Naziv biljke mora imati između 2 i 20 znakova")))

    }

    @Test
    fun testNazivBiljkeVeciOdDvadesetZnakova() {
        // Unos teksta "A" u EditText za unos naziva biljke
        onView(withId(R.id.nazivET)).perform(replaceText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica "))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())

        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Naziv biljke mora imati između 2 i 20 znakova")))

    }

    // Test porodica biljke

    @Test
    fun testPorodicaBiljkePrazan() {
        // Unos teksta "A" u EditText za unos naziva biljke
        onView(withId(R.id.nazivET)).perform(replaceText("Naziv"))
        onView(withId(R.id.porodicaET)).perform(replaceText(""))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())

        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Porodica mora imati između 2 i 20 znakova")))

    }

    @Test
    fun testPorodicaBiljkeManjaOdDvaZnaka() {
        // Unos teksta "A" u EditText za unos naziva biljke
        onView(withId(R.id.nazivET)).perform(replaceText("Naziv"))
        onView(withId(R.id.porodicaET)).perform(replaceText("A"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())

        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Porodica mora imati između 2 i 20 znakova")))

    }

    @Test
    fun testPorodicaBiljkeVecaOdDvadesetZnakova() {
        // Unos teksta "A" u EditText za unos naziva biljke
        onView(withId(R.id.nazivET)).perform(replaceText("Naziv"))
        onView(withId(R.id.porodicaET)).perform(replaceText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())

        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Porodica mora imati između 2 i 20 znakova")))

    }

    // test medicinsko upozorenje

    @Test
    fun testMedicinskoUpozorenjePrazno() {
        // Unos teksta "A" u EditText za unos naziva biljke
        onView(withId(R.id.nazivET)).perform(replaceText("Naziv"))
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText(""))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())

        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Medicinsko upozorenje mora imati između 2 i 20 znakova")))

    }

    @Test
    fun testMedicinskoUpozorenjeManjeOdDvaZnaka() {
        // Unos teksta "A" u EditText za unos naziva biljke
        onView(withId(R.id.nazivET)).perform(replaceText("Naziv"))
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("A"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())

        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Medicinsko upozorenje mora imati između 2 i 20 znakova")))

    }

    @Test
    fun testMedicinskoUpozorenjeVeceOdDvadesetZnakova() {
        // Unos teksta "A" u EditText za unos naziva biljke
        onView(withId(R.id.nazivET)).perform(replaceText("Naziv"))
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Jelo1"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())

        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(hasErrorText("Medicinsko upozorenje mora imati između 2 i 20 znakova")))

    }

// Test medicinska korist listViewa

    @Test
    fun testMedicinskaKoristLVHasSelectedItem() {
        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(1).perform(click())

        onView(withId(R.id.medicinskaKoristLV)).check(matches(hasMinimumSelectedItems(1)))
    }

// Test klimatski tip listViewa

    @Test
    fun testKlimatskiTipLVHasSelectedItem() {
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(1).perform(click())

        onView(withId(R.id.klimatskiTipLV)).check(matches(hasMinimumSelectedItems(1)))
    }


    @Test
    fun testZemljisniTipLVHasSelectedItem() {
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(1).perform(click())

        onView(withId(R.id.zemljisniTipLV)).check(matches(hasMinimumSelectedItems(1)))
    }

    fun hasMinimumSelectedItems(minimumCount: Int): Matcher<View> {
        return object : BoundedMatcher<View, ListView>(ListView::class.java) {
            override fun describeTo(description: org.hamcrest.Description) {
                description.appendText("at least $minimumCount items are selected")
            }

            override fun matchesSafely(listView: ListView): Boolean {
                return listView.checkedItemCount >= minimumCount
            }
        }
    }

// test za profil okusa
   /* @Test
    fun testListViewProfilOkusa() {
        // Find the ListView
        onView(withId(R.id.profilOkusaLV)).perform(scrollTo())

        // Select an item programmatically (for example, select the first item)
        onData(allOf(`is`(instanceOf(ProfilOkusaBiljke::class.java)), `is`(ProfilOkusaBiljke.values()[0])))
            .inAdapterView(withId(R.id.profilOkusaLV))
            .perform(click())

        // Assert that the correct item is selected
        onView(withId(R.id.profilOkusaLV)).check(matches(withListSelection(`is`(0))))
    }

    fun withListSelection(selection: Matcher<Int>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: org.hamcrest.Description) {
                description.appendText("with list selection: ")
                selection.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is ListView) return false
                return view.checkedItemPosition != ListView.INVALID_POSITION && selection.matches(view.checkedItemPosition)
            }
        }
    }*/

    @Test
    fun testListViewProfilOkusa() {
        // Find the ListView and perform a click action on the first item
        onData(anything())
            .inAdapterView(withId(R.id.profilOkusaLV))
            .atPosition(0)
            .perform(click())

        // Check if only one item is checked
        onView(withId(R.id.profilOkusaLV))
            .check(matches(hasExactlyOneCheckedItem()))
    }

    fun hasExactlyOneCheckedItem(): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: org.hamcrest.Description) {
                description.appendText("exactly one item is checked")
            }

            override fun matchesSafely(item: View?): Boolean {
                if (item !is ListView) {
                    return false
                }
                return item.checkedItemCount == 1
            }
        }
    }

    // dodajJelo test

    @Test
    fun testIstaJela() {
        onView(withId(R.id.nazivET)).perform(replaceText("Naziv"))
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Supa"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.dodajJeloBtn)).perform(click())
       // onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())

        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Jelo je vec dodano")))

    }

    @Test
    fun testIstaJelaVelikaMalaSlova() {
        onView(withId(R.id.nazivET)).perform(replaceText("Naziv"))
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Supa"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.jeloET)).perform(replaceText("sUPA"))
        onView(withId(R.id.dodajJeloBtn)).perform(click())

        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Jelo je vec dodano")))

    }

    @Test
    fun testListaJelaDodanaJela() {
        onView(withId(R.id.nazivET)).perform(replaceText("Naziv"))
        onView(withId(R.id.porodicaET)).perform(replaceText("Porodica"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(replaceText("Upozorenje"))

        onData(anything()).inAdapterView(withId(R.id.medicinskaKoristLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.klimatskiTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.zemljisniTipLV)).atPosition(0).perform(click())
        onData(anything()).inAdapterView(withId(R.id.profilOkusaLV)).atPosition(0).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("Supa"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        onView(withId(R.id.jelaLV)).check(matches(hasChildCount(1)))
    }

    @Test
    fun testDisplayPlantImageAfterCapture() {
        onView(withId(R.id.uslikajBiljkuBtn)).perform(scrollTo(),click())
        onView(withId(R.id.slikaIV)).check(matches(isDisplayed()))
    }

}