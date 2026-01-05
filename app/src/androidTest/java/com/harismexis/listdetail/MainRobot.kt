package com.harismexis.listdetail

import androidx.test.core.app.ActivityScenario

import junit.framework.TestCase.assertNotNull

class MainRobot {

    private lateinit var scenario: ActivityScenario<MainActivity>

    fun launchActivity() {
        scenario = androidx.test.core.app.launchActivity<MainActivity>()
    }

    fun finishActivity() {
        scenario.close()
    }

    fun checkActivityNonNull() {
        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }

}