# inf2100-pascal2016

## Build
`./gradlew` and `gradle` can be used interchangeably 

Build
if you dont have gradle installed

```
./gradlew build
```
or if you have gradle installed
```
gradle build
```

## Running

Tests: `gradle test` 

Build command automatically runs tests. 

Tests rely on Junit which is pulled automatically by gradle

Run application: `gradle run -PappArgs="['arg1', 'args2']"` or run jar directly from `/build/lib`

`gradle clean` nukes `build/`