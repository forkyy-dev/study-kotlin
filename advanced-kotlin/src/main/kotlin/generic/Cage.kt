package generic

fun main() {
    val cage = Cage()
    cage.put(Carp("잉어"))

    //아래와 같이 꺼내면 Animal 타입이 나오기 때문에 에러 발생.
    //val carp1: Carp = cage.getFirst()

    //1번 방식
    val carp1: Carp = cage.getFirst() as Carp

    //2번 방식 - Safe Type Casting과 Elvis Operator를 사용
    val carp2: Carp = cage.getFirst() as? Carp ?: throw IllegalArgumentException()

    //3번 방식 - 제네릭
    val genericCage = GenericCage<Carp>()
    genericCage.put(Carp("잉어"))
    val carp3: Carp = genericCage.getFirst()


    // 공변
    val goldFishCage = GenericCage<GoldFish>()
    goldFishCage.put(GoldFish("금붕어"))

    val fishCage = GenericCage<Fish>()
    fishCage.moveFrom(goldFishCage)


    // 반공변
    val fishCage2 = GenericCage<Fish>()
    val goldFishCage2 = GenericCage<GoldFish>()
    goldFishCage2.put(GoldFish("금붕어"))
    goldFishCage2.moveTo(fishCage2)

    val goldFishCage3: GenericCage<GoldFish> = GenericCage()
    val fishCage3: GenericCage<out Fish> = goldFishCage3

    val fishCage4: GenericCage<Fish> = GenericCage()
    val goldFishCage4: GenericCage<in GoldFish> = fishCage4
}



class Cage {
    private val animals: MutableList<Animal> = mutableListOf()

    fun getFirst(): Animal {
        return animals.first()
    }

    fun put(animal: Animal) {
        this.animals.add(animal)
    }

    fun moveFrom(cage: Cage) {
        this.animals.addAll(cage.animals)
    }
}

class GenericCage<T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T {
        return animals.first()
    }

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(cage: GenericCage<out T>) {
        this.animals.addAll(cage.animals)
    }

    fun moveTo(otherCage: GenericCage<in T>) {
        otherCage.animals.addAll(this.animals)
    }
}