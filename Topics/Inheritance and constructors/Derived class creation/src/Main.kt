





open class Employee(val name: String, val age: Int, var yearsOfWork: Int = 0)

class Programmer : Employee {
    // Write your code here
    constructor(name: String, age: Int, yearsOfWork: Int) : super(name, age, yearsOfWork)
    constructor(name: String, age: Int) : super(name, age)
}