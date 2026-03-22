@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package com.simple.component.service

import android.app.Application
import android.content.ComponentCallbacks
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

interface ComponentService<T : ComponentCallbacks> {

    fun priority(): Int = 0

    fun setup(t: T)
}

interface ApplicationService : ComponentService<Application> {
    override fun setup(application: Application)
}

// Activity Services
interface ActivityService : ComponentService<FragmentActivity> {
    override fun setup(fragmentActivity: FragmentActivity)
}

interface ActivityStartedService : ComponentService<FragmentActivity> {
    override fun setup(fragmentActivity: FragmentActivity)
}

interface ActivityResumedService : ComponentService<FragmentActivity> {
    override fun setup(fragmentActivity: FragmentActivity)
}

interface ActivityPausedService : ComponentService<FragmentActivity> {
    override fun setup(fragmentActivity: FragmentActivity)
}

interface ActivityStoppedService : ComponentService<FragmentActivity> {
    override fun setup(fragmentActivity: FragmentActivity)
}

interface ActivityDestroyedService : ComponentService<FragmentActivity> {
    override fun setup(fragmentActivity: FragmentActivity)
}

// Fragment Services
interface FragmentAttachedService : ComponentService<Fragment> {
    override fun setup(fragment: Fragment)
}

interface FragmentCreatedService : ComponentService<Fragment> {
    override fun setup(fragment: Fragment)
}

interface FragmentViewCreatedService : ComponentService<Fragment> {
    override fun setup(fragment: Fragment)
}

interface FragmentStartedService : ComponentService<Fragment> {
    override fun setup(fragment: Fragment)
}

interface FragmentResumedService : ComponentService<Fragment> {
    override fun setup(fragment: Fragment)
}

interface FragmentPausedService : ComponentService<Fragment> {
    override fun setup(fragment: Fragment)
}

interface FragmentStoppedService : ComponentService<Fragment> {
    override fun setup(fragment: Fragment)
}

interface FragmentViewDestroyedService : ComponentService<Fragment> {
    override fun setup(fragment: Fragment)
}

interface FragmentDestroyedService : ComponentService<Fragment> {
    override fun setup(fragment: Fragment)
}

interface FragmentDetachedService : ComponentService<Fragment> {
    override fun setup(fragment: Fragment)
}
