
package denguesimulation;

import java.util.ArrayList;
import java.util.Random;

public class DengueSimulation {
  int population;
  int endTime;
  int initInfectedPopulation;
  double infectionProbability;
  int resistanceRate;
  double reservoirProbability;
  Grid prevGrid;
  Grid currGrid;
  
  public DengueSimulation(int population, int endTime, int initInfectedPopulation, double infectionProbability, int resistanceRate, double reservoirProbability) {
    this.population = population;
    this.endTime = endTime;
    this.prevGrid = new Grid(population);
    this.currGrid = new Grid(population);
    this.initInfectedPopulation = initInfectedPopulation;
    this.infectionProbability = 1 - infectionProbability; 
    this.resistanceRate = resistanceRate;
    if(this.resistanceRate < 0) {this.resistanceRate = 0;}
    this.reservoirProbability = reservoirProbability;
    this.initialize();
  }
  
  //chooses random persons to be the initially infected members of the population
  public void initialize(){  
    for(int i = 0; i < initInfectedPopulation; i++) {
      Random generator = new Random();
      int x = generator.nextInt(prevGrid.getMaxRows());
      int y = generator.nextInt(prevGrid.getMaxRows());
      if(!currGrid.getPerson(x, y).isExcluded()) {
        prevGrid.changeState(x, y, 2);
        currGrid.changeState(x, y, 2);
      } else {
          i--;
      }
    }
  }
  
  //simulates the epidemic's effects on the population per time step
  public void simulate() {
    int currTime = 0;
    int[] susceptibleCounts = new int[endTime+1];
    int[] infectedCounts = new int[endTime+1];
    int[] resistantCounts = new int[endTime+1];
    susceptibleCounts[0] = population; infectedCounts[0] = initInfectedPopulation; resistantCounts[0] = 0;
    //loops through each time step
    while(currTime != endTime) {
      ArrayList<Coordinates> changedPersons = new ArrayList();

      for (int i = 0; i < prevGrid.getMaxRows(); i++) {
        for (int j = 0; j < prevGrid.getMaxColumns(); j++) {
          Person currPerson = prevGrid.getPerson(i,j);
          Coordinates currCoordinates = currPerson.getCoordinates();
          
          //if susceptible
          if(currPerson.isSusceptible()) {
            //calls willBeInfected which checks neighbors and generates probabilities
            boolean infected = willBeInfected(currPerson);
            if (infected) {
              currGrid.changeState(i, j, 2);
              //added to changedPersons arraylist for the purpose of moving contents to prevGrid later on
              changedPersons.add(currCoordinates);
            }
          } 
          
          //if infected
          if(currPerson.isInfected()) {
            //if it has reached resistanceRate, person becomes susceptible
            if(currPerson.getCurrStateTimeStep() == resistanceRate) {
              currGrid.changeState(i, j, 3);
              changedPersons.add(currCoordinates);
            }
          }
        }
      }
      
      /*whole block sets the contents of prevGrid to be those of the currGrid, with updated times*/
      
      //checks which persons have become infected, prevGrid is updated
      ArrayList<Coordinates> infectedCoordinates = currGrid.getInfectedCoordinates();
      prevGrid.setInfectedCoordinates(infectedCoordinates);
      
      //checks which persons have beocme resistant, prevGrid is updated
      ArrayList<Coordinates> resistantCoordinates = currGrid.getResistantCoordinates();
      prevGrid.setResistantCoordinates(resistantCoordinates);      
      prevGrid.updateTimes(changedPersons);
      
      //retrieves the number of susceptible, infected and resistant persons
      int susceptibleC = currGrid.getSusceptibleCount();
      int infectedC = currGrid.getInfectedCount();
      int resistantC = currGrid.getResistantCount();
      
      //for graphing purposes
      susceptibleCounts[currTime+1] = (susceptibleC);
      infectedCounts[currTime+1] = (infectedC);
      resistantCounts[currTime+1] = (resistantC);
      
      currTime++;
    }
    GraphicalUI graph = new GraphicalUI(population, susceptibleCounts, infectedCounts, resistantCounts);
  }  
  
  //checks neighbors and returns true if person will be infected
  public boolean willBeInfected(Person person) {
    ArrayList<Person> neighbors = prevGrid.getNeighbors(person);
    //loops through neighbors
    for(int i=0; i<neighbors.size(); i++) {
      //if that neighbor is infected, a probability is generated 
      if(neighbors.get(i).isInfected()) {
        double randomProbability = Math.random() * 1;
        //if probability is greater than the probability for infection, person becomes infected
        if(randomProbability > infectionProbability) {
          return true;
        }
      }
    }
    //generates random probability to check if it will be infected from the reservoir
    //if generated probability is less than reservoir probability, the person is infected
    double randomProbForRes = Math.random() * 1;
    if(randomProbForRes < reservoirProbability) {
        return true;
    }
    return false;
  }
}
