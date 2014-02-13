/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package denguesimulation;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author juliam
 */
public class DengueSimulation {
  int population;
  int endTime;
  int initInfectedPopulation;
  double infectionProbability;
  int resistanceRate;
  Grid prevGrid;
  Grid currGrid;
  
  public DengueSimulation(int population, int endTime, int rows, int columns, int initInfectedPopulation, double infectionProbability, int resistanceRate) {
    this.population = population;
    this.endTime = endTime;
    this.prevGrid = new Grid(rows, columns);
    this.currGrid = new Grid(rows, columns);
    this.initInfectedPopulation = initInfectedPopulation;
    this.infectionProbability = infectionProbability; 
    this.resistanceRate = resistanceRate;
    this.initialize();
  }
  
  public void initialize(){
    //chooses random persons to be the initially infected members of the population
    for(int i = 0; i < initInfectedPopulation; i++) {
      Random generator = new Random();
      int x = generator.nextInt(prevGrid.getMaxRows());
      int y = generator.nextInt(prevGrid.getMaxRows());
      prevGrid.changeState(x, y, 2);
      currGrid.changeState(x, y, 2);
    }
  }
  
  public void simulate() {
    int currTime = 0;
    int[] susceptibleCounts = new int[endTime];
    int[] infectedCounts = new int[endTime];
    int[] resistantCounts = new int[endTime];
    
    //System.out.println("INITIAL GRID");
    //System.out.println(prevGrid.display());
    
    while(currTime != endTime) {
      ArrayList<Coordinates> changedPersons = new ArrayList();
      //System.out.println("------------------------------------------------------");
      //System.out.println(currTime);
      //loops through previous grid, sets changed values in current grid
      for (int i = 0; i < prevGrid.getMaxRows(); i++) {
        for (int j = 0; j < prevGrid.getMaxColumns(); j++) {
          Person currPerson = prevGrid.getPerson(i,j);
          Coordinates currCoordinates = currPerson.getCoordinates();
          
          //if the current person is susceptible
          if(!currPerson.isInfected() && !currPerson.isResistant()) {
            //calls willBeInfected which checks neighbors and generates probabilities
            boolean infected = willBeInfected(currPerson);
            if (infected) {
              //System.out.println("PERSON INFECTED " + i + " " + j );
              //person at i,j will now be infected / state changed to 2
              currGrid.changeState(i, j, 2);
              //added to changedPersons arraylist for the purpose of moving contents to prevGrid later on
              changedPersons.add(currCoordinates);
              
              //System.out.println("NEW GRID");
              //System.out.println(currGrid.display());
              
              //System.out.println("OLD GRID");
              //System.out.println(prevGrid.display());
            }
          } 
          
          //if infected
          if(currPerson.isInfected()) {
            //if it has reached resistanceRate (will now become susceptible)
            if(currPerson.getCurrStateTimeStep() == resistanceRate) {
              //state changed to susceptible
              currGrid.changeState(i, j, 3);
              changedPersons.add(currCoordinates);
            }
          }
        }
      }
      
      /*whole block sets the contents of prevGrid to be those of the currGrid, with updated times*/
      
      //System.out.println("PREVIOUS GRID");
      //System.out.println(prevGrid.display());
      
      //checks which persons have become infected, prevGrid is updated
      ArrayList<Coordinates> infectedCoordinates = currGrid.getInfectedCoordinates();
      prevGrid.setInfectedCoordinates(infectedCoordinates);
      
      //checks which persons have beocme resistant, prevGrid is updated
      ArrayList<Coordinates> resistantCoordinates = currGrid.getResistantCoordinates();
      prevGrid.setResistantCoordinates(resistantCoordinates);
      
      //System.out.println("PREVIOUS TIMES");
      //System.out.println(prevGrid.displayTimes());
      
      prevGrid.updateTimes(changedPersons);
      
      //System.out.println("UPDATED TIMES");
      //System.out.println(prevGrid.displayTimes());
      
      //System.out.println("FINAL GRID AT CURRENT TIME");
      //System.out.println(currGrid.display());
      int susceptibleC = currGrid.getSusceptibleCount();
      int infectedC = currGrid.getInfectedCount();
      int resistantC = currGrid.getResistantCount();
      
      susceptibleCounts[currTime] = (susceptibleC);
      infectedCounts[currTime] = (infectedC);
      resistantCounts[currTime] = (resistantC);
      
      //System.out.println("susceptible: " + susceptibleC);
      //System.out.println("infected: " + infectedC);
      //System.out.println("resistant: " + resistantC);
      
      currTime++;
    }
    GraphicalUI graph = new GraphicalUI(population, susceptibleCounts, infectedCounts, resistantCounts);
  }
  
  public boolean willBeInfected(Person person) {
    ArrayList<Person> neighbors = prevGrid.getNeighbors(person);
    //loops through neighbors
    for(int i=0; i<neighbors.size(); i++) {
      //if that neighbor is infected, a probability is generated 
      if(neighbors.get(i).isInfected()) {
        double randomProbability = Math.random() * 1;
        //if probability is greater than the probability for infection, person becomes infected
        if(randomProbability > infectionProbability) {
          Person neighbor = neighbors.get(i);
          Coordinates nc = neighbor.getCoordinates();
          //System.out.println("PERSON WHO INFECTED " + nc.getX() + " " + nc.getY());
          return true;
        }
      }
    }
    return false;
  }
}
