/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package denguesimulation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
 
public class GraphicalUI extends JPanel {
  private int[] sus;
  private int[] infects; 
  private int[] resists;
  private int totalPopulation;
  private final int width = 800;
  private final int height = 650;
  private final int padding = 30;
  private final int pointSize = 10;
  private final int Y_HATCH_CNT = 10;
  private final Color susceptibleColor = Color.green;
  private final Color infectedColor = Color.red;
  private final Color resistantColor = Color.blue;
  private final Color pointColor = Color.gray;
  private final Stroke GRAPH_STROKE = new BasicStroke(3f);
    
  public GraphicalUI(int p, int[] s, int[] i, int[] r) {
    totalPopulation = p;
    sus = s;
    infects = i;
    resists = r;
    
    JFrame frame = new JFrame("Project_Graph");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(this);
    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
  }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      double xScale = ((double) getWidth() - 2 * padding) / (sus.length - 1);
      double yScale = ((double) getHeight() - 2 * padding) / (totalPopulation - 1);

      List<Point> susceptiblePoints = new ArrayList<>();
      List<Point> infectedPoints = new ArrayList<>();
      List<Point> resistantPoints = new ArrayList<>();
      
      for (int i = 0; i < sus.length; i++) {
         int x1 = (int) (i * xScale + padding);
         int y1 = (int) ((totalPopulation - sus[i]) * yScale + padding);
         susceptiblePoints.add(new Point(x1, y1));
         
         int y2 = (int) ((totalPopulation - infects[i]) * yScale + padding);
         infectedPoints.add(new Point(x1, y2));
         
         int y3 = (int) ((totalPopulation - resists[i]) * yScale + padding);
         resistantPoints.add(new Point(x1, y3));
         
      }

      // x and y axes 
      g2.drawLine(padding, getHeight() - padding, padding, padding);
      g2.drawLine(padding, getHeight() - padding, getWidth() - padding, getHeight() - padding);

      // hatchmarks for y axis. 
      for (int i = 0; i < Y_HATCH_CNT; i++) {
         int ct = (int) (totalPopulation * ((i+1) * 0.1));
         String popCt = Integer.toString(ct);
         int x0 = padding;
         int x1 = pointSize + padding;
         int y0 = getHeight() - (((i + 1) * (getHeight() - padding * 2)) / Y_HATCH_CNT + padding);
         //int y1 = y0;
         //System.out.println(x0 + " " + y0 + " " + x1 + " " + y1);
         g2.drawLine(x0, y0, x1, y0);
         g2.drawString(popCt, 10, y0+3);
      }

      // hatchmarks for x axis
      for (int i = 0; i < sus.length - 1; i++) {
         int x0 = (i + 1) * (getWidth() - padding * 2) / (sus.length - 1) + padding;
         //int x1 = x0;
         int y0 = getHeight() - padding;
         int y1 = y0 - pointSize;
         g2.drawLine(x0, y0, x0, y1);
         g2.drawString(Integer.toString(i+1) , x0-2, y0+20);         
      }
      
      //drawing the graph
      Stroke oldStroke = g2.getStroke();
      g2.setStroke(GRAPH_STROKE);
      for (int i = 0; i < susceptiblePoints.size() - 1; i++) {
         g2.setColor(susceptibleColor);
         int x1 = susceptiblePoints.get(i).x;
         int y1 = susceptiblePoints.get(i).y;
         int x2 = susceptiblePoints.get(i + 1).x;
         int y2 = susceptiblePoints.get(i + 1).y;
         g2.drawLine(x1, y1, x2, y2); 
         
         g2.setColor(infectedColor);
         x1 = infectedPoints.get(i).x;
         y1 = infectedPoints.get(i).y;
         x2 = infectedPoints.get(i + 1).x;
         y2 = infectedPoints.get(i + 1).y;
         g2.drawLine(x1, y1, x2, y2);
         
         g2.setColor(resistantColor);
         x1 = resistantPoints.get(i).x;
         y1 = resistantPoints.get(i).y;
         x2 = resistantPoints.get(i + 1).x;
         y2 = resistantPoints.get(i + 1).y;
         g2.drawLine(x1, y1, x2, y2);
      }

      g2.setStroke(oldStroke);      
      g2.setColor(pointColor);
      for (int i = 0; i < susceptiblePoints.size(); i++) {
         int x = susceptiblePoints.get(i).x - pointSize / 2;
         int y = susceptiblePoints.get(i).y - pointSize / 2;
         int ovalW = pointSize;
         int ovalH = pointSize;
         g2.fillOval(x, y, ovalW, ovalH);
         
         x = infectedPoints.get(i).x - pointSize / 2;
         y = infectedPoints.get(i).y - pointSize / 2;
         g2.fillOval(x, y, ovalW, ovalH);
         
         x = resistantPoints.get(i).x - pointSize / 2;
         y = resistantPoints.get(i).y - pointSize / 2;
         g2.fillOval(x, y, ovalW, ovalH);
      }
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(width, height);
   }
   
   public void graph(int[] s, int[] i, int[] r) {     
     GraphicalUI mainPanel = new GraphicalUI(0, s, i, r);
     JFrame frame = new JFrame("Project_Graph");
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.getContentPane().add(mainPanel);
     frame.pack();
     frame.setLocationByPlatform(true);
     frame.setVisible(true);   
   }
}

