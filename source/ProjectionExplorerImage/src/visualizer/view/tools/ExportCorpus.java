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

package visualizer.view.tools;

import javax.swing.JOptionPane;
import visualizer.graph.Graph;
import visualizer.graph.Scalar;
import visualizer.util.SaveDialog;
import visualizer.util.filefilter.ZIPFilter;

/**
 *
 * @author  Fernando Vieira Paulovich
 */
public class ExportCorpus extends javax.swing.JDialog {

    private static final long serialVersionUID = 1L;
    /** Creates new form ExportCorpus */
    private ExportCorpus(javax.swing.JFrame parent) {
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
        java.awt.GridBagConstraints gridBagConstraints;

        exportButtonGroup = new javax.swing.ButtonGroup();
        parametersPanel = new javax.swing.JPanel();
        exportPanel = new javax.swing.JPanel();
        allDocumentsRadioButton = new javax.swing.JRadioButton();
        selectedColoredDocsRadioButton = new javax.swing.JRadioButton();
        selectedNonColoredDocsRadioButton = new javax.swing.JRadioButton();
        corporaLabel = new javax.swing.JLabel();
        corporaTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        buttonPanel = new javax.swing.JPanel();
        exportButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Export Corpus");
        setModal(true);

        parametersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Parameters to Export"));
        parametersPanel.setLayout(new java.awt.GridBagLayout());

        exportPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Export what?"));
        exportPanel.setLayout(new java.awt.GridBagLayout());

        exportButtonGroup.add(allDocumentsRadioButton);
        allDocumentsRadioButton.setText("Export all documents");
        allDocumentsRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        allDocumentsRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        exportPanel.add(allDocumentsRadioButton, gridBagConstraints);

        exportButtonGroup.add(selectedColoredDocsRadioButton);
        selectedColoredDocsRadioButton.setSelected(true);
        selectedColoredDocsRadioButton.setText("Export colored documents");
        selectedColoredDocsRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        selectedColoredDocsRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        exportPanel.add(selectedColoredDocsRadioButton, gridBagConstraints);

        exportButtonGroup.add(selectedNonColoredDocsRadioButton);
        selectedNonColoredDocsRadioButton.setText("Export NO colored documents");
        selectedNonColoredDocsRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        selectedNonColoredDocsRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        exportPanel.add(selectedNonColoredDocsRadioButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        parametersPanel.add(exportPanel, gridBagConstraints);

        corporaLabel.setText("Corpus name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        parametersPanel.add(corporaLabel, gridBagConstraints);

        corporaTextField.setColumns(30);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        parametersPanel.add(corporaTextField, gridBagConstraints);

        searchButton.setText("Search...");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(9, 3, 3, 3);
        parametersPanel.add(searchButton, gridBagConstraints);

        getContentPane().add(parametersPanel, java.awt.BorderLayout.CENTER);

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        exportButton.setText("Export");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(exportButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(cancelButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        int result = SaveDialog.showSaveDialog(new ZIPFilter(), this);

        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            String filename = SaveDialog.getFilename();
            this.corporaTextField.setText(filename);
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        if (this.corporaTextField.getText().trim().length() > 0) {
            if (this.allDocumentsRadioButton.isSelected()) {
                this.graph.exportCorpus(this.corporaTextField.getText(), null, false);
            } else if (this.selectedColoredDocsRadioButton.isSelected()) {
                this.graph.exportCorpus(this.corporaTextField.getText(), this.scalar, false);
            } else {
                this.graph.exportCorpus(this.corporaTextField.getText(), this.scalar, true);
            }
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this,
                    "A name for the new corpus must be provided!",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_exportButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    public static ExportCorpus getInstance(javax.swing.JFrame parent) {
        if (instance == null || instance.getParent() != parent) {
            instance = new ExportCorpus(parent);
        }
        return instance;
    }

    public void display(Scalar scalar, Graph graph) {
        if (graph.getCorpus() != null) {
            this.graph = graph;
            this.scalar = scalar;
            this.corporaTextField.setText("");
            this.pack();
            this.setLocationRelativeTo(this.getParent());
            this.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this.getParent(),
                    "The original corpus must be chose!",
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ExportCorpus(null).setVisible(true);
            }

        });
    }

    private Scalar scalar;
    private Graph graph;
    //private javax.swing.JFileChooser file = new javax.swing.JFileChooser();
    private static ExportCorpus instance;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton allDocumentsRadioButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel corporaLabel;
    private javax.swing.JTextField corporaTextField;
    private javax.swing.JButton exportButton;
    private javax.swing.ButtonGroup exportButtonGroup;
    private javax.swing.JPanel exportPanel;
    private javax.swing.JPanel parametersPanel;
    private javax.swing.JButton searchButton;
    private javax.swing.JRadioButton selectedColoredDocsRadioButton;
    private javax.swing.JRadioButton selectedNonColoredDocsRadioButton;
    // End of variables declaration//GEN-END:variables
}
