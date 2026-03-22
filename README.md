# ComponentService

**ComponentService** is a library that helps decouple the initialization logic of components in Android applications (Application, Activity, Fragment) based on the **AutoRegister** library.

## 📦 Installation

Add JitPack to your `settings.gradle`:

```gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency to your `build.gradle` (module):

```gradle
dependencies {
    implementation("com.github.hoanganhtuan95ptit:ComponentService:1.0.0")
    ksp("com.github.hoanganhtuan95ptit.AutoRegister:auto-register-processor:1.1.1")
}
```

## 🚀 Usage

Simply create a class that implements the corresponding service interface and annotate it with `@AutoRegister(apis = [YourService::class])`. The library will automatically call the `setup` function without requiring any additional declarations in `onCreate`.

### 1. Application Service Example
```kotlin
import com.simple.auto.register.AutoRegister
import com.simple.component.service.ApplicationService

@AutoRegister(apis = [ApplicationService::class])
class MyTimberService : ApplicationService {
    override fun priority(): Int = 1
    override fun setup(application: Application) {
        Timber.plant(Timber.DebugTree())
    }
}
```

### 2. Activity Service Example
```kotlin
import com.simple.auto.register.AutoRegister
import com.simple.component.service.ActivityResumedService

@AutoRegister(apis = [ActivityResumedService::class])
class MyActivityLogger : ActivityResumedService {
    override fun setup(fragmentActivity: FragmentActivity) {
        // Automatically hooks into onResume of every Activity
        Log.d("ActivityLifecycle", "${fragmentActivity.javaClass.simpleName} Resumed")
    }
}
```

### 3. Fragment Service Example
```kotlin
import com.simple.auto.register.AutoRegister
import com.simple.component.service.FragmentViewCreatedService

@AutoRegister(apis = [FragmentViewCreatedService::class])
class MyFragmentTracker : FragmentViewCreatedService {
    override fun setup(fragment: Fragment) {
        // Automatically hooks into onViewCreated of every Fragment
        Analytics.trackScreen(fragment.javaClass.simpleName)
    }
}
```

## ⚙️ List of Services (Hook points)

Users can implement the following interfaces based on their needs:

### Application Service
```kotlin
interface ApplicationService : ComponentService<Application> {
    override fun setup(application: Application)
}
```

### Activity Services
```kotlin
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
```

### Fragment Services
```kotlin
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
```

---
Developed by [Hoàng Anh Tuấn](https://github.com/hoanganhtuan95ptit).
