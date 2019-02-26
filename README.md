# Common Library
[![Build Status](https://travis-ci.org/crisolutions/commonlibrary.svg?branch=master)](https://travis-ci.org/crisolutions/commonlibrary)

[![Release](https://jitpack.io/v/User/Repo.svg)](https://jitpack.io/#crisolutions/commonlibrary)

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
implementation 'com.github.crisolutions:commonlibrary:x.y.z'
```

To use just the Kotlin module of the library, of the module `commonlibktx`, you may only add this instead :

```
implementation 'com.github.crisolutions.commonlibrary:commonlib:x.y.z'
```


where `x.y.z` is the latest release available.

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

## Releasing
The [RELEASING.md](./RELEASING.md) file includes information on the releasing process for the library.
