## 4장 - 클래스, 객체, 인터페이스

- 코틀린의 선언은 기본적으로 `final & public`

### 코틀린 인터페이스

- default 메서드는 특별한 변경자 없이 메서드 구현을 해두면 된다.
- 인터페이스에 필드 추가는 불가능하다.
- 구현체에서는 반드시 `override` 변경자를 작성해야한다.


```kotlin
interface Clickable {
    fun hello() = println("안녕!")
    fun click()
}

class Mouse: Clickable {
    override fun click() = println("마우스 클릭!")
}
```

- 만약 구현체에서 2개의 클래스를 구현했는데, 양쪽에 동일한 시그니처의 default 메서드가 존재할 경우 구현체에서는 해당 메서드를 override 해야한다.
- 하위 클래스에서 상위 타입의 메서드를 호출할때는 `super<상위 클래스 타입>.method()` 형태로 호출한다.

```kotlin
class Mouse: Clickable {
    override fun click() = println("마우스 클릭!")
    override fun hello() {
        super<Clickable>.hello()
    } 
}
```

#### Kotlin에서의 인터페이스는 어떻게 구현될까?
Java6은 인터페이스에 default 메서드 구현이 지원되지 않는다.
이 때문에 Kotlin의 인터페이스 구현은 `Java의 인터페이스` + `정적 메서드를 가진 구현 객체`의 조합으로 구현된다.


### Kotlin에서의 상속 (feat. open, final, abstract)

#### 상속 - open
- Kotlin은 기본적으로 클래스가 final이다. 상속을 위해서는 `open` 키워드를 앞에 붙여줘야 한다.
  ```kotlin
    open class Test {}
  ```
  
- override된 메서드는 기본적으로 열려있기 때문에 막으려면 final 키워드가 추가되어야 한다.
    ```kotlin
    open class Mouse: Clickable {
        final override fun click() = println("마우스 클릭!")
    }
    ```
  
#### 추상 클래스 - abstract

- 추상 클래스는 해당 클래스의 인스턴스 생성이 불가능하다.
- 메서드 abstract 키워드가 선언되어 있지 않다면 해당 메서드는 default 메서드다.

```kotlin
abstract class Animated {
    abstract fun animate()
    open fun animateTwice() {}
    fun animateTwice() { }
}
```

### 가시성 변경자

> Kotlin의 기본 가시성은 `public` 이다.

kotlin에서는 `internal`이라는 가시성을 제공한다. 해당 가시성은 같은 모듈(함께 컴파일 되는 코드)에 한해서 접근할 수 있는 설정이다.

- 확장함수를 아래와 같이 선언한다고 했을때, 클래스 자체의 가시성은 internal인데 확장함수의 가시성이 public이라면 에러가 발생한다.

```kotlin
internal open class TalkativeButton {
    private fun yell() = println("ahhhhhhhhhh!")
    protected fun whisper() = println("shhhh..")
}

//불가능
fun TalkativeButton.hello() { }

//가능
internal fun TalkativeButton.hello() { }
```

- Java에서는 같은 패키지 내에 있으면 protected에 접근할 수 있지만, Kotlin에서는 불가능하다.  
  무조건 상속 받은 클래스에서만 접근이 가능하다.

#### Java로 컴파일이 된다면?
- 바이트코드상에서 `internal` 가시성은 `public`으로 컴파일 된다.
  - 결국 컴파일 후에는 다른 모듈에서도 접근이 가능해지지만, 오버라이드 방지 및 사용 방지를 위해 해당 멤버의 이름을 가독성이 떨어지게 수정한다.
- 클래스 레벨의 private은 Java에서는 지원되지 않기 때문에 패키지 전용 클래스로 컴파일 된다.

### 내부 클래스 & 중첩 클래스

- Kotlin의 내부 클래스는 명시적으로 요청하지 않는 한 외부 클래스 인스턴스에 대한 접근 권한이 아예 없다.
- Kotlin에서는 내부 클래스를 선언하면 Java의 `static inner class`랑 동일하게 취급된다.
- 일반적인 내부 클래스로 만들고 싶으면 `inner` 변경자를 추가하면 된다.
  - 외부 클래스를 참조하려면 `@Class명` 형태로 접근한다.
  ```kotlin
  class Outer {
    inner class Inner {
        fun accessOuter() : Outer = this@Outer
    } 
  }
  ```

### Sealed Class
- 상위 클래스를 상속한 하위 클래스의 정의를 제한할 수 있으며 반드시 상위 클래스 내에 중첩시켜야 한다.
- 내부적으로 private 생성자를 가진다.

```kotlin
sealed class Expr {
    class Num(val value: Int) : Expr()
    class Sum(val left: Expr, val right: Expr) : Expr()
}
```
- `sealed class`를 사용하면 when 식에서 else로 분기처리를 따로 하지 않아도 된다. (이미 하위 클래스가 제한되어 있어서)


## 생성자와 프로퍼티를 가지는 클래스 선언
- Kotlin에는 `주생성자`, `부생성자`, `초기화 블록`이 존재한다.
- 모든 파라미터에 default 파라미터를 설정할 경우, 컴파일러가 자동으로 빈 생성자를 생성해준다. 
```kotlin
class User(val name: String) {
    init {
        validate(name)
    }

    constructor(name: String) {
        this.name = name
    }

    fun validate(val name: String) { }
}
```

- 상속관계에 있는 클래스를 생성할때는 아래와 같이 해당 부모 클래스의 인자에 파라미터를 전달한다.
- 하위 클래스에서는 상속을 구현할때 부모 클래스 뒤에 괄호까지 함께 작성해야한다.  
```kotlin
open class User(name: String) {}
class AppUser(name: String) : User(name) {}
```

### 인터페이스의 프로퍼티

- Kotlin에서는 인터페이스에도 추상 파라미터를 선언할 수 있다.
  ```kotlin
  interface User {
    val nickname: String
  }
  
  class DefaultUser(override val nickname: String): User {}
  
  class SnsUser(val email: String): User {
    override val nickname: String
        get() = email.subStringBefore('@')
  }
  ```

### backing field

- Kotlin에서는 프로퍼티가 생성될때, 실제로 값이 저장되는 공간에 접근할 수 있는 식별자를 함께 제공해준다.
- 커스텀 getter, setter을 설정할때 사용할 수 있다.
  ```kotlin
  class SnsUser() {
    val nickname: String
        get() { "My name is ${field}" }
  }
  ```
  
### private setter
- 특정 프로퍼티는 외부에 공개는 하되, 변경은 클래스 내부에서만 하고 싶을 수 있다. 이때 접근자의 가시성을 설정할 수 있다.
  ```kotlin
  class TestUser() {
    var age: String
        private set
  }
  ```
  
### Data Class

- `equals(), hashcode(), toString()` 을 모두 자동으로 생성해준다.
  - 주 생성자에 작성된 프로퍼티에 대해서만 생성해준다.
- Data 클래스는 불변으로 생성하는것을 권장한다.
  - 이를 편하게 할 수 있도록 컴파일러는 `copy()` 메서드를 제공해준다.

### 클래스 위임: by 키워드
- 데코레이터 패턴을 사용해 상속이 허용되지 않는 클래스를 감싸서 추가적인 기능을 개발한다고 가정할때, 추가기능뿐만 아니라 원래 제공되던 기능까지 제공해야하기 때문에 필요한 코드가 많다.  
  ex) `size`, `isEmpty` 등등
- Kotlin에서는 by 키워드를 사용하여 간단하게 해결할 수 있다.
  ```kotlin
  class DelegateCollection<T>(
    innerList: Collection<T> = ArrayList<T>()
  ): Collection<T> by innerList { }
  ```

## 4.4 object 키워드: 클래스 선언과 인스턴스 생성

### object 명령어를 통한 싱글톤 객체 생성

- object 키워드를 사용해 싱글톤을 지정할 수 있다.(언어적인 차원에서 싱글톤을 보장해주는것)
  ```kotlin
  object Payroll {
    val allEmployees = arrayListOf<Person>()
  
  fun calculateSalary() { }
  }
  ```

### companion object
- Kotlin에서는 static 키워드를 지원하지 않는다. 대신 최상위 함수를 사용하는걸 권장하지만, 클래스 내부의 private 프로퍼티에 접근할 필요가 있을때도 있다.  
  이럴때 companion object를 활용할 수 있다.
- companion object에 이름을 붙일 수 있고, 인터페이스 구현, 확장함수 선언, 프로퍼티 정의 모두 가능하다.