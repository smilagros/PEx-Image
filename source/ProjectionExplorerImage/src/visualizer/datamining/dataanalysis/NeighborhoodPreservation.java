/* ***** BEGIN LICENSE BLOCK *****
 *
 * Copyright (c) 2005-2007 Universidade de Sao Paulo, Sao Carlos/SP, Brazil.
 * All Rights Reserved.
 *
 * This file is part of Projection Explorer (PEx).
 *
 * How to cite this work:
 *  
@inproceedings{paulovich2007pex,
author = {Fernando V. Paulovich and Maria Cristina F. Oliveira and Rosane 
Minghim},
title = {The Projection Explorer: A Flexible Tool for Projection-based 
Multidimensional Visualization},
booktitle = {SIBGRAPI '07: Proceedings of the XX Brazilian Symposium on 
Computer Graphics and Image Processing (SIBGRAPI 2007)},
year = {2007},
isbn = {0-7695-2996-8},
pages = {27--34},
doi = {http://dx.doi.org/10.1109/SIBGRAPI.2007.39},
publisher = {IEEE Computer Society},
address = {Washington, DC, USA},
}
 *  
 * PEx is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * PEx is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 *
 * This code was developed by members of Computer Graphics and Image
 * Processing Group (http://www.lcad.icmc.usp.br) at Instituto de Ciencias
 * Matematicas e de Computacao - ICMC - (http://www.icmc.usp.br) of 
 * Universidade de Sao Paulo, Sao Carlos/SP, Brazil. The initial developer 
 * of the original code is Fernando Vieira Paulovich <fpaulovich@gmail.com>.
 *
 * Contributor(s): Rosane Minghim <rminghim@icmc.usp.br>
 *
 * You should have received a copy of the GNU General Public License along 
 * with PEx. If not, see <http://www.gnu.org/licenses/>.
 *
 * ***** END LICENSE BLOCK ***** */

package visualizer.datamining.dataanalysis;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sf.epsgraphics.ColorMode;
import net.sf.epsgraphics.EpsGraphics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import visualizer.graph.Graph;
import visualizer.graph.XMLGraphParser;
import visualizer.matrix.Matrix;
import visualizer.projection.distance.DistanceMatrix;
import visualizer.projection.distance.Euclidean;
import visualizer.util.KNN;
import visualizer.util.Pair;
import visualizer.util.SaveDialog;
import visualizer.util.Util;
import visualizer.util.filefilter.EPSFilter;

/**
 *
 * @author  Fernando Vieira Paulovich
 */
public class NeighborhoodPreservation extends javax.swing.JDialog {

    /** Creates new form NeighborhoodPreservation */
    private NeighborhoodPreservation(javax.swing.JDialog parent) {
        super(parent);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        saveImageButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Neighborhood Preservation");
        setModal(true);

        saveImageButton.setText("Save Image");
        saveImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImageButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(saveImageButton);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(closeButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void saveImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveImageButtonActionPerformed
        int result = SaveDialog.showSaveDialog(new EPSFilter(), this, "image.eps");

        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = SaveDialog.getFilename();

            FileOutputStream out = null;

            try {
                // Save this document to example.eps
                out = new FileOutputStream(filename);

                // Create a new document with bounding box 0 <= x <= 100 and 0 <= y <= 100.
                EpsGraphics g = new EpsGraphics(filename, out, 0, 0,
                        panel.getWidth() + 1, panel.getHeight() + 1, ColorMode.COLOR_RGB);

                freechart.draw(g, new Rectangle2D.Double(0, 0, panel.getWidth() + 1,
                        panel.getHeight() + 1));

                // Flush and close the document (don't forget to do this!)
                g.flush();
                g.close();

            } catch (IOException ex) {
                Logger.getLogger(NeighborhoodPreservation.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Problems saving the file", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (out != null) {
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(NeighborhoodPreservation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_saveImageButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_closeButtonActionPerformed

    public static NeighborhoodPreservation getInstance(javax.swing.JDialog parent) {
        return new NeighborhoodPreservation(parent);
    }

    public void display(final DistanceMatrix dmatdata, final ArrayList<Serie> series, final int maxneigh) {
        final MessageDialog md = MessageDialog.show(this, "Calculating neighborhood preservation...");

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    NeighborhoodPreservation.this.freechart = createChart(createAllSeries(dmatdata, series, maxneigh));
                    NeighborhoodPreservation.this.panel = new ChartPanel(freechart);
                    NeighborhoodPreservation.this.getContentPane().add(panel, BorderLayout.CENTER);

                    NeighborhoodPreservation.this.setPreferredSize(new Dimension(650, 400));
                    NeighborhoodPreservation.this.setSize(new Dimension(650, 400));

                    NeighborhoodPreservation.this.setLocationRelativeTo(NeighborhoodPreservation.this.getParent());

                    md.close();

                    NeighborhoodPreservation.this.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(NeighborhoodPreservation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };

        t.start();
    }

    private JFreeChart createChart(XYDataset xydataset) {
        JFreeChart chart = ChartFactory.createXYLineChart("Neighborhood Preservation",
                "Number Neighbors", "Precision", xydataset, PlotOrientation.VERTICAL,
                true, true, false);

        chart.setBackgroundPaint(Color.WHITE);

        XYPlot xyplot = (XYPlot) chart.getPlot();
        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        numberaxis.setAutoRangeIncludesZero(false);

        xyplot.setDomainGridlinesVisible(false);
        xyplot.setRangeGridlinesVisible(false);
        xyplot.setDomainGridlinePaint(Color.BLACK);
        xyplot.setRangeGridlinePaint(Color.BLACK);

        xyplot.setOutlinePaint(Color.BLACK);
        xyplot.setOutlineStroke(new BasicStroke(1.0f));
        xyplot.setBackgroundPaint(Color.white);
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);

        xyplot.setDrawingSupplier(new DefaultDrawingSupplier(
                new Paint[]{Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA,
                    Color.CYAN, Color.ORANGE, Color.BLACK, Color.DARK_GRAY, Color.GRAY,
                    Color.LIGHT_GRAY, Color.YELLOW
                }, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));

        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        xylineandshaperenderer.setBaseShapesVisible(true);
        xylineandshaperenderer.setBaseShapesFilled(true);
        xylineandshaperenderer.setDrawOutlines(true);

        return chart;
    }

    private XYDataset createAllSeries(DistanceMatrix dmatdata, ArrayList<Serie> series, int maxneigh) 
            throws IOException {
        XYSeriesCollection xyseriescollection = new XYSeriesCollection();

        for (int i = 0; i < series.size(); i++) {
            double[] values = this.neighborhood(dmatdata, series.get(i).filename, maxneigh);
            XYSeries xyseries = this.createSerie(series.get(i).name, values);
            xyseriescollection.addSeries(xyseries);
        }

        return xyseriescollection;
    }

    private XYSeries createSerie(String name, double[] values) {
        XYSeries xyseries = new XYSeries(name);

        for (int i = 0; i < values.length; i++) {
            xyseries.add(i + 1, values[i]);
        }

        return xyseries;
    }
    
    public double[] neighborhood(DistanceMatrix dmatdata, Graph graph,
            int maxneigh) throws IOException {
        double[] values = new double[maxneigh];

        Matrix proj = Util.exportSelectedProjection(graph, null);//Projection(graph, null);
        DistanceMatrix dmatproj = new DistanceMatrix(proj, new Euclidean());
        
        System.out.println("[Matrix, DistanceMatrix] numero de elementos: "+dmatdata.getElementCount()+", "+dmatproj.getElementCount());
        if (dmatdata.getElementCount() != dmatproj.getElementCount()) {
            throw new IOException("Data set different from projection.");
        }

        KNN knnproj = new KNN(maxneigh);
        Pair[][] nproj = knnproj.execute(dmatproj);

        KNN knndata = new KNN(maxneigh);
        Pair[][] ndata = knndata.execute(dmatdata);

        for (int n = maxneigh-1; n < maxneigh; n++) {
            float percentage = 0.0f;

            for (int i = 0; i < dmatproj.getElementCount(); i++) {
                float total = 0.0f;

                for (int j = 0; j < n + 1; j++) {
                    if (this.contains(nproj[i], n + 1, ndata[i][j].index)) {
                        total++;
                    }
                }
                
                System.out.println(proj.getRow(i).getId()+": "+(total/(n+1)));

                percentage += total / (n + 1);
            }

            values[n] = percentage / dmatproj.getElementCount();
        }

        return values;
    }

    private double[] neighborhood(DistanceMatrix dmatdata, String filename, int maxneigh) throws IOException {
        
        double[] values = new double[maxneigh];

        XMLGraphParser parser = new XMLGraphParser();
        Graph graph = parser.parse(filename);

        Matrix proj = Util.exportProjection(graph, null);
        DistanceMatrix dmatproj = new DistanceMatrix(proj, new Euclidean());

        if (dmatdata.getElementCount() != dmatproj.getElementCount()) {
            throw new IOException("Data set different from projection.");
        }

        KNN knnproj = new KNN(maxneigh);
        Pair[][] nproj = knnproj.execute(dmatproj);

        KNN knndata = new KNN(maxneigh);
        Pair[][] ndata = knndata.execute(dmatdata);

        for( int n = 0; n < maxneigh; ++n )
            values[n] = doNeighborhoodPreservation(n, dmatproj, nproj, ndata);
        
        return values;
    }
    
    private double doNeighborhoodPreservation(int n, DistanceMatrix dmatproj, Pair[][] nproj, Pair[][] ndata) {
        float percentage = 0.0f;

        float[] values = getNPreservationValues(n, dmatproj, nproj, ndata);
        for( int i = 0; i < values.length; ++i )
            percentage += values[i];
            
        return percentage / dmatproj.getElementCount(); 
    }
    
    public static float[] getNPreservationValues(int n, DistanceMatrix dmatproj, Pair[][] nproj, Pair[][] ndata) {        
        float[] values = new float[dmatproj.getElementCount()];
        
        for (int i = 0; i < dmatproj.getElementCount(); i++) {
            float total = 0.0f;

            for (int j = 0; j < n + 1; j++) {
                if (contains(nproj[i], n + 1, ndata[i][j].index)) {
                    total++;
                }
            }
            
            values[i] = total/(n+1);
        }
        
        return values;
    }
    

    private static boolean contains(Pair[] neighbors, int length, int index) {
        for (int i = 0; i < length; i++) {
            if (neighbors[i].index == index) {
                return true;
            }
        }

        return false;
    }

    public static class Serie {

        public Serie(String name, String filename) {
            this.name = name;
            this.filename = filename;
        }

        public String name;
        public String filename;
    }

    private JFreeChart freechart;
    private JPanel panel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton saveImageButton;
    // End of variables declaration//GEN-END:variables
}
