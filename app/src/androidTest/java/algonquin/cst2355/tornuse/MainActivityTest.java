package algonquin.cst2355.tornuse;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.algonquin.cst2355.tornuse.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test case for the main activity.
     * It checks abcde for the test
     */
    @Test
    public void mainActivityTest() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(withId(R.id.theEditText));
        appCompatEditText.perform(replaceText("abcde"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("Your password is complex enough")));
    }

    /**
     * Test case for the main activity.
     * It checks special characters for the test
     */
    @Test
    public void mainActivityTest2() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(withId(R.id.theEditText));
        appCompatEditText.perform(replaceText("@#%@%@##@4"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("Your password is complex enough")));
    }

    /**
     * Test case for the main activity.
     * It checks UPPER case for the test
     */
    @Test
    public void mainActivityTest3() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(withId(R.id.theEditText));
        appCompatEditText.perform(replaceText("EFSDXZ"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("Your password is complex enough")));
    }

    /**
     * Test case for the main activity.
     * It checks Numbers for the test
     */
    @Test
    public void mainActivityTest4() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(withId(R.id.theEditText));
        appCompatEditText.perform(replaceText("3532312"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("Your password is complex enough")));
    }

    /**
     * Test case for the main activity.
     * It checks special characters, numbers.. everything for the test
     */
    @Test
    public void mainActivityTest5() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(withId(R.id.theEditText));
        appCompatEditText.perform(replaceText("dowon@!$@!41422"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("Your password is complex enough")));
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
