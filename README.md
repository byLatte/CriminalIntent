## Fragment
- Fragment Instance는 onCreate에서 구성하지만 Fragment View는 onCreateView에서 생성하고 구성한다.
## Gradle 추가
- ViewModel
```
implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
```
- RecyclerView
```
implementation 'androidx.recyclerview:recyclerview:1.1.0'
```
- Room (Database 이용)
```
plugins {
    id 'kotlin-kapt' // 추가
}

implementation 'androidx.room:room-runtime:2.2.6'
kapt 'androidx.room:room-compiler:2.2.6'
```

