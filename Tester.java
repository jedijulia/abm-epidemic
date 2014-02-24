
package denguesimulation;

import javax.swing.*;

public class Tester {
  public static void main(String[] args) {
    JTextField populationField = new JTextField(5);
    JTextField endTimeField = new JTextField(5);
    JTextField rowsField = new JTextField(5);
    JTextField columnsField = new JTextField(5);
    JTextField initInfectedPopulationField = new JTextField(5);
    JTextField infectionProbabilityField = new JTextField(5);
    JTextField resistanceRateField = new JTextField(5);
    JTextField reservoirProbabilityField = new JTextField(5);
    
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
    six.add(new JLabel("1 - Infection Probability: "));
    six.add(infectionProbabilityField);
    Box seven = Box.createHorizontalBox();
    seven.add(Box.createHorizontalStrut(49));
    seven.add(new JLabel("Resistance Rate: "));
    seven.add(resistanceRateField);
    Box eight = Box.createHorizontalBox();
    eight.add(Box.createHorizontalStrut(30));
    eight.add(new JLabel("Reservoir Probability: "));
    eight.add(reservoirProbabilityField);
      
    box.add(one);
    box.add(two);
    box.add(three);
    box.add(four);
    box.add(five);
    box.add(six);
    box.add(seven);
    box.add(eight);
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
      double reservoirProbability = Double.parseDouble(reservoirProbabilityField.getText());
      
      DengueSimulation simulation = new DengueSimulation(population, endTime,
        rows, columns, initInfectedPopulation, infectionProbability, resistanceRate, reservoirProbability);
      simulation.simulate();
    } else;
  }
}

