# ComponentService

**ComponentService** is a powerful lifecycle injection library for Android. It allows developers to "inject" any piece of code into the lifecycle of Components (Application, Activity, Fragment) while remaining **completely independent** from the main codebase.

## 🌟 Why ComponentService?

Traditional lifecycle management often leads to bloated `BaseActivity` or `BaseFragment` classes and tightly coupled logic in `onCreate`, `onResume`, etc. 

**ComponentService** solves this by:
- **Zero-code changes**: Inject logic into components without touching their source code.
- **Independence**: Keep feature-specific logic (Analytics, Logging, Theme management) in its own module/file, away from the main branch.
- **Decoupling**: Modules can hook into the lifecycle of an Activity or Fragment they don't even "know" about.

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

Simply create a class that implements a service interface and annotate it with `@AutoRegister(apis = [YourService::class])`. The library handles the rest.

### 1. Independent Application Initialization
Inject global SDK initialization without cluttering your `Application` class.
```kotlin
import com.simple.auto.register.AutoRegister
import com.simple.component.service.ApplicationService

@AutoRegister(apis = [ApplicationService::class])
class TimberInitializer : ApplicationService {
    override fun priority(): Int = 1
    override fun setup(application: Application) {
        Timber.plant(Timber.DebugTree())
    }
}
```

### 2. Decoupled Activity Hooking
Inject logging or monitoring into every Activity without a `BaseActivity`.
```kotlin
@AutoRegister(apis = [ActivityResumedService::class])
class ActivityTracker : ActivityResumedService {
    override fun setup(fragmentActivity: FragmentActivity) {
        // Automatically hooks into onResume of every Activity independently
        Log.d("Tracker", "${fragmentActivity.javaClass.simpleName} is now active")
    }
}
```

### 3. Feature-Specific Fragment Injection
Inject analytics into a specific screen without modifying the Fragment file.
```kotlin
@AutoRegister(apis = [FragmentViewCreatedService::class], className = "com.myapp.home.HomeFragment")
class HomeAnalyticsInjection : FragmentViewCreatedService {
    override fun setup(fragment: Fragment) {
        // This code is injected into HomeFragment's onViewCreated automatically
        Analytics.trackScreen("HomeView")
    }
}
```

## ⚙️ Supported Lifecycle Hooks

You can implement the following interfaces to inject code at specific moments:

### Application
- `ApplicationService`: Injected during Application creation.

### Activity Lifecycle Hooks
- `ActivityService`: Injected into `onCreate`.
- `ActivityStartedService`: Injected into `onStart`.
- `ActivityResumedService`: Injected into `onResume`.

### Fragment Lifecycle Hooks
- `FragmentAttachedService`: Injected into `onAttach`.
- `FragmentCreatedService`: Injected into `onCreate`.
- `FragmentViewCreatedService`: Injected into `onViewCreated`.
- `FragmentStartedService`: Injected into `onStart`.
- `FragmentResumedService`: Injected into `onResume`.

---
Developed by [Hoàng Anh Tuấn](https://github.com/hoanganhtuan95ptit).
