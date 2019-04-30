////////////////////////////////////////////////////////////////////////////////
// Line.java
// ============
// Line class using Parametric Form
//
// AUTHOR: Vincent Romani (vromani@outlook.com)
// CREATED: 2018-01-26
// UPDATED: 2018-02-23
////////////////////////////////////////////////////////////////////////////////

package Romani;

public class Line {

    //declaring vectors for line object.
    private Vector2 point;
    private Vector2 direction;

    //default constucter for line class. Creates point and direction with 0,0 points.
    public Line() {
        point = new Vector2(0, 0);
        direction = new Vector2(0, 0);
    }

    //constructor for 2 vectors, a point and a direction vector
    public Line(Vector2 point, Vector2 direction) {
        this.point = point;
        this.direction = direction;
    }

    //constructor for 2 float values, a slope value and an x-intercept value
    public Line(float slope, float intercept) {
        this.point = new Vector2(0, intercept);
        this.direction = new Vector2(1, slope);
    }

    //constructor for a line given 2 x and y points.
    public Line(float x1, float y1, float x2, float y2) {
        //sets point value to first set of values by creating new vector
        this.point = new Vector2(x1, y1);
        //sets direction point by subtracting the second points by the point vector
        this.direction = new Vector2(x2, y2).subtract(new Vector2(x1, y1));
    }

    //set method for 2 vectors, a point and a direction vector
    public void set(Vector2 point, Vector2 direction) {
        this.point = point;
        this.direction = direction;
    }

    //set method for 2 float values, a slope value and an x-intercept value
    public void set(float slope, float intercept) {
        this.point = new Vector2(0, intercept);
        this.direction = new Vector2(1, slope);
    }

    //set method for a line given 2 x and y points.
    public void set(float x1, float y1, float x2, float y2) {
        //sets point value to first set of values by creating new vector
        this.point = new Vector2(x1, y1);
        //sets direction point by subtracting the second points by the point vector
        this.direction = new Vector2(x2, y2).subtract(new Vector2(x1, y1));
    }

    //method for setting point value with passed vector
    public void setPoint(Vector2 point) {
        this.point = point;
    }

    //method for getting point value
    public Vector2 getPoint() {
        return point;
    }

    //method for setting direction value with passed vector
    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    //method for getting direction
    public Vector2 getDirection() {
        return direction;
    }

    //Override for toString, formatted with all necessary info
    @Override
    public String toString() {
        //format below by line
        return String.format("%s"
                + "%s"
                + "%12s%3.3f%s%3.3f%s"
                + "%12s%3.3f%s%3.3f%s",
                //output:
                "Line\n",
                "====\n",
                "Point: (", this.point.x, ", ", this.point.y, ")\n",
                "Direction: (", this.direction.x, ", ", this.direction.y, ")\n"
        );
    }

    //intersect method used for calculating the intercept point of two 2D vectors
    public Vector2 intersect(Line line) {
        //define variables a,b,c
        float a = direction.y;
        float b = -direction.x;
        float c = direction.y * point.x - direction.x * point.y;

        //calculate variables d,e,f
        float d = line.getDirection().y;
        float e = -line.getDirection().x;
        float f = line.getDirection().y * line.getPoint().x - line.getDirection().x * line.getPoint().y;

        //calculate determinant
        float determinent = a * e - b * d;

        //call default constructor to create new intersect with 0,0 values
        Vector2 intersectPoint = new Vector2();

        //checks if the points actually intersect
        if (this.isIntersected(line)) {
            //if they do intersect, performs calculations.
            intersectPoint.set((c * e - b * f) / determinent, (a * f - c * d) / determinent);

        }
        else{
            intersectPoint.set(Float.NaN, Float.NaN);
        }
        //returns either (0,0) point if intersection does not exist, or the intersection point
        return intersectPoint;
    }

    //method for checking if two lines intersect.
    public boolean isIntersected(Line line) {
        //defines variables a,b,d,e
        float a = direction.y;
        float b = -direction.x;

        float d = line.getDirection().y;
        float e = -line.getDirection().x;

        //calculates determinant and checks if it equals 0
        if ((a * e - b * d) == 0) {
            //if it is 0, returns false indicating they do not intersect
            return false;
        } //otherwise returns true value
        else {
            return true;
        }
    }
}
