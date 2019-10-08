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

package visualizer.projection.isomap;

import visualizer.projection.ProjectionData;
import visualizer.wizard.ProjectionView;

/**
 *
 * @author  Fernando Vieira Paulovich
 */
public class ISOMAPProjectionView extends ProjectionView {

    /** Creates new form ISOMAPProjectionView */
    public ISOMAPProjectionView(ProjectionData pdata) {
        super(pdata);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        statusPanel = new javax.swing.JPanel();
        statusProgressBar = new javax.swing.JProgressBar();
        statusLabel = new javax.swing.JLabel();
        parametersPanel = new javax.swing.JPanel();
        nrNeighborsLabel = new javax.swing.JLabel();
        nrNeighborsTextField = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("ISOMAP"));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        statusPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));
        statusPanel.setLayout(new java.awt.BorderLayout());

        statusProgressBar.setPreferredSize(new java.awt.Dimension(350, 22));
        statusProgressBar.setStringPainted(true);
        statusPanel.add(statusProgressBar, java.awt.BorderLayout.SOUTH);

        statusLabel.setText("   ");
        statusLabel.setMinimumSize(new java.awt.Dimension(100, 22));
        statusLabel.setPreferredSize(new java.awt.Dimension(100, 22));
        statusPanel.add(statusLabel, java.awt.BorderLayout.NORTH);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(statusPanel, gridBagConstraints);

        parametersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameters"));

        nrNeighborsLabel.setText("Number of Neighbors");
        parametersPanel.add(nrNeighborsLabel);

        nrNeighborsTextField.setColumns(10);
        nrNeighborsTextField.setText("10");
        parametersPanel.add(nrNeighborsTextField);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(parametersPanel, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void reset() {
        this.nrNeighborsTextField.setText(Integer.toString(pdata.getNumberNeighborsConnection()));
    }

    public void setStatus(String status, int value) {
        this.statusLabel.setText(status);
        this.statusProgressBar.setValue(value);
    }

    public void refreshData() {
        this.pdata.setNumberNeighborsConnection(Integer.parseInt(this.nrNeighborsTextField.getText()));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nrNeighborsLabel;
    private javax.swing.JTextField nrNeighborsTextField;
    private javax.swing.JPanel parametersPanel;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JProgressBar statusProgressBar;
    // End of variables declaration//GEN-END:variables
}
