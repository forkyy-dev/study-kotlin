## 자바 개발자를 위한 코틀린 입문

- [강의 링크] (https://inf.run/r9oU)

1. [변수를 다루는 방법](#1-변수를-다루는-방법)
2. [null을 다루는 방법](#2-null을-다루는-방법)
3. [Type을 다루는 방법](#3-Type을-다루는-방법)
4. [연산자를 다루는 방법](#4-연산자를-다루는-방법)
5. [제어문을 다루는 방법](#5-제어문을-다루는-방법)
6. [반복문을 다루는 방법](#6-반복문을-다루는-방법)
7. [예외를 다루는 방법](#7-예외를-다루는-방법)
8. [함수를 다루는 방법](#8-함수를-다루는-방법)
9. [클래스를 다루는 방법](#9-클래스를-다루는-방법)
10. [상속을 다루는 방법](#10-상속을-다루는-방법)
11. [접근 제어를 다루는 방법](#11-접근-제어를-다루는-방법)
12. [object-키워드를 다루는 방법](#12-object-키워드를-다루는-방법)
13. [중첩 클래스를 다루는 방법](#13-중첩-클래스를-다루는-방법)
14. [다양한 클래스를 다루는 방법](#14-다양한-클래스를-다루는-방법)
15. [배열과 컬렉션을 다루는 방법](#15-배열과-컬렉션을-다루는-방법)
16. [다양한 함수를 다루는 방법](#16-다양한-함수를-다루는-방법)
17. [람다를 다루는 방법](#17-람다를-다루는-방법)
18. [컬렉션을 함수형으로 다루는 방법](#18-컬렉션을-함수형으로-다루는-방법)
19. [이모저모](#19-이모저모)
20. [Scope Function](#20-Scope-Function)

---

### 1. 변수를 다루는 방법

- 일반 변수: var
- 불변 변수: val


> **코틀린에서는 타입을 컴파일러가 추론해준다.**

- 명시적으로 작성할 수도 있다.
- `val number: Long = 10L;`

- 초기값을 지정하지 않을 경우에는 **반드시 타입을 지정해야한다.**
- val 컬렉션에는 element를 추가할 수 있다.
    - List 객체에 값을 추가할 수 있다.

  > **웬만하면 다 불변으로 만들어라.**

- primitive vs reference
    - 실행 시 내부적으로는 primitive 타입으로 실행되지만 코드 작성 시 볼때는 하나의 Reference 타입으로 보인다.

  > **코틀린이 박싱과 언박싱을 알아서 처리해준다.**

- kotlin의 nullable

    ```kotlin
    var number3: Long? = null;
    ```

  > **코틀린은 기본적으로 모두 non-null이다.**  
  > nullable을 표시하려면 `타입?` 형태로 명시해야한다.

- kotlin의 객체 생성
    - new를 사용하면 안된다.

---

### 2. null을 다루는 방법

- ?를 어떻게 쓰느냐에 따라서 달라진다.

```kotlin
fun startWithsA1(str: String?): Boolean {
    if (str == null) {
        throw IllegalArgumentException("null이 들어왔습니다.")
    }

    return str.startsWith("A")
}

fun startWithsA2(str: String?): Boolean? {
    if (str == null) {
        return null
    }
    return str.startsWith("A")
}

fun startWithA3(str: String?): Boolean {
    if (str == null) {
        return false
    }
    return str.startsWith("A")
}
```

- **Safe Call**: null이 아니면 뒤의 메서드를 실행하고 null이면 그냥 null 처리

    ```kotlin
    val str: String? = “ABC”;
    str.length  //불가능
    str?.length //가능
    ```

- **Elvis 연산자**: 앞의 연산 결과가 null이면 뒤의 값을 반환.
    - early return 에서도 많이 사용된다.

    ```kotlin
    val str: String? = "ABC"
    str?.length ?: 0
    ```

- **null 단언!! → 확실하게 널이 아닐때만 사용할것.**
    - entity에서 최초 데이터를 조회해오기 전에만 null이고 이후로는 절대 null일 경우가 없는 경우에 매번 safe call을 하는건 비효율적이기 때문에 단언 기능이 있다.
    - 그렇다고 런타임에서 익셉션이 일언나지 않는다는 의미는 아니다.

    ```kotlin
    val str: String? = "ABC"
    str!!.length
    ```

- **플랫폼 타입**
    - 코틀린이 null 관련 정보를 알 수 없는 타입을 말한다. → Runtime Exception이 발생할 수 있다.

    ```kotlin
    public class Person {
    
    	@Nullable
    	public String getName(
    
    }
    ```

---

### 3. Type을 다루는 방법

- **기본타입**
    - 코틀린은 선언된 기본값을 보고 타입을 추론한다.
        - L을 붙이면 Long, f를 붙이지 않으면 Double
    - 코틀린은 자바처럼 암시적 타입 변환이 불가능하다.

        ```kotlin
        val number1 = 3
        
        val number2: Long = number1 //이건 에러가 난다.
        val number2: Long = number1.toLong()
        ```

- **타입 캐스팅**
    - java에서는 instanceof 를 사용해서 비교 후 처리한다.
    - kotlin에서는 `is` 를 사용하여 처리한다.
        - 스마트 캐스트:
          타입캐스팅은 명시적으로 작성하지 않아도 if문 등 앞에서 검증이 되었다면 생략이 가능하다. 코틀린이 알아서 간주한다.
        - 타입 비교 시에는 `as Person` 을 사용한다.
        -

    ```kotlin
    fun printAgeIfPerson(obj: Any) {
        //일반적인 타입캐스트
        val person = obj as Person
        
        //null이 들어올 수 있8으면
        val person = obj as? Person
    
    	//스마트 캐스트 - 앞에서 검증이 됐기 때문에 as를 붙이지 않아도 자동으로 캐스팅을 해준다.
        if (obj is Person) {
            val person = obj //as Person
            println(person.age)
        }
    
        //아닐경우
        if (obj !is Person) { ... }
    }
    
    if (obj !is Person) { ... }
    ```

- 3가지 특이한 타입
    - **Any**: 모든 객체의 최상위 타입
        - Primitive 타입의 최상위 타입도 Any 타입.
        - null은 Any로 표현할 수 없어서 Any?로 표현해야한다.
    - **Unit**: 반환값이 없는 함수의 반환 타입 (Java의 void와 유사)
        - Unit은 타입 인자로 사용이 가능하다. Java의 Void 클래스처럼
    - **Nothing**: 함수가 정상적으로 끝나지 않았다는 것을 표현하는 타입
        - 무조건 예외를 반환한다는 함수 혹은 무한루프.
        > 실제로는 거의 안쓴다.


- String interpolation / String indexing
    - Java에서는 StringBuilder, String.format()을 쓴다.
    - Kotlin에서는 `${변수}`를 사용하면 된다

  > 간단한 변수를 쓸때는 중괄호를 생략하라고 공식 컨벤션에서 말하고 있다.

    ```kotlin
    val person = Person("최태현", 100)
    val log = "사람의 이름은 ${person.name} 이고 나이는 ${person.age}세 입니다."
    ```

    - 배열처럼 대괄호를 써서 문자열의 특정 인덱스에 있는 값을 가져올 수 있다.

        ```kotlin
        val str = "ABCDE"
        val a = str[1]
        ```

---

### 4. 연산자를 다루는 방법

> 기본적인 연산자들은 모두 자바에서 사용하는것처럼 사용하면 된다.

- 객체 비교
    - Java에서는 명시적으로 compareTo를 사용해야 비교가 가능했다면
      Kotlin에서는 자동으로 compareTo를 호출한다.
    - === : 주소까지 검사 (동일성)
    - == : equals 를 자동으로 호출한다. (동등성)
- 논리 연산자: Java와 완전히 동일하다.
- 특이 연산자
    - in / !in: 컬렉션이나 범위에 포함되어 있다, 포함되어 있지 않다를 판단.
    - a..b
        - a ~ b까지의 범위 객체를 생성한다.
    - a[i] = b: a 의 i 인덱스에 값을 넣는다.
- 연산자 오버로딩

  > 코틀린은 객체마다 연산자를 직접 정의할 수 있다.

>


---

### 5. 제어문을 다루는 방법

- **Expression과 Statement**
    - Statement는 프로그램의 문장으로, 어떤 동작을 수행하는 코드 단위이다. 
    - Expression은 값을 반환하는 코드 조각으로, Statement의 일부가 될 수 있다.
    - 코틀린에서는 대부분의 제어 구조가 Expression으로 취급되어, 값을 반환하고 변수에 할당할 수 있다.
- **if - else if - else문**: 코틀린에서는 Expression이다. 그렇기 때문에 삼항연산자가 존재하지 않고 아래와 같이 바로 반환할 수 있다.

    ```kotlin
    fun getPassOrFail(score: Int): String {
        return if (score >= 50) {
            "P"
        } else {
            "F"
        }
    }
    
    fun getGrade(score: Int): String {
        return if (score >= 90) {
            "A"
        } else if (score >= 80) {
            "B"
        } else if (score >= 70) {
            "C"
        } else {
            "D"
        }
    }
    ```

- **범위**

    ```kotlin
        if (score !in 0..100) {
            throw IllegalArgumentException("score의 범위는 0~100 입니다.")
        }
        
        if (score in 0..100) {
            throw IllegalArgumentException("score의 범위는 0~100 입니다.")
        }
    ```

- **switch & when**
    - Java에서 쓰던 switch는
      Kotlin에서는 when으로 사용된다.
    - 조건에 범위 혹은 나열하는 형식도 모두 가능하다.

    ```kotlin
    fun getGradeWithWhen(score: Int): String {
    		return when (score) {
            in 90..99 -> "A"
            in 80..89 -> "B"
            in 70..79 -> "C"
            else -> "D"
        }
    }
    
    fun judgeNumber(number: Int) {
        when (number) {
            1,0,-1 -> println("어디서 많이 본 숫자입니다.")
            else -> println("처음 보는 숫자입니다.")
        }
    }
    ```

---

### 6. 반복문을 다루는 방법

- forEach
    - in 뒤에 iterable을 구현한 객체면 다 넣을 수 있다.

    ```kotlin
    for (number in numbers) {
    	println(number)
    }
    ```

- for문 ⇒ *등차수열을 사용한다.*
    - 범위를 나타내기 때문에 1~3까지 그대로.
    - `downTo`: 내려가는 방식
    - `step`: 정한 숫자값 단위로 값을 올리는 방식

  > `downTo`, `step` 모두 구현되어 있는 함수다. (**중위 함수**)

    ```kotlin
    for (i in 1..3) { ... }
    
    //하향
    for (i in 3 downTo 1) {...}
    
    //정한 숫자만큼 이동
    for (i in 1..5 step 1) {...}
    ```

- Progression & Range
    - Range클래스는 Progression 클래스를 상속받고 있음.
    - 동작 과정
        - e.g) 1..3 → 1~3까지의 등차수열 클래스를 만들어 달라는 의미.
            1. 1~3까지의 등차 수열 생성
            2. 등차수열.step 함수 호출
- while문
    - java와 완전히 동일하다.

---

### 7. 예외를 다루는 방법

- try-catch-finally
    - java와 동일하지만 Expression이기 때문에 바로 반환이 가능하다.
- Checked Exception & Unchecked Exception
    - 코틀린에서는 두가지를 구분하지 않는다.

> ***모두 Unchecked Exception이다.***


- try with resources
    - 코틀린에는 try with resources가 없다.
    - 아래와 같이 `.use` 라는 인라인 함수를 사용한다.

        ```kotlin
        fun readFileV2(path: String) {
                BufferedReader(FileReader(path)).use { reader ->
                    println(reader.readLine())
            }
        }
        ```

---

### 8. 함수를 다루는 방법

- 함수
    - 함수는 클래스 내부, 파일 최상단 등에 모두 위치할 수 있으며 하나의 파일 안에 여러개의 함수가 존재할 수 있다.
- 함수 선언 문법
    - 접근지시어: public은 생략이 가능하다.
    - 반환타입:
        - Unit은 생략이 가능하다.
        - 중괄호 대신 =을 사용한 함수의 경우 결과값이 추론이 가능할 경우 생략이 가능하다.
    - 결과값이 하나라면 Expression으로 만들 수 있다.

        ```kotlin
        fun max(a: Int, b: Int) = if (a > b) a else b
        ```

- **default parameter**

    ```kotlin
    fun repeat(str: String, num: Int, useNewLine: Boolean = false) { ... }
    ```

- **named argument**
    - Builder를 구현하지 않고도 builder와 같은 효과를 볼 수 있다.
    - java함수를 가져다 사용할때는 named argument를 사용할 수 없다.
      이유는 java가 바이트코드로 변환되었을때 파라미터명은 가지고 있지 않기 때문에.

        ```kotlin
        printNameAndGender(name = "최태현", gender = "남자")
        ```

- **같은 타입의 여러 파라미터 받기 (가변인자)**
    - `vararg` 키워드를 사용한다.
    - 배열을 인자로 넣어줄때는 앞에 * 를 넣어줘야 한다. → 스프레드 연산자

    ```kotlin
    fun printAll (vararg values: Int) {
        for (value in values) {
            println(value)
        }
    }
    
    printAll("A", "B", "C")
    
    val arr = arrayOf("A", "B", "C")
    printAll(*arr)
    ```

---

### 9. 클래스를 다루는 방법

- **클래스 생성 방법**
    - 기본적으로 public이다.
    - 생성자는 클래스명에 이어서 작성된다. (constructer 지시어는 생략 가능하다.)
    - 생성자와 필드를 하나로 처리할 수 있다.
        - 이때 생성자 안에 val로 작성하게 되면 하나의 프로퍼티로 인식해서 getter, setter를 자동으로 만들어 준다.

        ```kotlin
        class Person constructor(name: String, age: Int) {
            val name: String
            var age: Int
        }
        
        class Person (name: String, age: Int) {
            val name: String
            var age: Int
        }
        
        class Person (val name: String, var age: Int)
        ```

    - getter와 setter는 자동으로 만들어진다.
    - `.필드명` 으로 접근이 가능하다.
      (Java 파일을 가져와서 사용할때도 적용된다.)

        ```kotlin
        val person = Person("Lannstark", 27)
            println(person.name)
            println(person.age)
            person.age = 28
            println(person.age)
        ```

- **생성자에서 검증로직을 넣는다면?**
    - `init` 블록에 로직을 넣으면 된다.
      init 블록은 생성자 호출 시 실행된다.
- **생성자가 여러개라면?**

    ```kotlin
    class Person(val name: String, var age: Int) {
    		constructor(name: String) : this(name, 10)
    }
    ```

- **primary constructor & secondary constructor**
    - 주 생성자는 무조건 있어야 하지만 인자가 없을 경우에는 생략이 가능하다.
    - 부 생성자는 작성시에는 최종적으로 this를 사용하여 주 생성자를 무조건 호출해야 한다.
        - e.g) 부생성자2가 1을 호출 → 부생성자1이 내부에서 주생성자 호출 형태로!

        ```kotlin
        constructor(name: String) : this(name, 10)
        constructor() : this("이름 없음")
        ```

    - 부 생성자는 {} 바디를 가질 수 있다.

> **부 생성자 보다는 default Parameter를 사용하는것을 권장한다.**
>

- **custom getter, setter**
    - 객체의 속성을 나타내는거라면 ⇒ getter
    - 그게 아니라면 ⇒ setter

    ```kotlin
    val isAdult: Boolean
        get() = this.age >= 20
    
    val isAdult: Boolean
        get() { 
    	    this.age >= 20 
    	  }
    
    fun isAdult(): Boolean {
    		return this.age >= 20
    }
    ```

    - 프로퍼티에 대해 custom getter를 만들려면 field 변수를 사용해야 한다.
        - 왜 필드명 대신 `field` (backing field) 를 쓰냐?
          ⇒ [Class.name](http://Class.name) 호출 시 getter를 부르고, getter 내부에서 name.uppercase를 쓸 경우 또 getter를 부르기 때문에 무한루프가
          발생한다.

        ```kotlin
        val name = name
            get() = field.uppercase()
        ```

        - 사실 backing field를 쓰는것보다는 아래처럼 쓰는걸 선호한다.

            ```kotlin
            val getUppercaseName: String
                    get() = this.name.uppercase()
            ```

---

### 10. 상속을 다루는 방법

- 추상 클래스
    - Java와 크게 다를건 없음.

        ```kotlin
        abstract class Animal (
            protected val species: String,
            protected val legCount: Int
        ){
            abstract fun move()   
        }
        ```

    - 상속시에는 `extends`가 아닌 `:` 을 사용한다.

        ```kotlin
        class KotlinCat(
            species: String = "Cat",
        ) : Animal(species, 4) {
        
            override fun move() {
                println("Walking")
            }
        }
        ```

    - 추상 프로퍼티가 아니라면 오버라이드 할때는 무조건 상위 클래스의 프로퍼티에 open을 붙여줘야 한다.

        ```kotlin
        abstract class Animal (
            protected val species: String,
            protected open val legCount: Int
        )
        
        class Penguin(
        		val species: String = "Penguin"
        ) : Animal(species, 2) {
        
        		override val legCount: Int
                get() = super.legCount + this.wingCount
                
        }
        ```

- **인터페이스**
    - default 메서드는 default 지시어 없이 블록만 작성해도 된다.
    - 블록이 없는 경우에는 추상 메서드가 된다.

        ```kotlin
        interface Swimable {
        
            fun act() {
                println("Swimming")
            }
        
            fun fly()
            
        }
        ```

    - 구현은 상속과 마찬가지로 `:` 을 사용한다.
    - 중복되는 인터페이스를 특정할때는 `super<타입>.함수` 를 사용한다.

        ```kotlin
            override fun act() {
                super<Swimable>.act()
                super<Flyable>.act()
            }
        ```

    - backing field가 없는 프로퍼티를 인터페이스를 만들 수 있다.
        - getter에 대한 default 메서드 혹은 추상 메서드를 인터페이스에 만들 수 있다고 이해하면 됨

        ```kotlin
        val swimAbility: Int
                get() = 3
        ```

- **클래스를 상속할 때 주의할 점**
    - 상속해줄 클래스에는 open을 붙여줘야 한다.
    - 상속관계의 클래스 A, B가 존재하고 각각 init 메서드가 있을때 B를 생성하게 되면?!
        1. 클래스 A의 init이 호출된다.
        2. 클래스 B의 init이 호출된다.

        - 이때 A의 init에서 하위 클래스에서 오버라이드 하고 있는 필드에 접근할 경우, 초기화가 이루어지지 않은 값이 불린다.
          그렇기 때문에 상위 클래스를 설계할때 생성자 또는 초기화 블록에 사용되는 프로퍼티에는 open을 피해야 한다.
- **상속 관련 지시어 정리**
    - final: override 불가능! (*default로 보이지 않게 존재하고 있다.)*
    - open: override를 가능하게 해준다.
    - abstract
    - override: 코틀린은 반드시 작성해야한다.

---

### 11. 접근 제어를 다루는 방법

- **자바와 코틀린의 가시성 제어**
    - public: 코틀린에서는 기본적으로 public이다
    - protected: **선언된 클래스** 또는 하위 클래스에서만 접근 가능
        - 코틀린에서는 패키지를 namespace를 관리하기 위한 용도로만 사용하기 때문에.
    - internal: 같은 모듈에서만 접근 가능
        - 여기서의 모듈은 한번에 컴파일 되는 kotlin 코드
        - 바이트코드 상 public이 된다.
    - private: 그대로
- **코틀린 파일의 접근 제어**
    - protected는 파일에는 사용이 불가능하다.
    - private: 같은 파일에서만 접근이 가능하다.
- **다양한 구성요소의 접근 제어**
    - 클래스
    - 생성자
        - 생성자에 접근 지시어를 사용하려면 constructor를 같이 작성해줘야 한다.

        ```kotlin
        class Cat protected constructor () {}
        ```

    - Kotlin에서는 유틸성 클래스를 만들때는 file에 작성하면 편하다!
        - 이렇게 파일로 만들면 실제로 디컴파일 했을때는 클래스로 변경되며 정적 메서드를 가진것처럼 동작한다.

        ```kotlin
        package com.lannstark.lec11
        
        fun isDirectoryPath(path: String): Boolean {
            return path.endsWith("/")
        }
        ```

    - 프로퍼티
        - 가시성 설정 방식
            - val, var 앞에 접근 지시어를 설정할 경우 ⇒ getter, setter 모두 해당 가시성을 가진다.
            - setter만 private으로 설정하고 싶을 경우 아래에서 저렇게 설정할 수 있다.

            ```kotlin
            class Car (
                internal val name: String,
                private var owner: String,
                _price: Int 
            ) {
                var price = _price
                    private set
            }
            ```

- **Java와 Kotlin을 함께 사용할 경우 주의할 점**
    - Java는 같은 패키지의 Kotlin protected에 접근할 수 있다.

---

### 12. object 키워드를 다루는 방법

- **static 함수와 변수**
    - 코틀린에는 static이 없다. 대신 `companion object` 를 사용한다.
      클래스와 동행하는 유일한 object (인스턴스마다 생기는게 아니다.)
    - companion object도 인스턴스이기 때문에 이름도 설정할 수 있고, 인터페이스로도 만들 수 있음.

        ```kotlin
        class Person private constructor(
            var name: String,
            var age: Int
        ) {
        
        		//일반적인 companion object
            companion object {
                val MIN_AGE = 1
                fun newBaby(name: String): Person {
                    return Person(name, MIN_AGE)
                }
            }
            
            // Log 인터페이스를 상속받은 경우
            companion object Factory : Log {
                override fun log() {
                    println("Log")
                }
            }
        }
        ```

    - const 명령어
        - 값이 할당되는 시점이 달라진다.
        - 기본 타입과 String 타입에만 사용할 수 있다.

        ```kotlin
        companion object {
        
        		//컴파일 시 값이 할당된다.
            private const val MIN_AGE = 1
            
            //런타임 시 값이 할당된다.
            private val MIN_AGE = 1
            fun newBaby(name: String): Person {
                return Person(name, MIN_AGE)
            }
        }
        ```

    - `@JvmStatic`
        - 해당 어노테이션을 붙이면 Java에서 오브젝트 이름 없이도 바로 접근이 가능해진다.
- **싱글톤**
    - 코틀린에서는 클래스 명 앞에 `object` 를 붙여주면 알아서 싱글톤으로 생성된다.
- **익명 클래스**
    - Java의 경우 new XXX 하고 구현했음
    - Kotlin에서는 `object` 라는 명령어를 앞에 붙여주고 아래와 같이 구현함

        ```kotlin
        moveSomething(object : Movable {
            override fun move() {
                println("Move")
            }
        
            override fun fly() {
                println("Fly")
            }
        })
        ```

---

### 13. 중첩 클래스를 다루는 방법

- **중첩 클래스 종류**
    - static을 사용한 클래스 (권장)
    - static을 사용하지 않은 클래스
- **중첩 클래스와 내부 클래스**
    1. **바깥 클래스의 참조를 가지지 않는 내부 클래스**
        - java에서는 내부 클래스를 만들때는 외부와 연결성이 없는 클래스를 만들라고 한다.
        - 코틀린에서는 아래와 같이 만들면 기본적으로 바깥 클래스와 연결성이 없는 클래스가 만들어지기 때문에.

            ```kotlin
            class JavaHouse (
                private val address: String,
                private val livingRoom: LivingRoom
            ) {
                class LivingRoom (
                    private var area: Double
                )
            }
            ```

          위의 코드를 바이트코드로 변경 후 디컴파일 했을때 생성되는 코드.

          ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/096d92bd-bad9-4c9d-a8f9-3ddf8ee1c923/822490be-def4-4b30-b107-6b245f26c904/image.png)

    2. **바깥 클래스의 참조를 가진 내부 클래스 만들기**

        ```kotlin
        class JavaHouse (
            private val address: String,
            private val livingRoom: LivingRoom
        ) {
            inner class LivingRoom2 (
                private val area: Double
            ) {
                val address: String
                    get()  = this@JavaHouse.address
            }
        }
        ```

---

### 14. 다양한 클래스를 다루는 방법

- **Data Class**
    - DTO와 같은 클래스에 주로 이용된다.
    - Java에서는 dto 클래스를 만들려면 코드가 매우 길어진다.
      Kotlin에서는 `data` 만 앞에 붙여주면 equals, hashCode, toString 등의 메서드를 가진 객체가 생성된다.

        ```kotlin
        data class PersonDto(
            val name: String,
            val age: Int
        )
        ```

- **Enum Class**
    - class 앞에 `enum` 을 붙여주면 된다.

        ```kotlin
        enum class Country (
            private val code: String,
        ) {
            KOREA("KR"),
            USA("US"),
            JAPAN("JP")
        }
        ```

    - enum에 대한 분기처리를 할때는 when을 활용하면 매우 유용하다!!

        ```kotlin
        fun handleCountry (country: Country) {
            when (country) {
                Country.KOREA -> println("한국")
                Country.USA -> println("미국")
                Country.JAPAN -> println("일본")
            }
        }
        ```

- **Sealed Class, Sealed Interface**
    - 탄생 배경: 추상 클래스를 외부에서는 상속받지 못하게 하고 싶다라는 니즈에서!
    - 특징
        - 컴파일 타임때 하위 클래스의 타입을 모두 기억한다.
          즉, 런타임 때 클래스 타입이 추가될 수 없다.
        - 하위 클래스는 같은 패키지에 있어야 한다.
        - enum과 다른점
            - 클래스를 상속받을 수 있다.
            - 하위 클래스가 멀티 인스턴스가 가능하다.

    ```kotlin
    sealed class HyundaiCar (
        val name: String,
        val price: Long
    )
    
    class Sonata: HyundaiCar("Sonata", 30000000)
    class Grandeur: HyundaiCar("Grandeur", 40000000)
    class Avante: HyundaiCar("Avante", 20000000)
    ```

---

### 15. 배열과 컬렉션을 다루는 방법

- 배열
    - `arr.indices`: 0부터 마지막 index까지의 Range를 제공한다.
    - `arr.withIndex` :
    - `arr.plus`: 배열에 값 추가

        ```kotlin
        //생성
        val arr = arrayOf(1,2,3,4,5)
        
        //순회
        for (i in arr.indices) { ... }
        
        //index까지 제공하는 순회
        for ((idx, value) in arr.withIndex()) { ... }
        
        //삽입
        arr.plus(6)
        ```

- 컬렉션
    - 컬렉션 생성시에는 가변, 불변 여부를 설정해야한다.

      ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/096d92bd-bad9-4c9d-a8f9-3ddf8ee1c923/2fe7d2f2-5ff6-4411-8355-82f75831e16c/image.png)

    - 가변 컬렉션: MutableXXX - 컬렉션에 값을 추가, 삭제할 수 있다.
    - 불변 컬렉션: List, Set, Map - 컬렉션에 값을 추가, 삭제할 수 없다.

      > ***불변 리스트를 만들고 꼭 필요할때만 가변 리스트로 변경하는걸 추천!***
  >

    ```kotlin
    //List-----------------------
    //기본 구현체는 Array
    //불변 리스트
    val numbers = listOf(100, 200)
    
    //불변 리스트
    val mutableNumbers = mutableListOf(100, 200)
    
    //타입 추론이 가능하면 타입을 생략할 수 있다.
    val emptyList = emptyList<Int>()
    
    //Set-----------------------
    //List와 다를게 없다!
    //기본 구현체는 LinkedHashSet
    
    //Map-----------------------
    //생성1
    val oldMAp = mutableMapOf<Int, String>()
    oldMAp[1] = "MONDAY"
    oldMAp[2] = "TUESDAY"
        
    //생성2
    mapOf(1 to "MONDAY", 2 to "TUESDAY")
    
    //순회
    for (key in newMap.keys) {
        println("${key} ${newMap[key]}")
    }
    
    for ((key, value) in newMap.entries) {
        println("${key} ${value}")
    }
    ```

- null 가능성
    - List<Int?> : 리스트 내부에 들어오는 값이 null일 수 있다.
    - List<Int>? : 해당 리스트 객체가 null이 들어올 수 있다.
    - List<Int?>? : 해당 리스트가 null일 수 있고, 내부에 들어오는 값도 null일 수 있다.
- java와 함께 쓸때 주의할 점
    - Java는 불변/가변을 구분하지 않으며, nullable / non-nullable을 구분하지 않는다.
      즉, 감안하고 사용해야 한다. 또는 `unmodifableXXX()`를 사용해라!
    - 코틀린에서 Java 코드를 가져와서 쓸때는 플랫폼 타입을 신경써야 한다.

---

### 16. 다양한 함수를 다루는 방법

- **확장 함수**
    - 탄생 배경
        - Java와의 100% 호환을 목표로 하고 있어서 자연스럽게 코틀린 코드를 추가할 수는 없을까? 라는 고민이 있었음.
        - 클래스 안에 있는 메서드처럼 호출할 수 있지만, 함수는 밖에 만들 수 있게 하자! 라는 니즈로 발전!
    - `확장하려는 클래스.확장함수명` 형태로 작성한다.
      this(수신객체)를 통해 내부의 값에 접근한다.

        ```kotlin
        fun String.lastChar(): Char = this[this.length - 1]
        ```

    - 확장함수에서는 원본 클래스에 있는 private, protected 멤버를 가져올 수 없다.
    - 멤버함수와 확장함수의 시그니처가 같을 경우에는 멤버함수가 먼저 호출된다.
    - 확장함수가 오버라이드가 될 경우에는 타입에 따라서 호출되는게 달라진다.
    - Java에서 Kotlin의 확장함수를 쓸때는 정적 메서드처럼 쓸 수 있다.
- **infix(중위) 함수**

    ```kotlin
    infix fun Int.add2(other: Int): Int {
    	return this + other
    }
    
    infix fun Int.add2(x: Int) = this + x
    
    3 add2 4
    ```

- **inline 함수**
    - 함수가 호출되는 대신, 함수를 호출한 지점에 함수 본문을 그대로 복붙하고 싶은 경우.
        - `inline fun Int.add2(x: Int) = this + x`
    - 함수를 파라미터로 전달할때 오버헤드를 줄일 수 있다.
    - **But. 성능측정과 함께 신중하게 사용되어야 한다.**
- **지역 함수**
    - 함수 안에 함수를 선언할 수 있다.
    - depth도 깊어지고 깔끔하지 않기 때문에 비추~

        ```kotlin
        fun createPerson(firstName: String, lastName: String): Person {
            fun validateName(name: String, fieldName: String) {
                if (name.isEmpty()) {
                    throw IllegalArgumentException("Cannot create person with empty $fieldName")
                }
            }
            
            validateName(firstName, "firstName")
            validateName(lastName, "lastName")
            
            return Person(firstName, lastName, 30)
        }
        ```

---

### 17. 람다를 다루는 방법

- **Java에서 람다를 다루기 위한 노력**
  - 
- **코틀린에서의 람다**
    - 코틀린에서는 함수는 일급함수라서 변수에 할당할수도 있고 인자로 넘겨줄수도 잇다.
    - 형태:  `(파라미터 타입) → 반환타입`
    - 사용할때는 해당 함수의 마지막 파라미터의 타입이 람다고 중괄호 형식으로 전달할 경우에는 괄호 외부에 넣을 수 있다.
    - 익명 함수 사용시 파라미터가 하나면 화살표를 사용하지 않고 `it` 을 사용하여 간단하게 표현할 수 있다.
    - 람다 내부에 여러 줄을 작성할 수 있으며, 마지막에 작성되는 Expression이 반환값이 된다.

    ```kotlin
    // 생성 방식 1
    val isApple1: (Fruit) -> Boolean = fun(fruit: Fruit): Boolean {
    		return fruit.name == "사과"
    }
    
    //  생성 방식 2 : 주로 이 방법을 사용한다.
    val isApple2: (Fruit) -> Boolean = { fruit: Fruit -> fruit.name == "사과" }
    
    // it을 사용한 케이스
    val isApple2: (Fruit) -> Boolean = { it.name == "사과" }
    
    fun filterFruits(
        fruits: List<Fruit>, filter: (Fruit) -> Boolean
    ): List<Fruit> {
        val result = mutableListOf<Fruit>()
        for (fruit in fruits) {
            if (filter(fruit)) {
                result.add(fruit)
            }
        }
        return result
    }
    
    //사용 시 이런식으로 전달이 가능하다.
    filterFruits(fruits, isApple1)
    filterFruits(fruits, isApple2)
    filterFruits(fruits, fun(fruit: Fruit): Boolean {
    		return fruit.name == "사과"
    })
    filterFruits(fruits) {fruit: Fruit -> fruit.name == "사과"}
    ```

- **클로저**
    - Java: 람다 외부에 있는 변수를 사용할때는 해당 변수가 무조건 final이어야 한다.
    - Kotlin: 람다 외부에 있는 변수가 final이 아니더라도 사용할 수 있다.

  How?

    - 코틀린은 람다가 시작하는 지점에 참조하고 있는 변수들을 모두 캡쳐하여 그 정보를 가지고 있다.
      이런 데이터 구조를 **Closure** 라고 부른다.

    ```kotlin
    var targetFruitName = "수박"
    targetFruitName = "사과"
    
    //해당 람다는 함수가 실행되는 시점에 targetFruitName 값을 캡쳐해서 가지고 있는다.
    val isApple: (Fruit) -> Boolean = { it.name == targetFruitName }
    ```

- **try with resources**
    - use는 Closable이라는 클래스에 대한 확장함수다.
      또한 inline 함수다.

---

### 18. 컬렉션을 함수형으로 다루는 방법

- **Filter 와 Map**
    - filter / filterIndexed / map / mapIndexed / mapNotNull
- **다양한 컬렉션 처리 기능**
    - `all`: 조건을 모두 만족하면 true, 그렇지 않으면 false
    - `none`: 조건을 모두 불만족하면 true, 그렇지 않으면 false
    - `any`: 조건을 하나라도 만족하면 true, 그렇지 않으면 false
    - `count`: list의 size와 같은 기능
    - `sortedBy`: 오름차순
    - `sortingByDescending` : 내림차순
    - `distinctBy` : 중복 제거
    - `first`: 첫번째 값 (무조건 null 아니어야함)
    - `firstOrNull`: 첫번째 값 혹은 null을 반환
    - `last`: 마지막 값 (무조건 null 아니어야함)
    - `lastOrNull`: 마지막 값 혹은 null을 반환
- **List를 Map으로**
    - `groupBy`: 조건을 키로 map이 생성됨. value는 List가 된다.
        - 키와 값을 모두 설정할수도 있다.

            ```kotlin
            fruits.groupBy({ fruit -> fruit.name }, {fruit -> fruit.factoryPrice})
            ```

    - `assosiateBy`: `id - 단일객체` 형태의 Map을 만듬. → 고유한 값들로 구성되었을 경우에 사용
- **중첩된 컬렉션 처리**
    - `List<List<Fruit>>` 형태의 데이터라면?
    - `flatMap`: 조건에 맞춰서 단일 리스트로 만들 수 있다.
    - `flatten`: 그냥 단순히 단일 리스트로 만든다.

---

### 19. 이모저모

- **Type Alias와 as Import**
    - TypeAlias

        ```kotlin
        typealias FruitFilter = (Fruit) -> Boolean
        fun filterFruits( fruits: List<Fruit>, filter: FruitFilter) { ... }
        ```

    - asImport

        ```kotlin
        import com.lannstark.lec19.a.printHelloWorld as printA
        printA()
        ```

- **구조분해와 componentN 함수**
    - 구조분해
        - 실제로 내부 동작은 componentN 함수가 호출되는것이다.
        - DataClass는 기본적으로 componentN이라는 함수가 만들어진다.
          N번째 프로퍼티를 가져올 수 있는 함수다.
          ***그래서 구조분해 시 순서를 바꿔서 꺼내게 되면 값이 바뀌어서 나온다.***

            ```kotlin
            val person = Person("석호", 30)
            val (name, age) = person
            
            //실제 동작 과정
            val name = person.component1()
            val age = person.component2()
            ```

        - DataClass가 아닐 경우 직접 componentN 함수를 직접 구현해주면 된다.
            - 연산자의 속성을 가지고 있어서 operator라는 키워드를 붙여야한다.

            ```kotlin
            class Person(
            	val name: String,
            	val age: Int
            ) {
            	operator fun component1() {
            			return this.name
            	}
            	operator fun component2() {
            			return this.age
            	}
            }
            
            ```

- **Jump와 Label**
    - Jump
        - return, break, continue 모두 java와 동일
        - forEach 내에서 break와 continue를 쓰고 싶다면? ⇒ ***쓰지마셈***

            ```kotlin
            run {
            	list.forEach { number ->
            		if(number == 2) {
            		
            				//break
            				return@run
            				
            				//continue
            				return@forEach
            		}
            	}
            }
            ```

    - **Label**: 특정 Expression에 `라벨이름@`을 붙여 하나의 라벨로 간주하고, break, continue, return 등을 사용하는 기능 ⇒ ***쓰지마셈***
        - 아래 코드처럼 `loop@` 을 가리켜서 전체를 break 시킨다.

        ```kotlin
        loop@ for (i in 1..100) {
        		for (j in 1..10) {
        				if(j == 2) {
        					break@loop
        				}
        		}
        }
        ```

- **TakeIf와 TakeUnless**
    - takeIf: 주어진 조건을 만족하면 해당 값을 반환,ㄴㅇ 그렇지 않으면 null 반환

        ```kotlin
        fun getNumber(): Int? {
        		return number.takeIf( it > 0 )
        }
        ```

    - takeUnless: 주어진 조건을 만족하지 않으면 값을 반환, 그렇지 않으면 null 반환

        ```kotlin
        fun getNumber(): Int? {
        		return number.takeUnless( it <= 0 )
        }
        ```

---

### 20. Scope Function

- **scope funtion이란?**
    - 람다를 활용하여 일시적인 영역을 형성하는 함수
    - 장점
        - 코드를 간결하게 한다.
        - 메서드 체이닝이 가능해진다.
- **scope function의 분류**
    - let, run: 람다의 결과를 반환
    - also, apply: 객체 자체를 반환

        ```kotlin
        val value1 = person.let { it. age} //returns age
        val value1 = person.run { this. age} //returns age
        val value1 = person.also { it. age} //returns person
        val value1 = person.apply { this. age} //returns person
        ```

        - let은 파라미터로 일반 함수를, run은 확장함수를 받는다.
          그래서 let에서는 it을 쓸 수 있고, run은 this를 써야한다.
    - with(확장함수 아님)
        - this로 값을 접근한다.
- **언제 어떤 scope function을 사용해야 할까?**
    - let
        - 하나 이상의 함수를 call chain 결과로 호출 할 때
        - non-null인 값에 대해서만 코드 블록을 실행할때

        ```kotlin
        val strings = listOf("APPLE", "CAR")
        strings.map { it.length }
        	.filter { it > 3 }
        	.let(::println)
        
        val length = str?.let {
        		println(it.uppercase())
        		it.length
        }
        ```

    - run
        - 객체 초기화와 반환 값의 계산을 동시에 해야할 때

            ```kotlin
            val person = Person("석호", 30).run(personRepository::save)
            ```

    - apply, also
        - 객체 자체가 반환되기 때문에 객체 설정을 할때 객체를 수정하는 로직이 call chain 중간에 필요할 경우
        - TextFixture 만들때

            ```kotlin
            fun createPerson(
            		name: String,
            		age: Int,
            		hobby: String
            ): Person {
            		return Person(
            			name = name,
            			age = age
            		).apply {
            				this.hobby = hobby
            		}
            }
            
            mutableListOf("사과", "수박")
            	.also { println() }
            	.add("참외")
            ```

    - with
        - 특정 객체를 다른 객체로 변환할때 모듈간의 의존성 때문에 정적 팩토리 혹은 toClass 함수를 만들기 어려울때

            ```kotlin
            return with(person) {
            		PersonDto(
            				name = name,
            				age = age
            		)
            }
            ```

- **scope function과 가독성**