## 코틀린 고급편 강의

- [강의 링크](https://inf.run/fA29R)


### 섹션 2 - 제네릭

#### 1강. 제네릭과 타입 파라미터

- 제네릭 클래스 작성법
  ```kotlin
    class 클래스명<타입> { ... }
  ```

- 다음 상황에서는?
  - `Animal <- Fish <- Carp, GoldFish` 형태의 상속관계를 가진 클래스
  - 케이지 간의 이동을 하려면 에러가 발생하는데 원인은?
  ```kotlin
    class GenericCage<T : Animal> { ... }

    val fishCage = GenericCage<Fish>()
    val goldfishCage = GenericCage<Fish>()
  
    goldfishCage.add(GoldFish("금붕어"))
    fishCage.add(Goldfish("금붕어")) //가능하다!
    fishCage.moveFrom(goldfishCage) // Type Mismatch Error
  ```

상속 타입이 들어가는 자리에 하위 타입이 다시 들어갈 수 있다.  
하지만 위의 경우에는 `Cage<Fish>`의 자리에 `Cage<GoldFish>`를 넣으려 한것이기 때문에 에러 발생.  
이것에 대해 GenericCage는 **무공변하다** 라고 표현한다.


---

#### 2강. 배열과 리스트, 제네릭과 무공변

- Java의 Array는 **공변하다**. 그래서 실제로는 에러가 나지만 컴파일 단계에서는 에러가 나지 않는 코드를 작성하는게 가능하다.
  ```java
  String[] strs = new String[] {"A", "B", "C"};
  Object[] objs = strs;
  objs[0] = 1; 
  ```

- Java의 List는 무공변하다. 그래서 Array보다는 List를 활용하는게 좋다.

---


#### 3강. 공변과 반공변

Kotlin에서는 변성을 줄 수 있다.

1. 공변(variant)
   - 함수 파라미터의 입장에서 생산만 한다.
   - ex) Cage<Fish> 타입의 파라미터에 Cage<GoldFish>를 넣을 수 있도록 해준다.
   - 방법: 타입 파라미터 앞에 out이라는 키워드를 추가한다.
     - out을 **variance annotation**이라고 부른다.
     ```kotlin
     fun moveFrom(cage: GenericCage<out T>) { ... }
     ```
   - 위의 코드에서 `out`을 사용하면 파라미터로 받게 된 cage 객체는 해당 함수 내에서 **데이터를 조회만 가능하다**.
   - 만약 추가할 수 있다면 GoldFish 타입이 아닌 다른 객체가 저장될 수 있게 되기 때문에.
     ```kotlin
     fun moveFrom(cage: GenericCage<out T>) {
         cage.put(GoldFish("금붕어")) //Error
     }
     ```
2. 반공변(contra-variant)
   - 함수 파라미터의 입장에서 소비만 한다.
   - 위의 케이스와 반대의 케이스로 파라미터에 상위 타입을 넣고 싶다면 `in`을 사용하면 된다.
   - `in`을 사용하게 되면 파라미터로 받은 cage 객체는 해당 함수 내에서 **데이터를 추가만 할 수 있게 된다**.
     ```kotlin
     // T는 GoldFish라고 가정
     fun moveTo(cage: GenericCage<in T>) { ... }
     
     val goldFishCage = GenericCage<GoldFish>()
     goldFishCage.put(GoldFish("금붕어"))
     
     val fishCage = GenericCage<Fish>()
     goldFishCage.moveTo(fishCage)
     ```

---

#### 4강. 선언 지점 변성 / 사용 지점 변성

- Java의 `<? extends T>` => Kotlin의 `out` / 공변
  ```java
  List<Integer> ints = List.of(1,2,3);
  List<? extends Number> nums = ints;
  ```
  ```kotlin
  val goldFishCage: GenericCage<GoldFish> = GenericCage()
  val fishCage: GenericCage<out Fish> = goldFishCage3
  ```
- Java의 `<? super T>` => Kotlin의 `in` / 반공변
  ```java
  List<Number> numbers = List.of(1,2,3);
  List<? super Integer> ints = numbers;
  ```
  ```kotlin
  val fishCage: GenericCage<Fish> = GenericCage()
    val goldFishCage: GenericCage<in GoldFish> = fishCage
  ```
  
- 대표적인 사용 사례
  - 공변: List<out E> 생산만 한다. 
  - 반공변: Comparable<in T> 소비만 한다.

---

#### 5강. 제네릭 제약과 제네릭 함수

---

#### 6강. 타입 소거와 Star Projection

---

#### 7강. 제네릭 용어 정리 및 간단한 팁