# Repository containing utility libraries for common use cases in Android development

## EmptyStateRecyclerView
RecyclerView with support for empty state view.

Use the library adding `implementation 'com.kacera:emptystaterecyclerview:1.0.0'` into your build.gradle file.

## DynamicText
Data class which can be used to combine String data with string resource identifiers.

Use the library adding `implementation 'com.kacera:dynamictext:2.0.0'` into your build.gradle file.

### Usage
One of the usage cases this comes handy is when you want to conditionaly set either String text or StringRes id to one TextView using data-binding.  

Code in ViewModel
```
val title = MutableLiveData<DynamicText>()

fun onDataLoaded(response: Respose) {
    if (response.sucess) {
        title.value = DynamicText.from(response.data.title)	//data.title is String
    } else {
	    title.value = DynamicText.from(R.string.oh_no)	//id of a string from resources is Int
	}
}
```

Code in layout file
```
<TextView
    ...
    android:text="@{viewModel.title}"/>
```