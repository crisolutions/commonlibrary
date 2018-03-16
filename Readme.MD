# Common Library

Library of the Common module from CRI Solutions.

## Getting Started

To get the library setup, add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

And then add the dependency :

```
implementation 'com.github.crisolutions:commonlibrary:1.0.1'
```

Finally, update the Source and Target Compatibility to 1.8 in Project Structure for whichever module this dependency is being added to, or add 

```
android {
        ...
	compileOptions {
       	    sourceCompatibility JavaVersion.VERSION_1_8
	    targetCompatibility JavaVersion.VERSION_1_8
	}
}
```

to the module's gradle file.
