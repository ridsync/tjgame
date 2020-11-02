# tjgame

태준을 위한 동물소리 심플 게임 프로젝트

PhysicsLayout 오픈소스 사용하여 각 동물소리가 나오는 유아게임

## Gradle Dependency

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

Then, add the library to your project `build.gradle`
```gradle
dependencies {
    compile 'com.github.Jawnnypoo:PhysicsLayout:2.1.0'
}
```

## Reference Library

참조한 라이브러리는 [PhysicsLayout](https://github.com/Jawnnypoo/PhysicsLayout) 입니다.



License
--------

    Copyright 2016 John Carlson

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
