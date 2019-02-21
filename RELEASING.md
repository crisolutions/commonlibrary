# Releasing

This project is published using [jitpack](jitpack.io). Any version increment tagged on the master branch will be automatically made available as an artifact by jitpack. 

To release a new set of features or fixes worked on `dev` branch, `dev` must be merged into `master`. This stable build once approved and merged can be tagged with the next release version.

You can use [this link](https://jitpack.io/#crisolutions/commonlibrary) directly to view available library artifacts from jitpack and to also check if the newly tagged version is available.

The project follows the `MAJOR.MINOR.PATCH` versioning. A subset of fixes can increment the `PATCH` number. A new subset of extensions or helper methods can increment the `MINOR` version. Criteria for a `MAJOR` has not yet been set.

Currently there is no build-server or CI dedicated to the project, thus any pull requests to `master` or `dev` should be built and checked by the reviewers manually.