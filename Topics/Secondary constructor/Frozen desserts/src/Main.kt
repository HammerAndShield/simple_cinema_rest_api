




// write the IceCreamOrder class here
class IceCreamOrder {
    val price: Int

    constructor(popsicles: Int){
        price = popsicles * 7
    }

    constructor(scoops: Int, toppings: Int){
        price = (scoops * 5) + (toppings * 2)
    }
}