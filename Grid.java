
package denguesimulation;

import java.util.ArrayList;

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
  
  public Grid(int population) {
    int[] dimensions = getDimensions(population);
    int rows = dimensions[0];
    int columns = dimensions[1];
    grid = new Person[rows][columns];
    maxRows = rows;
    maxColumns = columns;
    initialize();
  }
  
  private int[] getDimensions(int population) {
      double squareRoot = Math.sqrt(population);
      int[] dimensions = new int[2];
      if (squareRoot == Math.floor(squareRoot)) {
          dimensions[0] = (int)squareRoot;
          dimensions[1] = (int)squareRoot;
      } else {
          //nothing for now
      }
      return dimensions;
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
  
  //returns a person's neighbors by looking at the 8 persons surrounding it in the grid
  public ArrayList<Person> getNeighbors(Person person) {
    ArrayList<Person> neighbors = new ArrayList();
    ArrayList<Coordinates> neighborCoordinates = person.getNeightbors();
    
    for(int i=0; i<neighborCoordinates.size(); i++) {
      Coordinates tempCoordinates = neighborCoordinates.get(i);
      if(isValidCoordinates(neighborCoordinates.get(i))) {
        Person neighbor = grid[tempCoordinates.getX()][tempCoordinates.getY()];
        neighbors.add(neighbor);
      }
    }    
    return neighbors;
  }
  
  //checks if the coordinates for its neighbors don't go beyond the grid's capacity
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
  
  //change state to susceptible(1), infected(2) or resistant(3) 
  public void changeState(int x, int y, int state) {
    grid[x][y].setState(state);
  }
  
  //returns person at coordinate x, y
  public Person getPerson(int x, int y) {
    return grid[x][y];
  }
  
  //returns number of susceptible persons
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
  
  //returns the coordinates of persons who are infected
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
  
  //sets state of persons in given coordinates to infected(2)
  public void setInfectedCoordinates(ArrayList<Coordinates> infectedCoordinates) {
    for(int i=0; i<infectedCoordinates.size(); i++) {
      Coordinates currCoordinates = infectedCoordinates.get(i);
      int x = currCoordinates.getX();
      int y = currCoordinates.getY();
      this.changeState(x, y, 2);
    }
  }
  
  //returns number of infected persons
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
  
  //returns the coordinates of persons who are resistant 
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
  
  //sets state of persons in given coordinates to resistant(3)
  public void setResistantCoordinates(ArrayList<Coordinates> resistantCoordinates) {
    for(int i=0; i<resistantCoordinates.size(); i++) {
      Coordinates currCoordinates = resistantCoordinates.get(i);
      int x = currCoordinates.getX();
      int y = currCoordinates.getY();
      this.changeState(x, y, 3);
    }
  }
  
  //returns number of resistant persons
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
  
  //displays the grid with each persons's state: 1-susceptible, 2-infected, 3-resistant
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
  
  //displays the grid containing how many time steps each individual has been at its current state
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