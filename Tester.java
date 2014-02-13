/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package denguesimulation;

import javax.swing.*;

/**
 *
 * @author juliam
 */
public class Tester {
  public static void main(String[] args) {
    JTextField populationField = new JTextField(5);
    JTextField endTimeField = new JTextField(5);
    JTextField rowsField = new JTextField(5);
    JTextField columnsField = new JTextField(5);
    JTextField initInfectedPopulationField = new JTextField(5);
    JTextField infectionProbabilityField = new JTextField(5);
    JTextField resistanceRateField = new JTextField(5);
    
    JPanel panel = new JPanel();
    Box box = Box.createVerticalBox();
    Box one = Box.createHorizontalBox();
    one.add(Box.createHorizontalStrut(82));
    one.add(new JLabel("Population: "));
    one.add(populationField);
    Box two = Box.createHorizontalBox();
    two.add(Box.createHorizontalStrut(90));
    two.add(new JLabel("End Time: "));
    two.add(endTimeField);
    Box three = Box.createHorizontalBox();
    three.add(Box.createHorizontalStrut(110));
    three.add(new JLabel("Rows: "));
    three.add(rowsField);
    Box four = Box.createHorizontalBox();
    four.add(Box.createHorizontalStrut(92));
    four.add(new JLabel("Columns: "));
    four.add(columnsField);
    Box five = Box.createHorizontalBox();
    five.add(new JLabel("Initial Infected Population: "));
    five.add(initInfectedPopulationField);
    Box six = Box.createHorizontalBox();
    six.add(Box.createHorizontalStrut(30));
    six.add(new JLabel("Infection Probability: "));
    six.add(infectionProbabilityField);
    Box seven = Box.createHorizontalBox();
    seven.add(Box.createHorizontalStrut(49));
    seven.add(new JLabel("Resistance Rate: "));
    seven.add(resistanceRateField);
    box.add(one);
    box.add(two);
    box.add(three);
    box.add(four);
    box.add(five);
    box.add(six);
    box.add(seven);
    panel.add(box);
    
    int result = JOptionPane.showConfirmDialog(null, panel,
      "Please enter parameters.", JOptionPane.OK_CANCEL_OPTION);
    
    if (result == JOptionPane.OK_OPTION) {
      int population = Integer.parseInt(populationField.getText());
      int endTime = Integer.parseInt(endTimeField.getText());
      int rows = Integer.parseInt(rowsField.getText());
      int columns = Integer.parseInt(columnsField.getText());
      int initInfectedPopulation = Integer.parseInt(initInfectedPopulationField.getText());
      double infectionProbability = Double.parseDouble(infectionProbabilityField.getText());
      int resistanceRate = Integer.parseInt(resistanceRateField.getText());
      
      DengueSimulation simulation = new DengueSimulation(population, endTime,
        rows, columns, initInfectedPopulation, infectionProbability, resistanceRate);
      simulation.simulate();
    } else;
    /*
    DengueSimulation simulation = new DengueSimulation(100, 10, 10, 10, 5, 0.8, 2);
    //int population, int endTime, int rows, int columns, int initInfectedPopulation, double infectionProbability, int resistanceRate
    
    //infectionProbability -> each susceptible person checks its neighbors to see who is infected.
    //  when it finds an infected one, the infected neighbor generates a random probability that it might
    //  infect said person.  if this randomly generated probability is higher than the infectionProbability,
    //  the person gets infected
    
    //resistanceRate -> how many time steps until an infected person becomes resistant 
    
    //for now can only handle population number that are squares, although we should be able to automatically generate
    //rows and columns later so we won't have to ask for it
    simulation.simulate();*/
  }
}

