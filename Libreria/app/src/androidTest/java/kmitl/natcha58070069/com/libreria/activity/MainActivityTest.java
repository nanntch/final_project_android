package kmitl.natcha58070069.com.libreria.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kmitl.natcha58070069.com.libreria.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void loginToApp() {
        onView(allOf(withId(R.id.login_button))).perform(click());
    }

    @Test
    public void InputTest() {
        //When first open app with out login
        onView(allOf(withId(R.id.maAdd))).perform(click());
        //Empty both
        onView(allOf(withId(R.id.adSave))).perform(click());

        //Empty name
        onView(withId(R.id.editComment)).perform(replaceText("It's good place."), closeSoftKeyboard());
        onView(allOf(withId(R.id.adSave))).perform(click());

        //Empty comment
        onView(withId(R.id.editName)).perform(replaceText("Libreria"), closeSoftKeyboard());
        onView(withId(R.id.editComment)).perform(replaceText(""), closeSoftKeyboard());
        onView(allOf(withId(R.id.adSave))).perform(click());

        //fully & Delete
        onView(withId(R.id.editName)).perform(replaceText("Libreria"), closeSoftKeyboard());
        onView(withId(R.id.editComment)).perform(replaceText("It's good place."), closeSoftKeyboard());
        onView(allOf(withId(R.id.adSave))).perform(click());
        onView(allOf(withId(R.id.adDelete))).perform(click());
//        pressBack();
    }


    @After
    public void LogoutApp() {
        onView(allOf(withId(R.id.maLogout))).perform(click());
        onView(allOf(withId(R.id.login_button))).perform(click());
        onView(allOf(withId(android.R.id.button1))).perform(click());
    }

}
