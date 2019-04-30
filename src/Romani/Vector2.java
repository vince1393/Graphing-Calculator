////////////////////////////////////////////////////////////////////////////////
// Vector2.java
// ============
// a general purpose class to define 2D vector
//
// AUTHOR: Vincent Romani (vromani@outlook.com)
// CREATED: 2018-01-26
// UPDATED: 2018-02-23
////////////////////////////////////////////////////////////////////////////////

package Romani;

public class Vector2 {

    //variables for vector object. X and Y values.
    public float x, y;

    //default constructor
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    //constructor for x and y inputs
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //set method for x and y inputs
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //set method for vector input. Sets vector object to passed vector
    public void set(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    //get method for returning x value
    public float getX() {
        return x;
    }

    //get method for returning y value
    public float getY() {
        return y;
    }

    //Override for toString. Outputs text in nicely formatted text.
    @Override
    public String toString() {
        return String.format("%s%3f%s%3f%s", "Vector2(", this.x, ",", this.y, ")");
    }

    //override for clone method. Simply creates a new version of the current object
    @Override
    public Vector2 clone() {
        return new Vector2(this.x, this.y);
    }

    //add method for adding passed vector to current vector. Returns current vector.
    public Vector2 add(Vector2 rhs) {
        this.x += rhs.x;
        this.y += rhs.y;
        return this;
    }

    //subtract method for subtractingpassed vector to current vector. Returns current vector.
    public Vector2 subtract(Vector2 rhs) {
        this.x -= rhs.x;
        this.y -= rhs.y;
        return this;
    }

    //scale method for multiplying passed float value to current vector. Returns current vector scaled.
    public Vector2 scale(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    //dot method for dotting two vectors. Returns float value of two vectors.
    public float dot(Vector2 rhs) {
        return this.x * rhs.x + this.y * rhs.y;
    }

    //gets the length of current object using formula. returns value as a float.
    public float getLength() {
        return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    //normalizes Vector by dividing both X and Y by the length.
    public Vector2 normalize() {
        //gets length and divides 1 by length to improve performance.
        float l = 1 / getLength();
        //sets the current vector to the calculated value
        set(this.x * l, this.y * l);
        return this;
    }

}
