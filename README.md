# Dynamic Components
This project contains a set of useful classes which can be used to hold various data sources regardless of their format.

## DynamicText
Data class which can be used to combine String data with string resource identifiers. Can be used for regular strings or plurals.

Use the library adding `implementation 'com.twocoders.dynamic:text:3.0.0'` into your build.gradle file.

### Usage
One of the usage cases this comes handy is when you want to conditionally set either String text or StringRes id to one TextView using data-binding.  

Code in ViewModel
```kotlin
val title = MutableLiveData<DynamicText>()

fun onDataLoaded(response: Respose) {
    if (response.sucess) {
        title.value = DynamicText.from(response.data.title) //-> data.title is String
    } else {
        title.value = DynamicText.from(R.string.oh_no) //-> id of a string from resources is Int
    }
}
```

Code in layout file
```xml
<TextView
    android:text="@{viewModel.title}" />
```