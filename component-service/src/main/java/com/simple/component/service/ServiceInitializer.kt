package com.simple.component.service

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.ComponentCallbacks
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.startup.Initializer
import com.simple.auto.register.AutoRegisterManager
import kotlinx.coroutines.flow.map

class ServiceInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val application = context.applicationContext as? Application ?: return
        setupApplication(application)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

    private fun setupApplication(application: Application) {

        AutoRegisterManager.subscribe(ApplicationService::class.java).map { it.toList() }.launchCollect(ProcessLifecycleOwner.get()) { list ->
            list.setup(application)
        }

        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity is FragmentActivity) setupActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                if (activity is FragmentActivity) setupActivityLifecycle(activity, ActivityStartedService::class.java)
            }

            override fun onActivityResumed(activity: Activity) {
                if (activity is FragmentActivity) setupActivityLifecycle(activity, ActivityResumedService::class.java)
            }

            override fun onActivityPaused(activity: Activity) {
                if (activity is FragmentActivity) setupActivityLifecycle(activity, ActivityPausedService::class.java)
            }

            override fun onActivityStopped(activity: Activity) {
                if (activity is FragmentActivity) setupActivityLifecycle(activity, ActivityStoppedService::class.java)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {
                if (activity is FragmentActivity) setupActivityLifecycle(activity, ActivityDestroyedService::class.java)
            }
        })
    }

    private fun setupActivity(fragmentActivity: FragmentActivity) {

        AutoRegisterManager.subscribe(ActivityService::class.java).map { it.toList() }.launchCollect(fragmentActivity) { list ->
            list.setup(fragmentActivity)
        }

        AutoRegisterManager.subscribe(fragmentActivity.javaClass.name, ActivityService::class.java).map { it.toList() }.launchCollect(fragmentActivity) { list ->
            list.setup(fragmentActivity)
        }

        fragmentActivity.supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                setupFragment(f, FragmentAttachedService::class.java)
            }

            override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
                setupFragment(f, FragmentCreatedService::class.java)
            }

            override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
                setupFragment(f, FragmentViewCreatedService::class.java)
            }

            override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                setupFragment(f, FragmentStartedService::class.java)
            }

            override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                setupFragment(f, FragmentResumedService::class.java)
            }

            override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
                setupFragment(f, FragmentPausedService::class.java)
            }

            override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
                setupFragment(f, FragmentStoppedService::class.java)
            }

            override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                setupFragment(f, FragmentViewDestroyedService::class.java)
            }

            override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                setupFragment(f, FragmentDestroyedService::class.java)
            }

            override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
                setupFragment(f, FragmentDetachedService::class.java)
            }
        }, true)
    }

    private fun <T : ComponentService<FragmentActivity>> setupActivityLifecycle(fragmentActivity: FragmentActivity, api: Class<T>) {

        AutoRegisterManager.subscribe(api).map { it.toList() }.launchCollect(fragmentActivity) { list ->
            list.setup(fragmentActivity)
        }

        AutoRegisterManager.subscribe(fragmentActivity.javaClass.name, api).map { it.toList() }.launchCollect(fragmentActivity) { list ->
            list.setup(fragmentActivity)
        }
    }

    private fun <T : ComponentService<Fragment>> setupFragment(fragment: Fragment, api: Class<T>) {

        AutoRegisterManager.subscribe(api).map { it.toList() }.launchCollect(fragment) { list ->
            list.setup(fragment)
        }

        AutoRegisterManager.subscribe(fragment.javaClass.name, api).map { it.toList() }.launchCollect(fragment) { list ->
            list.setup(fragment)
        }
    }

    private fun <T : ComponentCallbacks> List<ComponentService<T>>.setup(t: T) {

        sortedBy { it.priority() }.forEach { it.setup(t) }
    }
}
