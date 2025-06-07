## 3장 - 함수 정의와 호출

### 코틀린에서 컬렉션 만들기

- 코틀린은 자체 컬렉션을 제공하지 않고 **표준 자바의 컬렉션을 사용한다**.
    - _왜 자체 컬렉션을 안쓸까?_ : 자바와의 호환성을 위해!
- 자바보다 더 많은 편의 메서드들을 제공한다.
- 기본적으로 아래 함수를 써서 만들면 불변으로 생성된다. 가변으로 만들려면 `mutableXX()` 메서드를 사용하면 된다.

```kotlin
val set = hashSetOf(1, 7, 53)
val list = arrayListOf(1, 7, 53)
val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
```

---

자바에서는 컬렉션을 원하는 형식으로 출력하려면 편리함을 제공하는 서드파티(구아바, 아파티 커먼즈)를 추가해야만 했다.  
  **코틀린에서는 이런 요구사항들을 충족하는 함수가 표준 라이브러리에 제공된다.**

### Named Parameter

- 아래의 코드는 각각의 파라미터가 어떤 역할로 사용되는지 구분하기가 쉽지 않다.
  ```kotlin
  joinToString(collection, "", "", ".")
  ```

- 코틀린에서는 편의성을 위해 각각의 인자에 대해 이름을 명시할 수 있다.  
  이름을 명시했으면 뒤에 있는 인자들은 필수적으로 이름을 명시해야한다.

  ```kotlin
  joinToString(collection, separator = " ", prefix = " ", postfix = "." )
  ```

### Default Parameter
- 하나의 오버로딩한 메서드가 너무 많을 경우 메서드 호출 시 어떤 메서드가 호출될지 구분이 안되거나 헷갈리는 경우가 발생할 수 있다.
  혹은 동일한 값을 반복해서 입력해야하는 번거로움이 발생할 수도 있다.
- **코틀린에서는 파라미터의 default 값을 지정해 이런 불필요한 오버로딩을 피할 수 있다.**
- NamedParameter와 함께 사용하여 일부 인자를 제외하고 지정하여 넣을수도 있다.

  ```kotlin
  fun <T> joinToString(
      collection: Collection<T>,
      separator: String = ",",
      prefix: String = "",
      postfix: String = ""
  ): String { ... }
  
  joinToString(list)
  joinToString(list, ";")
  joinToString(list, prefix = "(", postfix = ")")
  ```
> 함부로 값을 바꾸면 해당 함수를 사용하고 있는 모든 부분이 영향을 받으니까 주의해야한다.

> 자바에서 코틀린 함수를 사용할 경우 `@JvmOverloads` 어노테이션을 붙여준다면 컴파일러가 자동으로 해당 함수의 파라미터들을 하나씩 생성한 메서드들을 오버로딩해준다. 디폴트값으로는 코틀린에 명시한 값을 사용한다. 

### 정적 유틸리티 클래스 없애기: 최상위 함수와 프로퍼티

#### 최상위 함수

- 자바에서는 함수가 무조건 클래스내에 속해야만 했기 때문에 유틸성 클래스들을 많이 작성해야만 했다.
- **코틀린에서는 함수를 소스파일의 최상위 레벨에 작성할 수 있기 때문에 그럴 필요가 사라졌다.**
- 실제 컴파일러가 컴파일할때는 해당 함수가 작성되어 있는 파일의 파일명으로 새로운 클래스를 정의해준다.
  - 만약 생성되는 클래스명을 바꾸고 싶다면 `@JvmName` 어노테이션을 해당 파일의 최상위에 작성하면 된다.
  ```kotlin
  @file:JvmName("새로운 파일명")
  ```

#### 최상위 프로퍼티

- 프로퍼티도 파일의 최상위에 위치시킬 수 있다. 사실상 정적 필드, 상수 선언에 사용될 수 있다.
  - 상수의 경우 앞에 `const` 변경자를 추가하면 컴파일 시 `public static final`로 컴파일이 된다.
  - `const` 변경자는 primitive 타입과 String 타입에만 추가할 수 있다.
  ```kotlin
  const val CONST_STRING = "상수상수"
  ```

### 확장 함수와 확장 프로퍼티

자바와의 호환성에 있어서 기존의 자바 API를 재작성하지 않고도 코틀린이 제공하는 편의 기능들을 사용할 수 있게 해주는 요소 중에는 대표적으로 **확장함수**가 존재한다.

#### 확장 함수

- 특정 클래스의 멤버 메서드인것처럼 호출이 되지만, 해당 클래스 외부에 선언된 함수를 의미한다.
- 구조: `<수신 객체 타입>.<함수명>(): <수신 객체>.xxx`
  ```kotlin
  //수신 객체 타입: String, 수신 객체: this
  fun String.lastChar(): Char = this.get(this.length - 1)
  
  println("HelloWorld".lastchar()) // "d"
  ```

**<확장 함수의 특징>**
- 자바 코드로 컴파일한 클래스 파일이 있다면 해당 클래스에는 확장함수를 추가할 수 있다. 즉, 제한이 거의 없다.
- this(수신객체)를 생략할수도 있다.
- 클래스 내부에서 사용되는 private, protected 프로퍼티나 메서드에는 접근할 수 없다.
- 확장 함수 사용을 위해서는 import가 필요하며, `as`를 사용하여 더 짧은 이름으로 사용이 가능하다.
  ```kotlin
  import strings.lastChar as last
  
  println("HelloWorld".last())
  ```

- 자바에서 확장 함수를 사용할때는 수신객체를 인자로 넘겨주면 된다.
  ```java
  System.out.println(StringUtilKt.lastChar("HelloWorld"));
  ```

- **확장함수는 오버라이드 할 수 없다!**
  - 확장함수 호출 시 수신 객체로 지정한 변수의 정적 타입에 의해 호출될 확장함수가 결정된다.
  ```kotlin
  open class View()
  class Button: View(){}
  
  fun View.showOff() = println("view")
  fun Button.showOff() = println("button")
  
  val view: View = Button()
  println(view.showOff()) // view
  ```

- 확장 함수와 해당 클래스의 멤버 함수의 이름이 동일할 경우 멤버 함수의 우선순위가 높기 때문에 멤버 함수가 호출된다.


#### 확장 프로퍼티

- 확장 함수와 비슷하게 해당 클래스의 필드처럼 불러서 사용할 수 있지만 실제로 클래스 내에 필드가 추가되는건 아니기 때문에 상태를 갖지는 못한다.
  - getter는 최소한 작성해주는게 권장된다.

  ```kotlin
  val String.lastChar: Char
    get() = get(length - 1)
  ```

### 컬렉션 처리: 가변 길이 인자, 중위 함수 호출, 라이브러리 지원

#### 가변 인자 함수

- 코틀린에서는 변수 앞에 `vararg` 변경자를 붙여주면 여러개의 인자를 전달할 수 있다.
  ```kotlin
  fun testFunc(vararg numbers: Int) { ... }
  ```
- 스프레드 연산자: 배열을 풀어서 전달할때는 앞에 `*`을 붙여주면 된다.
  ```kotlin
  fun testFunc(arr: Array<String>) { ... }
  
  val list = listOf("1", "2", "3")
  testFunc(*list)
  ```

#### 중위 호출과 구조분해 선언

- 중위 호출: 인자가 하나뿐인 일반 메서드 혹은 확장 함수에 사용할 수 있다.
  - 선언 방식: 메서드 앞에 `infix` 명령어를 입력하면 된다.
  - 코틀린에서 더 편하게 사용할 수 있도록 지원해주는것일뿐, 내부 구현이 특별한건 아니다.
  ```kotlin
  infix fun Any.to(other: Any) = Pair(this,other)
  
  val map = mapOf(1 to "one")
  ```
- 구조 분해 선언
  ```kotlin
  val (key,value) = Pair(1, "one")
  ```

### 문자열과 정규식 다루기
- 자바의 split은 정규식을 사용하여 구분하기 때문에 개발자가 의도한대로 결과가 나오지 않을때가 있다.
- 코틀린에서는 String 타입이 아닌 명시적으로 Regex 타입을 받기 때문에 혼동을 피할 수 있다.
  ```kotlin
  val result1 = "12.345-6.A".split("[.\\-]".toRegex())
  val result2 = "12.345-6.A".split('.', '-')
  ```
- 3중 따옴표를 사용하면 이스케이프 문자도 필요하지 않다. 또한 줄바꿈이 필요할때도 명시적으로 줄바꿈을 하는 이스케이프 문자도 필요없다. 

### 로컬 함수와 확장

- 메서드를 작은 단위로 나누는 작업은 이점도 있지만 동시에 너무 세분화가 되면 가독성이 오히려 떨어진다는 문제가 생긴다.
- 코틀린에서는 추출한 함수를 원래 함수 내부에 중첩시킬 수 있는데 이를 **로컬함수**라고 한다.













