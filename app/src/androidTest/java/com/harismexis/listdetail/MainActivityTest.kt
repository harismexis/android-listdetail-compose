package com.harismexis.listdetail

import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val robot: MainRobot = MainRobot()

    @Before
    fun setup() {
        robot.launchActivity()
    }

    @Test
    fun activityHasLaunched() {
        robot.checkActivityNonNull()
    }

    @After
    fun finish() {
        robot.finishActivity()
    }
}