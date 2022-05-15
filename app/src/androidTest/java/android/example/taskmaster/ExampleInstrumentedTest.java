package android.example.taskmaster;



import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;


import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity =
            new ActivityTestRule<>(MainActivity.class);

    
    @Test
    public void checkFor_RcycleView_detailPage() {

        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.addtask)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextTextPersonName)).perform(clearText(),typeText("task1"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPersonName2)).perform(clearText(),typeText("body"), closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
//        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("new"))));
        onView(withId(R.id.button3)).perform(click());
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("mainPage")).perform(click());
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.Recycle_task)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.detailpage)).check(matches(isDisplayed()));
        onView(withId(R.id.textView5)).check(matches(withText("task1")));
    }

    @Test
    public void Test_userNameSetting_inMainpage() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("action_Settings")).perform(click());
        onView(withId(R.id.settings)).check(matches(isDisplayed()));
        onView(withId(R.id.editTextTextPersonName3)).perform(clearText(),typeText("weasm"), closeSoftKeyboard());
        onView(withId(R.id.button4)).perform(click());
        pressBack();
        onView(withId(R.id.main)).check(matches(isDisplayed()));
        onView(withId(R.id.textView7)).check(matches(withText("weasm")));

    }

    @Test
    public void check_for_UI_elementsAre_Displayed_MainPage() {
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.textView7)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.button)).check(matches(isDisplayed()));
        onView(withId(R.id.button2)).check(matches(isDisplayed()));

    }


}