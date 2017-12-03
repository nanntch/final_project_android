package kmitl.natcha58070069.com.libreria.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import kmitl.natcha58070069.com.libreria.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private TextView tvLocation, tvLatLng;

    //Should @Before beforeTest()

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void loginToApp(){
        onView(allOf(withId(R.id.login_button))).perform(click());
    }

    @Test
    public void addNameAndCommentSuccess() {
        //When first open app with out login
//        onView(allOf(withId(R.id.login_button))).perform(click());
        onView(allOf(withId(R.id.maAdd))).perform(click());
        onView(withId(R.id.editName)).perform(replaceText("Libreria"), closeSoftKeyboard());
        onView(withId(R.id.editComment)).perform(replaceText("It's good place."), closeSoftKeyboard());
        onView(allOf(withId(R.id.adSave))).perform(click());
        pressBack();
//        onView(allOf(withId(R.id.maLogout))).perform(click());
//        onView(allOf(withId(R.id.login_button))).perform(click());
    }

//    @Test
//    public void

    @After
    public void LogoutApp(){
        onView(allOf(withId(R.id.maLogout))).perform(click());
        onView(allOf(withId(R.id.login_button))).perform(click());
        onView(allOf(withId(android.R.id.button1))).perform(click());
    }

//    @Test
//    public void AddNameAndCommentAfterLogin() {
//        onView(allOf(withId(R.id.login_button))).perform(click());
//        onView(allOf(withId(R.id.maAdd))).perform(click());
//        onView(withId(R.id.editName)).perform(replaceText("Libreria"), closeSoftKeyboard());
//        onView(withId(R.id.editComment)).perform(replaceText("It's good place."), closeSoftKeyboard());
//        onView(allOf(withId(R.id.adSave))).perform(click());
        //because it link to Google maps, I must fix value for test
//        tvLocation.setText("KMITL");
//        tvLatLng.setText("(1.00, -1.00)");
//        onView(allOf(withId(R.id.maTextFind))).perform(scrollTo(), click());
//        onView(allOf(withId(R.id.adSave))).perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        onView(allOf(withId(R.id.shBackToMain))).perform(scrollTo(), click());
//    }

//    @Test
//    public void EmptyName() {
//        onView(allOf(withId(R.id.login_button))).perform(click());
//
//        onView(allOf(withId(R.id.maAdd))).perform(click());
//        onView(withId(R.id.editName)).perform(replaceText(""), closeSoftKeyboard());
//        onView(withId(R.id.editComment)).perform(replaceText("It's good place."), closeSoftKeyboard());
//        onView(allOf(withId(R.id.adSave))).perform(click());
//    }

//    @Test
//    public void EmptyComment() {
//        onView(allOf(withId(R.id.login_button))).perform(click());
//
//        onView(allOf(withId(R.id.maAdd))).perform(click());
//        onView(withId(R.id.editName)).perform(replaceText("Libreria"), closeSoftKeyboard());
//        onView(withId(R.id.editComment)).perform(replaceText(""), closeSoftKeyboard());
//        onView(allOf(withId(R.id.adSave))).perform(click());
//    }

//    @Test
//    public void EmptyNameAndComment() {
//
//        onView(allOf(withId(R.id.login_button))).perform(click());
//
//        onView(allOf(withId(R.id.maAdd))).perform(click());
//        onView(withId(R.id.editName)).perform(replaceText(""), closeSoftKeyboard());
//        onView(withId(R.id.editComment)).perform(replaceText(""), closeSoftKeyboard());
//        onView(allOf(withId(R.id.adSave))).perform(click());
//    }

//    @Test
//    public void DeleteLibreria() {
//        onView(allOf(withId(R.id.login_button))).perform(click());
//
//        onView(allOf(withId(R.id.maAdd))).perform(click());
//        onView(withId(R.id.editName)).perform(replaceText("Libreria"), closeSoftKeyboard());
//        onView(withId(R.id.editComment)).perform(replaceText("It's good place."), closeSoftKeyboard());
//        onView(allOf(withId(R.id.adDelete))).perform(click());
//    }

//    @Test
//    public void BackToMain() {
//        onView(allOf(withId(R.id.login_button))).perform(click());
//
//        onView(allOf(withId(R.id.maAdd))).perform(click());
//        onView(withId(R.id.editName)).perform(replaceText("Libreria"), closeSoftKeyboard());
//        onView(withId(R.id.editComment)).perform(replaceText("It's good place."), closeSoftKeyboard());
//        onView(allOf(withId(R.id.adBack))).perform(click());
//    }
//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
}
