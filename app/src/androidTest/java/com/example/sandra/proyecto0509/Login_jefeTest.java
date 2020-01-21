package com.example.sandra.proyecto0509;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static java.util.EnumSet.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

@RunWith(AndroidJUnit4.class)
public class Login_jefeTest {

    @Rule
    public IntentsTestRule<Login_jefe> activityRule = new IntentsTestRule<>(Login_jefe.class);

    private String username="jefeestudios4@hotmail.com";
    private String password="1234567890";


    @Test
    public void clickLoginButton(){
        intended(hasComponent(Login_jefe.class.getName()),times(0));
        onView(withId(R.id.ed_email)).perform(ViewActions.typeText(username), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(ViewActions.typeText(password), closeSoftKeyboard());
        onView(withId(R.id.btn_yaregistrado)).perform(click());
        intended(hasComponent(Resul_Jefe_Estudios.class.getName()),times(1));

    }
}