/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package denguesimulation;

import java.util.ArrayList;

/**
 *
 * @author juliam
 */
public class Grid {
  Person[][] grid;
  int maxRows;
  int maxColumns;
  
  public Grid(int rows, int columns) {
    grid = new Person[rows][columns];
    maxRows = rows;
    maxColumns = columns;
    initialize();
  }
  
  public int getMaxRows() {
    return maxRows;
  }
  
  public int getMaxColumns() {
    return maxColumns;
  }
  
  //fill grid up with persons
  public void initialize(){
    for (int i = 0; i < maxRows; i++) {
      for (int j = 0; j < maxColumns; j++) {
        grid[i][j] = new Person(i, j);
      }
    }
  }
  
  public ArrayList<Person> getNeighbors(Person person) {
    ArrayList<Person> neighbors = new ArrayList();
    ArrayList<Coordinates> neighborCoordinates = person.getNeightbors();
    
    for(int i=0; i<neighborCoordinates.size(); i++) {
      Coordinates tempCoordinates = neighborCoordinates.get(i);
      //checks if the coordinates for its neighbors don't go beyond the grid
      if(isValidCoordinates(neighborCoordinates.get(i))) {
        Person neighbor = grid[tempCoordinates.getX()][tempCoordinates.getY()];
        neighbors.add(neighbor);
      }
    }    
    return neighbors;
  }
  
  private boolean isValidCoordinates(Coordinates coordinates) {
    int x = coordinates.getX();
    int y = coordinates.getY();
    
    if((x >= maxRows) || (x < 0)) {
      return false;
    } else if ((y >= maxColumns) || (y < 0)) {
      return false;
    } else {
      return true;
    }
  }
  
  public void changeState(int x, int y, int state) {
    grid[x][y].setState(state);
  }
  
  public Person getPerson(int x, int y) {
    return grid[x][y];
  }
  
  public int getSusceptibleCount() {
    int susceptibleCount = 0;
    for (int i = 0; i < maxRows; i++) {
      for (int j = 0; j < maxColumns; j++) {
        Person currPerson = grid[i][j];
        if(currPerson.isSusceptible()) {
          susceptibleCount++;
        }
      }
    }
    return susceptibleCount;
  }
  
  //returns the coordinates of persons who are infected, for grid update in DengueSimulation
  public ArrayList<Coordinates> getInfectedCoordinates() {
    ArrayList<Coordinates> infected = new ArrayList();
    for (int i = 0; i < maxRows; i++) {
      for (int j = 0; j < maxColumns; j++) {
        Person currPerson = grid[i][j];
        if(currPerson.getState() == 2) {
          infected.add(currPerson.getCoordinates());
        }
      }
    }
    return infected;
  }
  
  //sets state of persons in given coordinates to infected
  public void setInfectedCoordinates(ArrayList<Coordinates> infectedCoordinates) {
    for(int i=0; i<infectedCoordinates.size(); i++) {
      Coordinates currCoordinates = infectedCoordinates.get(i);
      int x = currCoordinates.getX();
      int y = currCoordinates.getY();
      this.changeState(x, y, 2);
    }
  }
  
  public int getInfectedCount() {
    int infectedCount = 0;
    for (int i = 0; i < maxRows; i++) {
      for (int j = 0; j < maxColumns; j++) {
        Person currPerson = grid[i][j];
        if(currPerson.isInfected()) {
          infectedCount++;
        }
      }
    }
    return infectedCount;
  }
  
  //returns the coordinates of persons who are resistant for grid update in DengueSimulation
  public ArrayList<Coordinates> getResistantCoordinates() {
    ArrayList<Coordinates> infected = new ArrayList();
    for (int i = 0; i < maxRows; i++) {
      for (int j = 0; j < maxColumns; j++) {
        Person currPerson = grid[i][j];
        if(currPerson.getState() == 3) {
          infected.add(currPerson.getCoordinates());
        }
      }
    }
    return infected;
  }
  
  public void setResistantCoordinates(ArrayList<Coordinates> infectedCoordinates) {
    for(int i=0; i<infectedCoordinates.size(); i++) {
      Coordinates currCoordinates = infectedCoordinates.get(i);
      int x = currCoordinates.getX();
      int y = currCoordinates.getY();
      this.changeState(x, y, 3);
    }
  }
  
  public int getResistantCount() {
    int resistantCount = 0;
    for (int i = 0; i < maxRows; i++) {
      for (int j = 0; j < maxColumns; j++) {
        Person currPerson = grid[i][j];
        if(currPerson.isResistant()) {
          resistantCount++;
        }
      }
    }
    return resistantCount;
  }
  
  //increases each person's currStateTimeStep by 1, except for those who have changed states (becomes 0)
  public void updateTimes(ArrayList<Coordinates> changedCoordinates) {
     for (int i = 0; i < maxRows; i++) {
      for (int j = 0; j < maxColumns; j++) {
        Person currPerson = grid[i][j];
        if(changedCoordinates.contains(currPerson.getCoordinates())) {
          currPerson.resetCurrStateTimeStep();
        } else {
          currPerson.incCurrStateTimeStep();
        }
      }
    }   
  }
  
  public String display() {
    String display = "";
    for (int i = 0; i < maxRows; i++) {
      display += "[ ";
      for (int j = 0; j < maxColumns; j++) {
        display += grid[i][j].getState() + " ";
      }
      display += "] \n";
    }
    
    return display;
  }
  
  //for debugging purposes
  public String displayTimes() {
    String display = "";
    for (int i = 0; i < maxRows; i++) {
      display += "[ ";
      for (int j = 0; j < maxColumns; j++) {
        display += grid[i][j].getCurrStateTimeStep() + " ";
      }
      display += "] \n";
    }
    
    return display;
  }
}