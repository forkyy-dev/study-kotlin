## 2장 - 코틀린 기초

### 기본 요소: 함수와 변수

#### 함수

- 기본구조: `fun [함수명] (파라미터명: 파라미터타입): 반환타입 { ... }`
    - 여기서 반환타입은 없을 경우 생략할 수 있다.

```kotlin
// 블록이 본문인 함수
fun max(a: Int, b: Int): Int {
    return if (a>b) a else b
}

// 식이 본문인 함수
fun max(a: Int, b: Int): Int = if (a > b) a else b
```

- 코틀린에서 `if문`은 `statement`가 아닌 결과를 만들어 내는 `expression`이기 때문에 반환값으로 사용이 가능하다.  
  위의 두번째 예시처럼 간결하게 표현도 가능하다.
  > **코틀린에서는 주로 식이 본문인 함수가 자주 쓰인다.**
- 식이 본문인 함수의 경우에는 반환타입을 생략할 수 있다. (블록이 본문인 함수는 중간중간 return이 많이 사용될 수 있기 때문에 명시적으로 반환타입을 작성하도록 되어 있다.)

#### 변수

- 기본구조: `var/val <변수명> = <값>`

```kotlin
val question = "질문"
val answer = 42

val number: Int
number = 1
```
- 기본적으로는 타입을 작성하지 않아도 되지만 초기값을 지정하지 않을 경우에는 타입을 무조건 명시해야한다.
- `val(value)`: 자바 기준으로 final 변수
- `var(variable)`: 자바 기준으로 일반 변수
> 코틀린에서는 기본적으로 val을 사용하는것을 권장한다.

#### 문자열 템플릿

- 코틀린에서는 문자열 내에서 `$변수명` 혹은 `${변수명}` 형태로 사용할 수 있다. 
  - _중괄호를 사용하는게 아무래도 가독성 면에서 좋을것으로 보인다._
```kotlin
fun main(args: Array<String>) {
    val name = if (args.isNotEmpty()) args[0] else "Kotlin"
    println("My name is $name")
    println("My name is ${args[0]}")
}
```

### 클래스와 프로퍼티

#### 클래스
- 코틀린에서는 생성자를 만들고 필드를 대입하는 로직을 자동으로 만들어주기 때문에 더욱 더 간결하게 클래스를 작성할 수 있다.
- 코틀린은 기본 가시성이 `public`이다.
- 자바와 다르게 `new` 키워드 없이 생성한다.
```kotlin
class Person(val name: String)

val person1 = Person("함석호")
```

#### 프로퍼티
- 프로퍼티란 **자바에서 클래스가 가지는 필드와 접근메서드를 대체하는 역할**이다.
- val로 만든 프로퍼티의 경우 가시성은 private이며 getter만 자동으로 생성한다.
- var로 만든 프로퍼티의 경우 가시성은 private이며 getter, setter 모두를 자동으로 생성한다.

```kotlin
class Person(
    val name: String,   // private + getter
    var age: Int        // private + getter + setter
)

val person = Person("함석호", 31)

//getter
println(person.name)
println(person.age)

//setter
person.age = 20
```

#### 커스텀 접근자

- 아래와 같이 isSquare 프로퍼티는 값을 저장하는 필드가 존재하지 않고, 오로지 게터만 존재한다.

```kotlin
class Rectangle (
    val height: Int,
    val width: Int
) {
    val isSquare: Boolean
        // get() {
        //     return height == width
        // }
    
      get() = height == width
}
```
- 함수 정의 vs 커스텀 getter 정의 중 더 나은것은?
  - 사실 내부 동작이나 성능적으로는 차이가 없고, 가독성 측면에서의 차이만 존재한다.
  - 클래스의 특성을 정의하고 싶다면 프로퍼티로 써라.

#### 디렉토리와 패키지

- 패키지 선언을 통해 패키지를 지정할 수 있다.
- 동일한 패키지에 속해 있다면, 다른 파일에 선언되어 있는 내용이더라도 사용이 가능하다.
- 다른 패키지에 정의되어 있다면, import문을 사용하여 추가해야한다.
  - `import 패키지명.*` 을 사용할 경우 해당 패키지 내에 최상위에 정의된 모든 함수 혹은 프로퍼티까지 접근이 가능해진다.

```kotlin
//패키지 선언
package parent.child

//import문
import java.util.Random

class PrototypeClass () { }
fun xxFunction() {
    val random = Random()
    val num = random.nextInt()
}
```

**자바와의 차이는?**
- 자바는 디렉토리 구조가 패키지 구조를 따라가며 각각의 클래스가 별도로 분리되어 있다.
- 코틀린은 하나의 파일 안에 여러개의 클래스를 정의할 수 있으며, 하위 패키지에 별도의 디렉토리를 생성하지 않아도 문제가 되지 않는다.
> **하지만 자바의 방식을 사용하는게 일반적이며 권장된다.**

### 선택 표현과 처리: enum과 when

#### enum 클래스

- enum 클래스 내부에도 별도의 함수를 작성할 수 있다.

```kotlin
enum class Color (
    val r: Int,
    val g: Int,
    val b: Int
){
  RED(255,0,0), 
  ORANGE(255,165,0), 
  YELLOW(255,255,0), 
  GREEN(0,255,0), 
  BLUE(0,0,255), 
  INDIGO(75,0,130), 
  VIOLET(238,130,238);
  
  fun rgb() = (r * 256 + g) * 256 + b
}
```

#### when

- 자바의 switch 문을 대체하는 역할이며 식이기 식이 본문인 함수에 사용이 가능하다.
- 각 분기의 끝에 break를 넣지 않아도 된다.
- 해당 파일에 Color enum 클래스를 임포트하면 더 간단하게 사용할 수 있다.

```kotlin
fun getMnemonic(color:Color) = when(color) {
    Color.RED -> "Richard"
    Color.ORANGE -> "Of"
    Color.YELLOW -> "York"
    Color.GREEN -> "Gave"
    Color.BLUE -> "Battle"
    Color.INDIGO -> "In"
    Color.VIOLET -> "Vain"
}
```

- 자바의 switch와 다르게 분기 조건으로 객체를 사용하는것도 가능하다.
```kotlin
fun getMnemonic(c1:Color, c2: Color) = when(setOf(c1, c2)) {
    setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
    setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
    setOf(Color.BLUE, Color.VIOLET) -> Color.INDIGO
    else -> throw Exception("Dirty color")
}
```

- when의 조건에 인자 없이 사용도 가능하다. 이럴 경우에는 **_각 분기의 조건에 boolean 결과를 계산하는 식이어야 한다_**.
```kotlin
when {
    //조건 -> 결과
}
```

#### 스마트 캐스트: 타입 검사와 타입 캐스트를 조합

- 코틀린에서 `is`를 사용하여 타입을 검사한다. (자바의 instanceOf)
- 자바와의 차이점은 검사 후 **명시적으로 타입을 변환하지 않아도 타입 추론을 통해 컴파일러가 캐스팅을 해준다**.  
  이를 `스마트 캐스트` 라고 부른다.

```java
public static void main(String[] args) {
  Person p = new Person("포키");
  if (p instanceof Human){
    Human h = (Human)p;
    System.out.println(h);
  }
}
```

```kotlin
fun main() {
    val p = Person("포키")
  if (p is Human) {
      val h = p //컴파일러 덕분에 `is Human`이 생략이 가능해진다.
  }
}
```

#### if와 when에서의 블록 사용

- if 와 when 모두 분기에서 블록을 사용할 수 있다.
- 값을 반환해야하는 경우라면 블록 내의 마지막 식이 결과가 된다.

### 이터레이션: while 과 for 루프

- while은 자바와 동일하다!
  - 기본구조: `while (조 건) { ... }`
- for 루프는 자바의 for each 형태로만 존재한다. 기본적으로 `Range`를 사용하여 구현되어 있다.
  - 기본구조: `for(<원소> in <범위>)`
  - 범위에는 숫자뿐만 아니라 문자 타입의 값에도 적용이 가능하다!
  - `downTo`, `step`을 사용해 역순, 증가값을 설정할 수 있다.
  - `until`을 사용하면 마지막 숫자를 제외한 범위까지 순회한다.

```kotlin
for (i in 1..100){
    //...
}

for (i in 1..100 step 2) {
    //...
}

for (i in 100 downTo 1 step 2) {
    //...
}
```

#### map에 대한 이터레이션

- 구조분해 문법을 사용하여 key, value를 바로 꺼낼 수 있다.
  ```kotlin
  val m = TreeMap<Char, String>()
  for ((k, v) in m) {
    //...
  }
  ```

#### in을 사용한 컬렉션 원소 검사

- `in` 연산자를 사용하여 원소가 해당 컬렉션이나 범위에 포함되어 있는지 검사할 수 있다.
  - _실재 내부 구현은 크기 비교로 이루어져있다._

```kotlin
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'
```

### 예외 처리 방법

- 코틀린에서는 예외가 모두 `UncheckedException`이다.
- 코틀린에서는 try도 식이기 때문에 변수에 대입이 가능하다.





