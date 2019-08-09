import scala.collection.mutable.HashMap

// NOTE: The entry point for a basic Scala program is whatever "object" has a "main" function in it.
// If this object is instead declared as a class the compiler throws an error because the main funciton
// won't be compiled to a static java function, as is expected of a main entry point.
object PracticeMain {
  def main(args: Array[String]): Unit = {

    // val runTestJson = new RunTestJson
    val runComplexTest = new ComplexJsonParsing
  }
  def mainPractice: Unit = {
    println("Hello, world!")

  // Create a practice object that contains all the code I wrote in the online editor.
    // Recall that when an object is created in Scala ALL STATEMENTS INSIDE THE OBJECT ARE EXECUTED!!!
    // So all the practice code will execute
    val prac = new Practice

    // NOTE: Need to add the '_' to indicate that it's a partially applied function
    // NOTE: in order to tabe advantage of the partial application /currying you have to store the intermediate
    // function away and use THAT to continue the operation. The old commented code below is an incorrect
    // implementation of this, as it's creating two separate partial functions on each call.
    // println(prac.curriedFunction(2)_)// should print 2, maybe, or perhaps it's a function
    // println(prac.curriedFunction(2))// this time it should have all the curred values necessary to print the product: 4

    // CORRECT - store the intermediate partial function
    var pf = prac.curriedFunction(2)_
    println(pf)// prints that pf is a function
    println(pf(2))// this time it should have all the curred values necessary to print the product: 4

    // print the cube of 3; 27
    // NOTE: again need to add the '_' to indicate partial application, although in this case since there are 3 parameters
    // and 2 that haven't been supplied, we need to use '_' twice
    // println(prac.cCube(3)(_)(_))// pass the first param, should print a function type (maybe)
    // println(prac.cCube(3)(3)_)// pass the second and last param, should now print 27

    //The code above is incorrect, as we have to store away the partially applied function
    var pcf = prac.cCube(3) _
    println(pcf) // prints function, as expected
    // NOTE: you don't need an additional '_' after the call to the partial function because it seems that Scala already
    // knows that this is a partial implementation of the function cCube
    var pcf2 = pcf(3) // another partial function with just the last missing param.
    println(pcf2) // prints function, as expected
    println(pcf2(3)) // Should print 27

    // partial product
    var pp4 = prac.cMult5(5) _
    var pp3 = pp4(4)
    var pp2 = pp3(3)
    var pp1 = pp2(2)
    println("partial product after storing and calling partial funcionts:" + pp1(1))
    // We can't use a loop to re-assign a partial function to a container as the partial functions won't have the same
    // function signatures.
    // for(i <- (2 to 4).reverse) pp4 = pp4(i)
    // println("final product after looping through a range to supply values to the partially applied funcitons: " + pp(1))
  }
}

//NOTE The primary constructor is the only one that needs to specify the params as "val" or maybe "var"

// Primary constructor with public member variables. All variables will have getters and setters
// created for it at compile time
class Person(var name: String, val age: Int) { parent =>

// Marks the member variable 'name' as instance private to 'this' instance of Person.
// No getter or setter is created for members with this modifier
// class Person(private[this] val name: String, val age: Int) {

// Primary constructor with the name variable set to private, meaning that 'name' will only be accessible
// from within an instance of Person, AND from other instances of Person objects.
// class Person(private val name: String, val age: Int) {
 	var location = ""
    println("Person '"+ this.name +
          "' created with age : " + age.toString +
          " and no location set")

  // Call the primary constructor
  def this(name: String, age: Int, location: String) {
    this(name, age)
    this.location = location

    println("Location for '" + this.name +"' set to: " + location)
  }

  // will print the name of the friend Person that is passed in IFF the field is not instance-private
  def myFriend(friend : Person) {
    // println("My name is " + name + " and my friends name is " + friend.name)
    println("my name is " + name +
            " and I cannot get my friend name, but my friend is " + friend.age +
            " years old!")
  }

  def description = name + " is seated at location: " + location

  // inner class describing the contents of a persons pocket
  class PersonsPocket(var contents: String) {
    def whatsInside = parent.name + " has " + contents + " in their pocket!"
  }

  var pockets = new HashMap[String, PersonsPocket]
}

class Practice {
  // return a curried multiplier, i.e., a function that multiplies n (2) values, which can be supplied at different
  // times
  def curriedFunction(x:Int)(y:Int):Int = x*y
  // curried cube generator
  // NOTE: not really a cube generator since z/x/y can be different values.
  def cCube(x: Int)(y: Int)(z: Int): Int = (x*y*z)

  // curried function that multiplies 5 ints
  def cMult5(a: Int)(b: Int)(c: Int)(d: Int)(e: Int) : Int = a*b*c*d*e

  // an attempt at a curried accumulator
  // def cAccumulator(a: Int = 0)(b: Int*) : Int = a + b

  // procedure to let us know what's going on
  // procedure does not have a return type
  def logInt(n: Int) {
    println("Checking '" + n + "' for foo/bar/baz compatibility")
  }

  def myprint(left: String = "", right: String = "", center: String = "") {
    print(left + center + right)
  }

  // procedure to count down from n to 0
  def countdown(n: Int) {
    myprint("[")
    for (i <- (0 to n).reverse) myprint(left = " ", center = i.toString(), right = ",")
    myprint("]")
  }

  // foobarbaz : takes in variable args and prints to the console based on the number
  // functions have a return type, even if it's Unit
  def foobarbaz(ns: Int*): Unit = {
    for (n <- ns) {
      logInt(n)
      if (n % 3 == 0 && n % 5 == 0) println("baz") else if (n % 5 == 0) println("bar") else if (n % 3 == 0) println("foo") else println(n)
    }
  }

  // create a vector of values via yield command
  var x = for (i <- 1 to 20) yield i
  // pass the generated vector into a function that expects a variable amount of args,
  // this is possible by annotating the argument with the ":_*" char sequence
  // foobarbaz(x:_*)

  // call function with a variable set of args that aren't in a container of some sort
  // foobarbaz(1,2,3,4,5)

  // countdown(10)

  // create a new person object witht he default constructor
  val p = new Person("foo", 123)
  // create a new person object with the location set to oxnard
  val q = new Person("jim", 1234, "oxnard")
  // another person for later tests
  val r = new Person("hieu", 1089)

  println(q.description)
  println(p.description)
  q.location = "sea"
  println(q.description)

  // Since name is object-private, we can pass Person q into Person p and have Person p access the
  // name of Person q. This is possible because the object-private variables are accessible
  // to all instances of the object.
  // Also works for public fields.
  // Error if Person.name is instance private
  p.myFriend(q)

  r.description
  r.name = "eddie"
  r.description
  // Cannot directly access the name field because it's private to Person objects.
  // uncomment to see the error
  // println(p.name)

  // Create a new pocket and put something in it. We use the instance of Person, 'r', to create
  // the pocket. Remember that each instance has their own class PersonsPocket, so you can't have
  // Person A create a PersonPocket in Person B
  r.pockets("front") = new r.PersonsPocket("lint")
  val backPocket = new r.PersonsPocket("wallet")
  //p.pockets("back") = backPocket
  // check that the pocket contains what we put in it, in this case, lint
  println(r.pockets("front").whatsInside)
}
