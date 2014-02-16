
package denguesimulation;

import java.util.ArrayList;

public class Person {
  int state = 1;
  int currStateTimeStep; //counter for how many time steps a person has been in that current state 
  ArrayList<Coordinates> neighborCoordinates = new ArrayList();
  Coordinates coordinates;
  
  public Person(int x, int y) {
    this.coordinates = new Coordinates(x, y);
  }

  public ArrayList<Coordinates> getNeightbors() {
    neighborCoordinates.add(getTopLeft());
    neighborCoordinates.add(getTopMid());
    neighborCoordinates.add(getTopRight());
    neighborCoordinates.add(getLeft());
    neighborCoordinates.add(getRight());
    neighborCoordinates.add(getBottomLeft());
    neighborCoordinates.add(getBottomMid());
    neighborCoordinates.add(getBottomRight());
    return neighborCoordinates;
  }
  
  private Coordinates getTopLeft() {
    int x = coordinates.getX() - 1;
    int y = coordinates.getY() - 1;
    
    return new Coordinates(x, y);
  }
  
  private Coordinates getTopMid() {
    int x = coordinates.getX() - 1;
    int y = coordinates.getY();
    
    return new Coordinates(x, y);    
  }
  
  private Coordinates getTopRight() {
    int x = coordinates.getX() - 1;
    int y = coordinates.getY() + 1;
    
    return new Coordinates(x, y);
  }
  
   private Coordinates getLeft() {
    int x = coordinates.getX();
    int y = coordinates.getY() - 1;
    
    return new Coordinates(x, y);
  }
   
   private Coordinates getRight() {
    int x = coordinates.getX();
    int y = coordinates.getY() + 1;
    
    return new Coordinates(x, y);
  }
   
  private Coordinates getBottomLeft() {
    int x = coordinates.getX() + 1;
    int y = coordinates.getY() - 1;    
    
    return new Coordinates(x, y);
  }
  
  private Coordinates getBottomMid() {
    int x = coordinates.getX() + 1;
    int y = coordinates.getY();    
    
    return new Coordinates(x, y);
  }
  
  private Coordinates getBottomRight() {
    int x = coordinates.getX() + 1;
    int y = coordinates.getY() + 1;    
    
    return new Coordinates(x, y);
  }
  
  public Coordinates getCoordinates() {
    return coordinates;
  }
  
  public void setState(int state) {
    this.state = state;
  }
  
  public int getState() {
    return state;
  }
  
  public boolean isSusceptible() {
    if(state == 1) {
      return true;
    } else {
      return false;
    }
  }  
  
  public boolean isInfected() {
    if(state == 2) {
      return true;
    } else {
      return false;
    }
  }
  
  public boolean isResistant() {
      if(state == 3) {
      return true;
    } else {
      return false;
    }  
  }
  
  public int getCurrStateTimeStep() {
    return currStateTimeStep;
  }
  
  //for when the person changes state (e.g. from susceptible to infected)
  public void resetCurrStateTimeStep() {
    currStateTimeStep = 0;
  }
  
  public void incCurrStateTimeStep() {
    currStateTimeStep++;
  }
}


