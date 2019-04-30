package Romani;

////////////////////////////////////////////////////////////////////////////////
// MainModel.java
// ============
// Used to compare two lines by utilizing line class
//
// AUTHOR: Vincent Romani (vromani@outlook.com)
// CREATED: 7-Apr-2018
// UPDATED: 17-Apr-2018
////////////////////////////////////////////////////////////////////////////////

public class MainModel {
private Line line1, line2;

    // constructor class. creates 2 empty lines
    public MainModel() {
        line1= new Line();
        line2=new Line();
    }

    //setter method for line 1
    public void setLine1(float x1, float y1, float x2, float y2) {
        this.line1.set(x1, y1, x2, y2);
    }
    
    //setter method for line 2
    public void setLine2(float x1, float y1, float x2, float y2) {
        this.line2.set(x1, y1, x2, y2);
    }

    //returns intersect point by using line class intersect method
    public Vector2 getIntersectPoint(){
        return line1.intersect(line2);
    }

}
