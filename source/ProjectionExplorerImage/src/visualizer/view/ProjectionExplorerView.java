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
 * Contributor(s): Roberto Pinho <robertopinho@yahoo.com.br>, 
 *                 Rosane Minghim <rminghim@icmc.usp.br>
 *
 * You should have received a copy of the GNU General Public License along 
 * with PEx. If not, see <http://www.gnu.org/licenses/>.
 *
 * ***** END LICENSE BLOCK ***** */
package visualizer.view;

import br.com.metodos.utils.Retangulo;
import br.com.overlayanalisys.euclideandistance.EuclideanDistance;
import br.com.overlayanalisys.layoutsimilarity.LayoutSimilarity;
import br.com.overlayanalisys.sizeincrease.SizeIncrease;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.WindowConstants;
import visualizer.converters.DistanceMatrixConverterView;
import visualizer.converters.PointsFileConverterView;
import visualizer.corpus.Corpus;
import visualizer.google.GoogleView;
import visualizer.graph.Graph;
import visualizer.graph.XMLGraphWriter;
import visualizer.graph.Connectivity;
import visualizer.graph.Vertex;
import visualizer.graph.coodination.Coordinator;
import visualizer.graph.coodination.DistanceMappingView;
import visualizer.graph.coodination.IdentityMappingView;
import visualizer.graph.coodination.TopicMappingView;
import visualizer.graph.listeners.VertexSelectionFactory;
import visualizer.util.Delaunay;
import visualizer.graph.Scalar;
import visualizer.graph.XMLGraphParser;
import visualizer.graph.coodination.Mapping;
import visualizer.datamining.dataanalysis.SimilarityMatrixView;
import visualizer.datamining.dataanalysis.DistanceHistogramView;
import visualizer.datamining.dataanalysis.NeighborhoodHitView;
import visualizer.datamining.dataanalysis.NeighborhoodPreservationView;
import visualizer.matrix.Matrix;
import visualizer.util.Pair;
import visualizer.util.PExConstants;
import visualizer.datamining.clustering.HierarchicalClusteringType;
import visualizer.datamining.clustering.MultidimensionalClusteringView;
import visualizer.datamining.clustering.SilhouetteCoefficient;
import visualizer.datamining.clustering.SilhouetteCoefficientView;
import visualizer.datamining.dataanalysis.CalculateStressView;
import visualizer.datamining.dataanalysis.KruskalStress;
import visualizer.datamining.dataanalysis.NeighborhoodHit;
import visualizer.datamining.dataanalysis.SammonStress;
import visualizer.datamining.dataanalysis.Stress;
import visualizer.util.OpenDialog;
import visualizer.util.SaveDialog;
import visualizer.util.SystemPropertiesManager;
import visualizer.util.Util;
import visualizer.util.filefilter.EPSFilter;
import visualizer.util.filefilter.PNGFilter;
import visualizer.util.filefilter.PROJFilter;
import visualizer.util.filefilter.SCALARFilter;
import visualizer.util.filefilter.CONFilter;
import visualizer.util.filefilter.TITLEFilter;
import visualizer.util.filefilter.VTKFilter;
import visualizer.util.filefilter.XMLFilter;
import visualizer.topic.Topic;
import visualizer.topic.TopicClusters;
import visualizer.topic.RulesTopicGrid;
import visualizer.util.filefilter.DIRFilter;
import visualizer.view.tools.BrowserControl;
import visualizer.view.tools.CreateDistanceScalar;
import visualizer.view.tools.KnnConnectivity;
import visualizer.view.tools.CreatePDFCorpora;
import visualizer.view.tools.ExportCorpus;
import visualizer.view.tools.ImportProjection;
import visualizer.view.tools.JoinScalars;
import visualizer.view.tools.MSTConnectivity;
import visualizer.view.tools.WordsManager;
import visualizer.featureselection.FeatureSelectionView;
import visualizer.featureselection.NeuralNetworkClassifierView;
import visualizer.graph.GroupRepresentative;
import visualizer.matrix.MatrixFactory;
import visualizer.projection.distance.Dissimilarity;
import visualizer.projection.distance.DissimilarityFactory;
import visualizer.projection.distance.DissimilarityType;
import visualizer.projection.distance.DistanceMatrix;
import visualizer.projection.representative.AnalysisType;
import visualizer.projection.representative.BoxplotDataGenerator;
import visualizer.projection.representative.BoxplotRepresentative;
import visualizer.projection.representative.NeighborhoodHitClusterData;
import visualizer.projection.representative.NeighborhoodPreservationClusterData;
import visualizer.projection.representative.SilhouetteCoefficientClusterData;
import visualizer.projection.representative.StressClusterData;
import visualizer.view.tools.FeaturesWeightView;
import visualizer.view.tools.MovePointsView;
import visualizer.view.tools.MultimodalVolumeReaderView;
import visualizer.view.tools.PointsToMatlabView;
import visualizer.view.tools.ScalarConnectivity;
import visualizer.wizard.ProjectionWizardView;

/**
 *
 * @author  Fernando Vieira Paulovich
 */
public class ProjectionExplorerView extends javax.swing.JFrame {

    
    private ArrayList<Retangulo> initialProjection = null;
    private ArrayList<Retangulo> finalProjection = null;
    /*static {
    System.loadLibrary("lspsolver");
    }
     */
    /** Creates new form ProjectionExplorerView */
    public ProjectionExplorerView() {
        //System.out.println(System.getProperty("java.library.path"));

        initComponents();
        this.setTitle("Projection Explorer for Images (PEx Image)");
        this.fileContentEditorPane.addHyperlinkListener(new HyperlinkListener() {

            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    BrowserControl.displayURL(e.getURL().toString());
                }
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.width * 0.9), (int) (screenSize.height * 0.9));
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        SystemPropertiesManager m = SystemPropertiesManager.getInstance();
        m.setProperty("UNZIP.DIR", System.getProperty("user.dir") + "\\tempDir");
        m.setProperty("SCILABCODE.DIR", System.getProperty("user.dir") + "\\lib\\scilabCode");
        m.setProperty("FEATURE.DIR", System.getProperty("user.dir") + "\\lib\\featureSelection");
        this.viewContentToggleButtonActionPerformed(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectionButtonGroup = new javax.swing.ButtonGroup();
        windowsButtonGroup = new javax.swing.ButtonGroup();
        toolBar = new javax.swing.JToolBar();
        newButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        separatorLabel1 = new javax.swing.JLabel();
        zoomInButton = new javax.swing.JButton();
        zoomOutButton = new javax.swing.JButton();
        separatorLabel2 = new javax.swing.JLabel();
        toolButton = new javax.swing.JButton();
        separatorLabel5 = new javax.swing.JLabel();
        removeOverlapJButton = new javax.swing.JButton();
        runForceButton = new javax.swing.JButton();
        searchPanel = new javax.swing.JPanel();
        searchToolbarLabel = new javax.swing.JLabel();
        searchToolbarTextField = new javax.swing.JTextField();
        goToolbarButton = new javax.swing.JButton();
        graphToolBar = new javax.swing.JToolBar();
        createLabelToggleButton = new javax.swing.JToggleButton();
        createFilterLabelToggleButton = new javax.swing.JToggleButton();
        cutToggleButton = new javax.swing.JToggleButton();
        findToggleButton = new javax.swing.JToggleButton();
        selectToggleButton = new javax.swing.JToggleButton();
        viewContentToggleButton = new javax.swing.JToggleButton();
        coordinatedSelectToggleButton = new javax.swing.JToggleButton();
        showVertexLabelToggleButton = new javax.swing.JToggleButton();
        separatorLabel4 = new javax.swing.JLabel();
        movePointToggleButton = new javax.swing.JToggleButton();
        separatorLabel = new javax.swing.JLabel();
        createScalarFromDocButton = new javax.swing.JButton();
        createLabelsButton = new javax.swing.JButton();
        separatorLabel6 = new javax.swing.JLabel();
        ReproduceButton = new javax.swing.JButton();
        ReloadButton = new javax.swing.JButton();
        separatorLabel3 = new javax.swing.JLabel();
        showAllLabelsToggleButton = new javax.swing.JToggleButton();
        highlightLabelToggleButton = new javax.swing.JToggleButton();
        cleanLabelsButton = new javax.swing.JButton();
        windowSplitPane = new javax.swing.JSplitPane();
        pointsPanel = new javax.swing.JPanel();
        allPointsPanel = new javax.swing.JPanel();
        dataSearchPanel = new javax.swing.JPanel();
        searchLabel = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        goButton = new javax.swing.JButton();
        scrollPanePoints = new javax.swing.JScrollPane();
        pointsList = new javax.swing.JList(pointsListModel);
        optionTabbedPane = new javax.swing.JTabbedPane();
        markedPointPanel = new javax.swing.JPanel();
        nearestNeighborScrollPanel = new javax.swing.JScrollPane();
        nearestNeighborsList = new javax.swing.JList(nearestNeighborListModel);
        markedPointFields = new javax.swing.JPanel();
        markedPointField = new javax.swing.JTextField();
        contentPanel = new javax.swing.JPanel();
        fileContentScrollPane = new javax.swing.JScrollPane();
        fileContentEditorPane = new javax.swing.JEditorPane();
        fileTitleTextField = new javax.swing.JTextField();
        desktop = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        fileNew = new javax.swing.JMenuItem();
        fileOpen = new javax.swing.JMenuItem();
        fileSave = new javax.swing.JMenuItem();
        separator1 = new javax.swing.JSeparator();
        exportMenu = new javax.swing.JMenu();
        fileExportToPng = new javax.swing.JMenuItem();
        fileExportToVtk = new javax.swing.JMenuItem();
        fileExportToProjection = new javax.swing.JMenuItem();
        fileExportToEps = new javax.swing.JMenuItem();
        separator2 = new javax.swing.JSeparator();
        importMenu = new javax.swing.JMenu();
        fileImportFromProjection = new javax.swing.JMenuItem();
        directoryImportProjection = new javax.swing.JMenuItem();
        separator3 = new javax.swing.JSeparator();
        converters = new javax.swing.JMenu();
        pointsFileConverter_jMenuItem = new javax.swing.JMenuItem();
        matrixFileConverter_jMenuItem = new javax.swing.JMenuItem();
        pointsToMatlab_jMenuItem = new javax.swing.JMenuItem();
        multiModalVolumeToPoints_jMenuItem = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        editClean = new javax.swing.JMenuItem();
        editDelete = new javax.swing.JMenuItem();
        menuTool = new javax.swing.JMenu();
        memoryCheckMenuItem = new javax.swing.JMenuItem();
        googleSearchsMenuItem = new javax.swing.JMenuItem();
        stopwordsOption = new javax.swing.JMenuItem();
        separatorOptions1 = new javax.swing.JSeparator();
        connectivityMenu = new javax.swing.JMenu();
        knnOptions = new javax.swing.JMenuItem();
        delaunayOptions = new javax.swing.JMenuItem();
        mstOptions = new javax.swing.JMenuItem();
        scalarConnectivityOptions = new javax.swing.JMenuItem();
        importConnectivity = new javax.swing.JMenuItem();
        exportConnectivity = new javax.swing.JMenuItem();
        scalarMenu = new javax.swing.JMenu();
        createScalarFromConnectionMenuItem = new javax.swing.JMenuItem();
        joinScalarsOptions = new javax.swing.JMenuItem();
        calculateHCOptions = new javax.swing.JMenu();
        alinkHC = new javax.swing.JMenuItem();
        clinkHC = new javax.swing.JMenuItem();
        slinkHC = new javax.swing.JMenuItem();
        importScalarsOption = new javax.swing.JMenuItem();
        exportScalarsOption = new javax.swing.JMenuItem();
        rulesMenu = new javax.swing.JMenu();
        RulesFromGridMenuItem = new javax.swing.JMenuItem();
        exportRuleTermMenuItem = new javax.swing.JMenuItem();
        corpusMenu = new javax.swing.JMenu();
        loadCorporaOptions = new javax.swing.JMenuItem();
        exportCorporaOptions = new javax.swing.JMenuItem();
        createCorporaPDFOptions = new javax.swing.JMenuItem();
        coordinationMenu = new javax.swing.JMenu();
        distCoordinationMenuItem = new javax.swing.JMenuItem();
        identityCoordinationMenuItem = new javax.swing.JMenuItem();
        topicCoordinationMenuItem = new javax.swing.JMenuItem();
        titlesOption = new javax.swing.JMenu();
        importtitles = new javax.swing.JMenuItem();
        exporttitles = new javax.swing.JMenuItem();
        movements_jMenu = new javax.swing.JMenu();
        movePoints_jMenuItem = new javax.swing.JMenuItem();
        separatorOptions2 = new javax.swing.JSeparator();
        FeatureSelection = new javax.swing.JMenuItem();
        NeuralNetworkClassifier = new javax.swing.JMenuItem();
        weightedFeatures_jMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        toolOptions = new javax.swing.JMenuItem();
        dataMiningMenu = new javax.swing.JMenu();
        dataAnalysisMenu = new javax.swing.JMenu();
        createDistanceHistogramMenuItem = new javax.swing.JMenuItem();
        viewSimilarityMatrixMenuItem = new javax.swing.JMenuItem();
        neighborHitMenuItem = new javax.swing.JMenuItem();
        neighborPreservationMenuItem = new javax.swing.JMenuItem();
        stressMenuItem = new javax.swing.JMenuItem();
        layoutSimilarityMenuItem = new javax.swing.JMenuItem();
        sizeIncreaseMenuItem = new javax.swing.JMenuItem();
        euclideanDistanceMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        clusteringMenu = new javax.swing.JMenu();
        multidimensionalMenuItem = new javax.swing.JMenuItem();
        silhouetteCoefficientMenuItem = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        menuWindows = new javax.swing.JMenu();
        alignVerticalMenuItem = new javax.swing.JMenuItem();
        separator4 = new javax.swing.JSeparator();
        menuHelp = new javax.swing.JMenu();
        about = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Projection Explorer for Images (PEx-Image)");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        newButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/New16.gif"))); // NOI18N
        newButton.setToolTipText("Create a new projection");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });
        toolBar.add(newButton);

        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Open16.gif"))); // NOI18N
        openButton.setToolTipText("Open an existing projection");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
        toolBar.add(openButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Save16.gif"))); // NOI18N
        saveButton.setToolTipText("Save the current projection");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        toolBar.add(saveButton);

        separatorLabel1.setText("       ");
        toolBar.add(separatorLabel1);

        zoomInButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/ZoomIn16.gif"))); // NOI18N
        zoomInButton.setToolTipText("Zoom in");
        zoomInButton.setMaximumSize(new java.awt.Dimension(29, 27));
        zoomInButton.setMinimumSize(new java.awt.Dimension(29, 27));
        zoomInButton.setPreferredSize(new java.awt.Dimension(29, 27));
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInButtonActionPerformed(evt);
            }
        });
        toolBar.add(zoomInButton);

        zoomOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/ZoomOut16.gif"))); // NOI18N
        zoomOutButton.setToolTipText("Zoom out");
        zoomOutButton.setMaximumSize(new java.awt.Dimension(29, 27));
        zoomOutButton.setMinimumSize(new java.awt.Dimension(29, 27));
        zoomOutButton.setPreferredSize(new java.awt.Dimension(29, 27));
        zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutButtonActionPerformed(evt);
            }
        });
        toolBar.add(zoomOutButton);

        separatorLabel2.setText("       ");
        toolBar.add(separatorLabel2);

        toolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Preferences16.gif"))); // NOI18N
        toolButton.setToolTipText("Tool Preferences");
        toolButton.setMaximumSize(new java.awt.Dimension(29, 27));
        toolButton.setMinimumSize(new java.awt.Dimension(29, 27));
        toolButton.setPreferredSize(new java.awt.Dimension(29, 27));
        toolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolButtonActionPerformed(evt);
            }
        });
        toolBar.add(toolButton);

        separatorLabel5.setText("       ");
        toolBar.add(separatorLabel5);

        removeOverlapJButton.setText("Remove Overlapping");
        removeOverlapJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeOverlapJButtonActionPerformed(evt);
            }
        });
        toolBar.add(removeOverlapJButton);

        runForceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/media/Play16.gif"))); // NOI18N
        runForceButton.setToolTipText("Run Force Directed Layout");
        runForceButton.setMaximumSize(new java.awt.Dimension(29, 27));
        runForceButton.setMinimumSize(new java.awt.Dimension(29, 27));
        runForceButton.setPreferredSize(new java.awt.Dimension(29, 27));
        runForceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runForceButtonActionPerformed(evt);
            }
        });
        toolBar.add(runForceButton);

        searchPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        searchToolbarLabel.setText("Search");
        searchPanel.add(searchToolbarLabel);

        searchToolbarTextField.setColumns(15);
        searchToolbarTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchToolbarTextFieldKeyPressed(evt);
            }
        });
        searchPanel.add(searchToolbarTextField);

        goToolbarButton.setText("...");
        goToolbarButton.setMaximumSize(new java.awt.Dimension(29, 27));
        goToolbarButton.setMinimumSize(new java.awt.Dimension(29, 27));
        goToolbarButton.setPreferredSize(new java.awt.Dimension(29, 27));
        goToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goToolbarButtonActionPerformed(evt);
            }
        });
        searchPanel.add(goToolbarButton);

        toolBar.add(searchPanel);

        getContentPane().add(toolBar, java.awt.BorderLayout.NORTH);

        graphToolBar.setOrientation(javax.swing.SwingConstants.VERTICAL);

        selectionButtonGroup.add(createLabelToggleButton);
        createLabelToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Information16.gif"))); // NOI18N
        createLabelToggleButton.setToolTipText("Create Labels");
        createLabelToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createLabelToggleButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(createLabelToggleButton);

        selectionButtonGroup.add(createFilterLabelToggleButton);
        createFilterLabelToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/ContextualHelp16.gif"))); // NOI18N
        createFilterLabelToggleButton.setToolTipText("Create Filter Labels");
        createFilterLabelToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createFilterLabelToggleButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(createFilterLabelToggleButton);

        selectionButtonGroup.add(cutToggleButton);
        cutToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Cut16.gif"))); // NOI18N
        cutToggleButton.setToolTipText("Split Graph");
        cutToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutToggleButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(cutToggleButton);

        selectionButtonGroup.add(findToggleButton);
        findToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Find16.gif"))); // NOI18N
        findToggleButton.setToolTipText("Find Sub-graph");
        findToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findToggleButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(findToggleButton);

        selectionButtonGroup.add(selectToggleButton);
        selectToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/AlignCenter16.gif"))); // NOI18N
        selectToggleButton.setToolTipText("Select Graph");
        selectToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectToggleButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(selectToggleButton);

        selectionButtonGroup.add(viewContentToggleButton);
        viewContentToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Copy16.gif"))); // NOI18N
        viewContentToggleButton.setSelected(true);
        viewContentToggleButton.setToolTipText("View Content");
        viewContentToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewContentToggleButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(viewContentToggleButton);

        selectionButtonGroup.add(coordinatedSelectToggleButton);
        coordinatedSelectToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Refresh16.gif"))); // NOI18N
        coordinatedSelectToggleButton.setToolTipText("Coordinated Selection");
        coordinatedSelectToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coordinatedSelectToggleButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(coordinatedSelectToggleButton);

        selectionButtonGroup.add(showVertexLabelToggleButton);
        showVertexLabelToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/text/Italic16.gif"))); // NOI18N
        showVertexLabelToggleButton.setToolTipText("Show Vertex Labels");
        showVertexLabelToggleButton.setFocusable(false);
        showVertexLabelToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showVertexLabelToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        showVertexLabelToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showVertexLabelToggleButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(showVertexLabelToggleButton);

        separatorLabel4.setText("    ");
        graphToolBar.add(separatorLabel4);

        movePointToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/navigation/Forward16.gif"))); // NOI18N
        movePointToggleButton.setToolTipText("Move Point");
        movePointToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movePointToggleButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(movePointToggleButton);

        separatorLabel.setText("    ");
        graphToolBar.add(separatorLabel);

        createScalarFromDocButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Replace16.gif"))); // NOI18N
        createScalarFromDocButton.setToolTipText("Create Scalar from Document");
        createScalarFromDocButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createScalarFromDocButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(createScalarFromDocButton);

        createLabelsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/About16.gif"))); // NOI18N
        createLabelsButton.setToolTipText("Create Label Based on Kmeans");
        createLabelsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createLabelsButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(createLabelsButton);

        separatorLabel6.setText("    ");
        graphToolBar.add(separatorLabel6);

        ReproduceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Add16.gif"))); // NOI18N
        ReproduceButton.setToolTipText("Reproduce Projection");
        ReproduceButton.setFocusable(false);
        ReproduceButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ReproduceButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ReproduceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReproduceButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(ReproduceButton);

        ReloadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Undo16.gif"))); // NOI18N
        ReloadButton.setToolTipText("Force Reload Data");
        ReloadButton.setFocusable(false);
        ReloadButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ReloadButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ReloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReloadButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(ReloadButton);

        separatorLabel3.setText("    ");
        graphToolBar.add(separatorLabel3);

        showAllLabelsToggleButton.setText("SL");
        showAllLabelsToggleButton.setToolTipText("Show all labels");
        showAllLabelsToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllLabelsToggleButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(showAllLabelsToggleButton);

        highlightLabelToggleButton.setText("HL");
        highlightLabelToggleButton.setToolTipText("Highlight Labels");
        highlightLabelToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlightLabelToggleButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(highlightLabelToggleButton);

        cleanLabelsButton.setText("CL");
        cleanLabelsButton.setToolTipText("Clean Projection");
        cleanLabelsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanLabelsButtonActionPerformed(evt);
            }
        });
        graphToolBar.add(cleanLabelsButton);

        getContentPane().add(graphToolBar, java.awt.BorderLayout.EAST);

        windowSplitPane.setDividerLocation(250);
        windowSplitPane.setOneTouchExpandable(true);

        pointsPanel.setPreferredSize(new java.awt.Dimension(100, 256));
        pointsPanel.setLayout(new java.awt.GridLayout(2, 0));

        allPointsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Instances"));
        allPointsPanel.setPreferredSize(new java.awt.Dimension(100, 107));
        allPointsPanel.setLayout(new java.awt.BorderLayout(5, 5));

        dataSearchPanel.setPreferredSize(new java.awt.Dimension(10, 23));
        dataSearchPanel.setLayout(new java.awt.BorderLayout(5, 5));

        searchLabel.setText("Search");
        dataSearchPanel.add(searchLabel, java.awt.BorderLayout.WEST);

        searchTextField.setColumns(10);
        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyPressed(evt);
            }
        });
        dataSearchPanel.add(searchTextField, java.awt.BorderLayout.CENTER);

        goButton.setText("...");
        goButton.setMaximumSize(new java.awt.Dimension(29, 23));
        goButton.setMinimumSize(new java.awt.Dimension(29, 23));
        goButton.setPreferredSize(new java.awt.Dimension(29, 23));
        goButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goButtonActionPerformed(evt);
            }
        });
        dataSearchPanel.add(goButton, java.awt.BorderLayout.EAST);

        allPointsPanel.add(dataSearchPanel, java.awt.BorderLayout.NORTH);

        scrollPanePoints.setBorder(javax.swing.BorderFactory.createTitledBorder("Titles"));

        pointsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pointsList.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pointsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pointsListMouseClicked(evt);
            }
        });
        scrollPanePoints.setViewportView(pointsList);

        allPointsPanel.add(scrollPanePoints, java.awt.BorderLayout.CENTER);

        pointsPanel.add(allPointsPanel);

        optionTabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        markedPointPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Instance"));
        markedPointPanel.setPreferredSize(new java.awt.Dimension(100, 128));
        markedPointPanel.setLayout(new java.awt.BorderLayout());

        nearestNeighborScrollPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Nearest Neighbors"));
        nearestNeighborScrollPanel.setAutoscrolls(true);
        nearestNeighborScrollPanel.setPreferredSize(new java.awt.Dimension(100, 50));

        nearestNeighborsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        nearestNeighborsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nearestNeighborsListMouseClicked(evt);
            }
        });
        nearestNeighborScrollPanel.setViewportView(nearestNeighborsList);

        markedPointPanel.add(nearestNeighborScrollPanel, java.awt.BorderLayout.CENTER);

        markedPointFields.setLayout(new java.awt.BorderLayout());

        markedPointField.setEditable(false);
        markedPointField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        markedPointFields.add(markedPointField, java.awt.BorderLayout.NORTH);

        markedPointPanel.add(markedPointFields, java.awt.BorderLayout.NORTH);

        optionTabbedPane.addTab("Neighbors", markedPointPanel);

        contentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("File Content"));
        contentPanel.setLayout(new java.awt.BorderLayout(3, 3));

        fileContentScrollPane.setAutoscrolls(true);

        fileContentEditorPane.setEditable(false);
        fileContentEditorPane.setAutoscrolls(false);
        fileContentScrollPane.setViewportView(fileContentEditorPane);

        contentPanel.add(fileContentScrollPane, java.awt.BorderLayout.CENTER);

        fileTitleTextField.setEditable(false);
        contentPanel.add(fileTitleTextField, java.awt.BorderLayout.NORTH);

        optionTabbedPane.addTab("Content", contentPanel);

        pointsPanel.add(optionTabbedPane);

        windowSplitPane.setLeftComponent(pointsPanel);

        desktop.setBackground(javax.swing.UIManager.getDefaults().getColor("ToolBar.background"));
        desktop.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        desktop.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                desktopComponentResized(evt);
            }
        });
        desktop.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                desktopComponentAdded(evt);
            }
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                desktopComponentRemoved(evt);
            }
        });
        windowSplitPane.setRightComponent(desktop);

        getContentPane().add(windowSplitPane, java.awt.BorderLayout.CENTER);

        menuFile.setMnemonic('F');
        menuFile.setText("File");

        fileNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        fileNew.setMnemonic('N');
        fileNew.setText("New Projection");
        fileNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileNewActionPerformed(evt);
            }
        });
        menuFile.add(fileNew);

        fileOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        fileOpen.setMnemonic('O');
        fileOpen.setText("Open Projection");
        fileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileOpenActionPerformed(evt);
            }
        });
        menuFile.add(fileOpen);

        fileSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        fileSave.setMnemonic('S');
        fileSave.setText("Save Projection");
        fileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveActionPerformed(evt);
            }
        });
        menuFile.add(fileSave);
        menuFile.add(separator1);

        exportMenu.setText("Export");

        fileExportToPng.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileExportToPng.setMnemonic('P');
        fileExportToPng.setText("Export PNG File");
        fileExportToPng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileExportToPngActionPerformed(evt);
            }
        });
        exportMenu.add(fileExportToPng);

        fileExportToVtk.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileExportToVtk.setMnemonic('K');
        fileExportToVtk.setText("Export VTK File");
        fileExportToVtk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileExportToVtkActionPerformed(evt);
            }
        });
        exportMenu.add(fileExportToVtk);

        fileExportToProjection.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileExportToProjection.setMnemonic('J');
        fileExportToProjection.setText("Export 2D Points File");
        fileExportToProjection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileExportToProjectionActionPerformed(evt);
            }
        });
        exportMenu.add(fileExportToProjection);

        fileExportToEps.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileExportToEps.setMnemonic('E');
        fileExportToEps.setText("Export EPS File");
        fileExportToEps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileExportToEpsActionPerformed(evt);
            }
        });
        exportMenu.add(fileExportToEps);

        menuFile.add(exportMenu);
        menuFile.add(separator2);

        importMenu.setText("Import");

        fileImportFromProjection.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fileImportFromProjection.setMnemonic('P');
        fileImportFromProjection.setText("Import  2D Points File");
        fileImportFromProjection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileImportFromProjectionActionPerformed(evt);
            }
        });
        importMenu.add(fileImportFromProjection);

        directoryImportProjection.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        directoryImportProjection.setMnemonic('D');
        directoryImportProjection.setText("Import 2D Points Directory");
        directoryImportProjection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directoryImportProjectionActionPerformed(evt);
            }
        });
        importMenu.add(directoryImportProjection);

        menuFile.add(importMenu);
        menuFile.add(separator3);

        converters.setText("File Converters");

        pointsFileConverter_jMenuItem.setText("Points File Converter");
        pointsFileConverter_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pointsFileConverter_jMenuItemActionPerformed(evt);
            }
        });
        converters.add(pointsFileConverter_jMenuItem);

        matrixFileConverter_jMenuItem.setText("Matrix File Converter");
        matrixFileConverter_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matrixFileConverter_jMenuItemActionPerformed(evt);
            }
        });
        converters.add(matrixFileConverter_jMenuItem);

        pointsToMatlab_jMenuItem.setText("Points to Matlab");
        pointsToMatlab_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pointsToMatlab_jMenuItemActionPerformed(evt);
            }
        });
        converters.add(pointsToMatlab_jMenuItem);

        multiModalVolumeToPoints_jMenuItem.setText("Multimodal Volume to Points");
        multiModalVolumeToPoints_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multiModalVolumeToPoints_jMenuItemActionPerformed(evt);
            }
        });
        converters.add(multiModalVolumeToPoints_jMenuItem);

        menuFile.add(converters);

        menuBar.add(menuFile);

        menuEdit.setMnemonic('E');
        menuEdit.setText("Edit");

        editClean.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        editClean.setMnemonic('C');
        editClean.setText("Clean Projection");
        editClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCleanActionPerformed(evt);
            }
        });
        menuEdit.add(editClean);

        editDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        editDelete.setMnemonic('D');
        editDelete.setText("Delete Points");
        editDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDeleteActionPerformed(evt);
            }
        });
        menuEdit.add(editDelete);

        menuBar.add(menuEdit);

        menuTool.setMnemonic('T');
        menuTool.setText("Tool");

        memoryCheckMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        memoryCheckMenuItem.setMnemonic('H');
        memoryCheckMenuItem.setText("Memory Check");
        memoryCheckMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memoryCheckMenuItemActionPerformed(evt);
            }
        });
        menuTool.add(memoryCheckMenuItem);

        googleSearchsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        googleSearchsMenuItem.setMnemonic('G');
        googleSearchsMenuItem.setText("Google Searches");
        googleSearchsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                googleSearchsMenuItemActionPerformed(evt);
            }
        });
        menuTool.add(googleSearchsMenuItem);

        stopwordsOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        stopwordsOption.setMnemonic('G');
        stopwordsOption.setText("Manage Stopwords");
        stopwordsOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopwordsOptionActionPerformed(evt);
            }
        });
        menuTool.add(stopwordsOption);
        menuTool.add(separatorOptions1);

        connectivityMenu.setText("Connectivity");

        knnOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));
        knnOptions.setMnemonic('K');
        knnOptions.setText("Create KNN Connectivity");
        knnOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                knnOptionsActionPerformed(evt);
            }
        });
        connectivityMenu.add(knnOptions);

        delaunayOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        delaunayOptions.setMnemonic('T');
        delaunayOptions.setText("Create Delaunay Connectivity");
        delaunayOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delaunayOptionsActionPerformed(evt);
            }
        });
        connectivityMenu.add(delaunayOptions);

        mstOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        mstOptions.setMnemonic('M');
        mstOptions.setText("Create MST Connectivity");
        mstOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mstOptionsActionPerformed(evt);
            }
        });
        connectivityMenu.add(mstOptions);

        scalarConnectivityOptions.setText("Connectivity from Scalars");
        scalarConnectivityOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scalarConnectivityOptionsActionPerformed(evt);
            }
        });
        connectivityMenu.add(scalarConnectivityOptions);

        importConnectivity.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        importConnectivity.setMnemonic('I');
        importConnectivity.setText("Import Connectivity");
        importConnectivity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importConnectivityActionPerformed(evt);
            }
        });
        connectivityMenu.add(importConnectivity);

        exportConnectivity.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exportConnectivity.setMnemonic('E');
        exportConnectivity.setText("Export Connectivity");
        exportConnectivity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportConnectivityActionPerformed(evt);
            }
        });
        connectivityMenu.add(exportConnectivity);

        menuTool.add(connectivityMenu);

        scalarMenu.setText("Scalar");

        createScalarFromConnectionMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        createScalarFromConnectionMenuItem.setMnemonic('G');
        createScalarFromConnectionMenuItem.setText("Scalars from Connectivity");
        createScalarFromConnectionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createScalarFromConnectionMenuItemActionPerformed(evt);
            }
        });
        scalarMenu.add(createScalarFromConnectionMenuItem);

        joinScalarsOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        joinScalarsOptions.setMnemonic('J');
        joinScalarsOptions.setText("Join Scalars");
        joinScalarsOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinScalarsOptionsActionPerformed(evt);
            }
        });
        scalarMenu.add(joinScalarsOptions);

        calculateHCOptions.setMnemonic('H');
        calculateHCOptions.setText("Calculate Hierarchical Clustering");

        alinkHC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        alinkHC.setMnemonic('A');
        alinkHC.setText("ALINK");
        alinkHC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alinkHCActionPerformed(evt);
            }
        });
        calculateHCOptions.add(alinkHC);

        clinkHC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        clinkHC.setMnemonic('C');
        clinkHC.setText("CLINK (default)");
        clinkHC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clinkHCActionPerformed(evt);
            }
        });
        calculateHCOptions.add(clinkHC);

        slinkHC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        slinkHC.setMnemonic('S');
        slinkHC.setText("SLINK");
        slinkHC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slinkHCActionPerformed(evt);
            }
        });
        calculateHCOptions.add(slinkHC);

        scalarMenu.add(calculateHCOptions);

        importScalarsOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        importScalarsOption.setMnemonic('S');
        importScalarsOption.setText("Import Scalars");
        importScalarsOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importScalarsOptionActionPerformed(evt);
            }
        });
        scalarMenu.add(importScalarsOption);

        exportScalarsOption.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        exportScalarsOption.setMnemonic('x');
        exportScalarsOption.setText("Export Scalars");
        exportScalarsOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportScalarsOptionActionPerformed(evt);
            }
        });
        scalarMenu.add(exportScalarsOption);

        menuTool.add(scalarMenu);

        rulesMenu.setText("Rules");

        RulesFromGridMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        RulesFromGridMenuItem.setText("Create Rules");
        RulesFromGridMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RulesFromGridMenuItemActionPerformed(evt);
            }
        });
        rulesMenu.add(RulesFromGridMenuItem);

        exportRuleTermMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        exportRuleTermMenuItem.setText("Export Rule Terms");
        exportRuleTermMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportRuleTermMenuItemActionPerformed(evt);
            }
        });
        rulesMenu.add(exportRuleTermMenuItem);

        menuTool.add(rulesMenu);

        corpusMenu.setText("Corpus");

        loadCorporaOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        loadCorporaOptions.setMnemonic('C');
        loadCorporaOptions.setText("Load Corpus");
        loadCorporaOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadCorporaOptionsActionPerformed(evt);
            }
        });
        corpusMenu.add(loadCorporaOptions);

        exportCorporaOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        exportCorporaOptions.setMnemonic('X');
        exportCorporaOptions.setText("Export Corpus");
        exportCorporaOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportCorporaOptionsActionPerformed(evt);
            }
        });
        corpusMenu.add(exportCorporaOptions);

        createCorporaPDFOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        createCorporaPDFOptions.setMnemonic('P');
        createCorporaPDFOptions.setText("Crate Corpus from PDF Files");
        createCorporaPDFOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createCorporaPDFOptionsActionPerformed(evt);
            }
        });
        corpusMenu.add(createCorporaPDFOptions);

        menuTool.add(corpusMenu);

        coordinationMenu.setText("Coordination");

        distCoordinationMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        distCoordinationMenuItem.setMnemonic('D');
        distCoordinationMenuItem.setText("Create distance Coodination");
        distCoordinationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distCoordinationMenuItemActionPerformed(evt);
            }
        });
        coordinationMenu.add(distCoordinationMenuItem);

        identityCoordinationMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        identityCoordinationMenuItem.setMnemonic('E');
        identityCoordinationMenuItem.setText("Create Identity Coodination");
        identityCoordinationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identityCoordinationMenuItemActionPerformed(evt);
            }
        });
        coordinationMenu.add(identityCoordinationMenuItem);

        topicCoordinationMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        topicCoordinationMenuItem.setMnemonic('T');
        topicCoordinationMenuItem.setText("Create topic Coodination");
        topicCoordinationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topicCoordinationMenuItemActionPerformed(evt);
            }
        });
        coordinationMenu.add(topicCoordinationMenuItem);

        menuTool.add(coordinationMenu);

        titlesOption.setText("Titles");

        importtitles.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        importtitles.setText("Import Titles");
        importtitles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importtitlesActionPerformed(evt);
            }
        });
        titlesOption.add(importtitles);

        exporttitles.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        exporttitles.setText("ExportTitles");
        exporttitles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exporttitlesActionPerformed(evt);
            }
        });
        titlesOption.add(exporttitles);

        menuTool.add(titlesOption);

        movements_jMenu.setText("Movements");

        movePoints_jMenuItem.setText("Points Movements");
        movePoints_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movePoints_jMenuItemActionPerformed(evt);
            }
        });
        movements_jMenu.add(movePoints_jMenuItem);

        menuTool.add(movements_jMenu);
        menuTool.add(separatorOptions2);

        FeatureSelection.setMnemonic('O');
        FeatureSelection.setText("Feature Selection");
        FeatureSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FeatureSelectionActionPerformed(evt);
            }
        });
        menuTool.add(FeatureSelection);

        NeuralNetworkClassifier.setMnemonic('O');
        NeuralNetworkClassifier.setText("Neural Network Classifier");
        NeuralNetworkClassifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NeuralNetworkClassifierActionPerformed(evt);
            }
        });
        menuTool.add(NeuralNetworkClassifier);

        weightedFeatures_jMenuItem.setText("Weighted Features Construction");
        weightedFeatures_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weightedFeatures_jMenuItemActionPerformed(evt);
            }
        });
        menuTool.add(weightedFeatures_jMenuItem);
        menuTool.add(jSeparator1);

        toolOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        toolOptions.setMnemonic('O');
        toolOptions.setText("Tool Options");
        toolOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolOptionsActionPerformed(evt);
            }
        });
        menuTool.add(toolOptions);

        menuBar.add(menuTool);

        dataMiningMenu.setText("Data Mining");

        dataAnalysisMenu.setMnemonic('D');
        dataAnalysisMenu.setText("Data Analysis");

        createDistanceHistogramMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        createDistanceHistogramMenuItem.setMnemonic('H');
        createDistanceHistogramMenuItem.setText("Crate Distance Histogram");
        createDistanceHistogramMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createDistanceHistogramMenuItemActionPerformed(evt);
            }
        });
        dataAnalysisMenu.add(createDistanceHistogramMenuItem);

        viewSimilarityMatrixMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        viewSimilarityMatrixMenuItem.setText("View Similarity Matrix");
        viewSimilarityMatrixMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSimilarityMatrixMenuItemActionPerformed(evt);
            }
        });
        dataAnalysisMenu.add(viewSimilarityMatrixMenuItem);

        neighborHitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        neighborHitMenuItem.setMnemonic('N');
        neighborHitMenuItem.setText("Neighborhood Hit");
        neighborHitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                neighborHitMenuItemActionPerformed(evt);
            }
        });
        dataAnalysisMenu.add(neighborHitMenuItem);

        neighborPreservationMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        neighborPreservationMenuItem.setMnemonic('P');
        neighborPreservationMenuItem.setText("Neighborhood Preservation");
        neighborPreservationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                neighborPreservationMenuItemActionPerformed(evt);
            }
        });
        dataAnalysisMenu.add(neighborPreservationMenuItem);

        stressMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        stressMenuItem.setText("Projection Stress");
        stressMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stressMenuItemActionPerformed(evt);
            }
        });
        dataAnalysisMenu.add(stressMenuItem);

        layoutSimilarityMenuItem.setText("Layout Similarity");
        layoutSimilarityMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                layoutSimilarityMenuItemActionPerformed(evt);
            }
        });
        dataAnalysisMenu.add(layoutSimilarityMenuItem);

        sizeIncreaseMenuItem.setText("Size Increase");
        sizeIncreaseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sizeIncreaseMenuItemActionPerformed(evt);
            }
        });
        dataAnalysisMenu.add(sizeIncreaseMenuItem);

        euclideanDistanceMenuItem.setText("Euclidean Distance");
        euclideanDistanceMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                euclideanDistanceMenuItemActionPerformed(evt);
            }
        });
        dataAnalysisMenu.add(euclideanDistanceMenuItem);

        jMenuItem1.setText("Neighborhood Hit Group");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        dataAnalysisMenu.add(jMenuItem1);

        jMenuItem2.setText("Neighborhood Preservation Group");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        dataAnalysisMenu.add(jMenuItem2);

        jMenuItem5.setText("Teste Todos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        dataAnalysisMenu.add(jMenuItem5);

        dataMiningMenu.add(dataAnalysisMenu);

        clusteringMenu.setMnemonic('C');
        clusteringMenu.setText("Clustering");

        multidimensionalMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        multidimensionalMenuItem.setText("Multidimensional Data");
        multidimensionalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multidimensionalMenuItemActionPerformed(evt);
            }
        });
        clusteringMenu.add(multidimensionalMenuItem);

        silhouetteCoefficientMenuItem.setText("Silhouette Coefficient");
        silhouetteCoefficientMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                silhouetteCoefficientMenuItemActionPerformed(evt);
            }
        });
        clusteringMenu.add(silhouetteCoefficientMenuItem);

        jMenuItem4.setText("Silhouette Coefficient Group");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        clusteringMenu.add(jMenuItem4);

        dataMiningMenu.add(clusteringMenu);

        menuBar.add(dataMiningMenu);

        menuWindows.setMnemonic('W');
        menuWindows.setText("Windows");
        menuWindows.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                menuWindowsMenuSelected(evt);
            }
        });

        alignVerticalMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        alignVerticalMenuItem.setMnemonic('W');
        alignVerticalMenuItem.setText("Align Windows Vertically");
        alignVerticalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignVerticalMenuItemActionPerformed(evt);
            }
        });
        menuWindows.add(alignVerticalMenuItem);
        menuWindows.add(separator4);

        menuBar.add(menuWindows);

        menuHelp.setMnemonic('H');
        menuHelp.setText("Help");

        about.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        about.setMnemonic('A');
        about.setText("About");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        menuHelp.add(about);

        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void createFilterLabelToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createFilterLabelToggleButtonActionPerformed
        Viewer.setType(VertexSelectionFactory.SelectionType.CREATE_FILTER_TOPIC);
    }//GEN-LAST:event_createFilterLabelToggleButtonActionPerformed

    private void menuWindowsMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_menuWindowsMenuSelected
        for (JComponent c : this.windows.keySet()) {
            if (c instanceof Viewer) {
                Viewer gv = (Viewer) c;
                this.windows.get(gv).setText(gv.getTitle());
            } else if (c instanceof JInternalFrame.JDesktopIcon) {
                JInternalFrame.JDesktopIcon icon = (JInternalFrame.JDesktopIcon) c;
                Viewer gv = (Viewer) icon.getInternalFrame();
                this.windows.get(gv).setText(gv.getTitle());
            }
        }
    }//GEN-LAST:event_menuWindowsMenuSelected

    private void desktopComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_desktopComponentRemoved

        if (evt.getChild() instanceof Viewer) {
            Viewer gv = (Viewer) evt.getChild();
            this.menuWindows.remove(this.windows.get(gv));
        } else if (evt.getChild() instanceof JInternalFrame.JDesktopIcon) {
            JInternalFrame.JDesktopIcon icon = (JInternalFrame.JDesktopIcon) evt.getChild();
            this.menuWindows.remove(this.windows.get(icon));
        }
    }//GEN-LAST:event_desktopComponentRemoved

    private void desktopComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_desktopComponentAdded
        if (evt.getChild() instanceof Viewer) {
            final Viewer gv = (Viewer) evt.getChild();

            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(gv.getTitle());
            this.windows.put(gv, menuItem);

            this.windowsButtonGroup.add(menuItem);
            menuItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ev) {
                    try {
                        gv.setSelected(true);
                    } catch (PropertyVetoException e) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                    }
                }
            });

            this.menuWindows.add(menuItem);
            menuItem.setSelected(true);

        } else if (evt.getChild() instanceof JInternalFrame.JDesktopIcon) {
            JInternalFrame.JDesktopIcon icon = (JInternalFrame.JDesktopIcon) evt.getChild();
            final Viewer gv = (Viewer) icon.getInternalFrame();

            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(gv.getTitle());
            this.windows.put(icon, menuItem);

            this.windowsButtonGroup.add(menuItem);
            menuItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ev) {
                    try {
                        gv.setSelected(true);
                        gv.setIcon(false);
                        gv.moveToFront();
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            this.menuWindows.add(menuItem);
            menuItem.setSelected(false);
        }
    }//GEN-LAST:event_desktopComponentAdded

    private void topicCoordinationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topicCoordinationMenuItemActionPerformed
        JInternalFrame[] allframes = this.desktop.getAllFrames();

        if (allframes != null) {
            TopicMappingView tmv = TopicMappingView.getInstance(this);
            tmv.display(allframes);
        }
    }//GEN-LAST:event_topicCoordinationMenuItemActionPerformed

    private void identityCoordinationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identityCoordinationMenuItemActionPerformed
        JInternalFrame[] allframes = this.desktop.getAllFrames();

        if (allframes != null) {
            IdentityMappingView emv = IdentityMappingView.getInstance(this);
            emv.display(allframes);
        }
}//GEN-LAST:event_identityCoordinationMenuItemActionPerformed

    private void distCoordinationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distCoordinationMenuItemActionPerformed
        JInternalFrame[] allframes = this.desktop.getAllFrames();

        if (allframes != null) {
            DistanceMappingView dmv = DistanceMappingView.getInstance(this);
            dmv.display(allframes);
        }
    }//GEN-LAST:event_distCoordinationMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        JInternalFrame[] allframes = this.desktop.getAllFrames();

        if (allframes != null) {
            for (JInternalFrame jif : allframes) {
                try {
                    jif.setSelected(true);
                    int result = ((Viewer) jif).saveOnClosing();

                    if (result == javax.swing.JOptionPane.CANCEL_OPTION) {
                        return;
                    }

                } catch (PropertyVetoException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void goToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goToolbarButtonActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();
        

        if (gv != null) {
            if (OpenDialog.checkCorpus(gv.getGraph(), this)) {
                try {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    Scalar s = gv.getGraph().createQueryScalar(this.searchToolbarTextField.getText());
                    gv.updateScalars(s);

                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_goToolbarButtonActionPerformed

    private void searchToolbarTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchToolbarTextFieldKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            this.goToolbarButtonActionPerformed(null);
        }
    }//GEN-LAST:event_searchToolbarTextFieldKeyPressed

    private void runForceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runForceButtonActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            boolean start = gv.runForce();
            if (!start) {
                this.runForceButton.setIcon(new javax.swing.ImageIcon(getClass().
                        getResource("/toolbarButtonGraphics/media/Stop16.gif")));
                this.runForceButton.setToolTipText("Stop Force Directed Layout");
            } else {
                this.runForceButton.setIcon(new javax.swing.ImageIcon(getClass().
                        getResource("/toolbarButtonGraphics/media/Play16.gif")));
                this.runForceButton.setToolTipText("Run Force Directed Layout");
            }
        }
    }//GEN-LAST:event_runForceButtonActionPerformed

    private void toolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolButtonActionPerformed
        this.toolOptionsActionPerformed(evt);
    }//GEN-LAST:event_toolButtonActionPerformed

    private void zoomInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInButtonActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();
        if (gv != null) {
            gv.zoomIn();
        }
    }//GEN-LAST:event_zoomInButtonActionPerformed

    private void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutButtonActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();
        if (gv != null) {
            gv.zoomOut();
        }
    }//GEN-LAST:event_zoomOutButtonActionPerformed

    private void cleanLabelsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanLabelsButtonActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();
        if (gv != null) {
            gv.cleanSelection(true);
            gv.cleanTopics();
            gv.updateImage();
        }
    }//GEN-LAST:event_cleanLabelsButtonActionPerformed

    private void highlightLabelToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highlightLabelToggleButtonActionPerformed
        Viewer.setHighlightTopic(!Viewer.isHighlightTopic());
    }//GEN-LAST:event_highlightLabelToggleButtonActionPerformed

    private void showAllLabelsToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllLabelsToggleButtonActionPerformed
        Topic.setShowTopics(!Topic.isShowTopics());
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            gv.repaint();
        }
    }//GEN-LAST:event_showAllLabelsToggleButtonActionPerformed

    private void createLabelsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createLabelsButtonActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            try {
                TopicClusters labels = new TopicClusters(gv);

                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                labels.execute(this);
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (IOException ex) {
                Logger.getLogger(ProjectionExplorerView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_createLabelsButtonActionPerformed

    private void createScalarFromDocButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createScalarFromDocButtonActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            Vertex v = gv.getSelectedVertex();

            if (v != null) {
                CreateDistanceScalar ds = CreateDistanceScalar.getInstance(this);
                int result = ds.display(gv);

                if (result == CreateDistanceScalar.GENERATED) {
                    gv.updateScalars(ds.getScalar());
                }
            } else {
                JOptionPane.showMessageDialog(this, "One vertex must be selected.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_createScalarFromDocButtonActionPerformed

    private void movePointToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movePointToggleButtonActionPerformed
        Viewer.setMovePoints(this.movePointToggleButton.isSelected());
    }//GEN-LAST:event_movePointToggleButtonActionPerformed

    private void coordinatedSelectToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coordinatedSelectToggleButtonActionPerformed
        Viewer.setType(VertexSelectionFactory.SelectionType.COORD_SELECT);
    }//GEN-LAST:event_coordinatedSelectToggleButtonActionPerformed

    private void viewContentToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewContentToggleButtonActionPerformed
        Viewer.setType(VertexSelectionFactory.SelectionType.VIEW_CONTENT);
    }//GEN-LAST:event_viewContentToggleButtonActionPerformed

    private void selectToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectToggleButtonActionPerformed
        Viewer.setType(VertexSelectionFactory.SelectionType.SELECT_GRAPH);
    }//GEN-LAST:event_selectToggleButtonActionPerformed

    private void findToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findToggleButtonActionPerformed
        Viewer.setType(VertexSelectionFactory.SelectionType.FIND_SUBGRAPH);
    }//GEN-LAST:event_findToggleButtonActionPerformed

    private void cutToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutToggleButtonActionPerformed
        Viewer.setType(VertexSelectionFactory.SelectionType.SPLIT_GRAPH);
    }//GEN-LAST:event_cutToggleButtonActionPerformed

    private void createLabelToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createLabelToggleButtonActionPerformed
        Viewer.setType(VertexSelectionFactory.SelectionType.CREATE_TOPIC);
    }//GEN-LAST:event_createLabelToggleButtonActionPerformed

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutActionPerformed
        Help.getInstance(this).display();
    }//GEN-LAST:event_aboutActionPerformed

    private void alignVerticalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignVerticalMenuItemActionPerformed
        int nrWindows = this.desktop.getAllFrames().length;

        if (nrWindows > 0) {
            java.awt.Dimension deskSize = this.desktop.getSize();
            int width = deskSize.width / nrWindows;
            int height = deskSize.height;

            for (int i = 0; i < nrWindows; i++) {
                if (this.desktop.getAllFrames()[i] instanceof Viewer) {
                    this.desktop.getAllFrames()[i].setBounds(i * width, 0, width, height);
                }
            }
        }
    }//GEN-LAST:event_alignVerticalMenuItemActionPerformed

    private void createCorporaPDFOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createCorporaPDFOptionsActionPerformed
        CreatePDFCorpora.getInstance(this).display();
    }//GEN-LAST:event_createCorporaPDFOptionsActionPerformed

    private void exportCorporaOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportCorporaOptionsActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            ExportCorpus.getInstance(this).display(gv.getCurrentScalar(), gv.getGraph());
        }
    }//GEN-LAST:event_exportCorporaOptionsActionPerformed

    private void loadCorporaOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadCorporaOptionsActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            gv.getGraph().setCorpus(null);
            OpenDialog.checkCorpus(gv.getGraph(), this);
        }
    }//GEN-LAST:event_loadCorporaOptionsActionPerformed

    private void exportRuleTermMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportRuleTermMenuItemActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            javax.swing.JFileChooser file = new javax.swing.JFileChooser();
            file.resetChoosableFileFilters();
            file.setAcceptAllFileFilterUsed(false);
            file.setMultiSelectionEnabled(false);
            file.setCurrentDirectory(new File("config"));

            String[] choices = {"Session", "Last Run", "Cancel"};
            int response = JOptionPane.showOptionDialog(null, "Which list of terms from labels?", "Choose the List",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, "Cancel");

            if (response != 2) { // Not Cancel

                String[] choicesW = {"No Weight", "All Weights", "Max Weight", "Cancel"};
                int responseW = JOptionPane.showOptionDialog(null, "Export with Weights?", "Export with Weights",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choicesW, "Cancel");
                if (responseW != 3) { // Not Cancel

                    int result = file.showSaveDialog(this);

                    if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                        String filename = file.getSelectedFile().getAbsolutePath();

                        SystemPropertiesManager m = SystemPropertiesManager.getInstance();
                        m.setProperty("STA.FILE", file.getSelectedFile().getName());

                        if (!filename.toLowerCase().endsWith(".data")) {
                            filename = filename.concat(".data");
                        }

                        PrintWriter out;
                        try {
                            out = new PrintWriter(new FileWriter(filename));
                            List<String> sortedList = new ArrayList<String>();

                            if (response == 0) //Session
                            {
                                if (responseW == 1) //All Weights
                                {
                                    sortedList.addAll(gv.getGraph().getTopicData().getTermSetAccumW());
                                } else if (responseW == 0) //No Weight
                                {
                                    sortedList.addAll(gv.getGraph().getTopicData().getTermSetAccum());
                                } else if (responseW == 2) { //Max Weights

                                    List<String> tmpSortedList = new ArrayList<String>();
                                    tmpSortedList.addAll(gv.getGraph().getTopicData().getTermSetAccumW());
                                    Collections.sort(tmpSortedList);
                                    String curWord = "", word;
                                    double weightMax = 0.0;
                                    int wordCount = 0;
                                    for (String line : tmpSortedList) {
                                        word = line.split(";")[0];
                                        if (word.equals(curWord)) {
                                            wordCount++;
                                            weightMax = Math.max(weightMax, (new Double(line.split(";")[1]).doubleValue()));
                                        } else {
                                            if (wordCount > 0) {
                                                sortedList.add(curWord + ";" + (weightMax));
                                            }
                                            curWord = word;
                                            weightMax = (new Double(line.split(";")[1]).doubleValue());
                                            wordCount = 1;
                                        }
                                    }
                                    sortedList.add(curWord + ";" + (weightMax));
                                } else //Run
                                if (responseW == 1) //All Weights
                                {
                                    sortedList.addAll(gv.getGraph().getTopicData().getTermSetRunW());
                                } else if (responseW == 0) //No Weight
                                {
                                    sortedList.addAll(gv.getGraph().getTopicData().getTermSetRun());
                                } else if (responseW == 2) { //Max Weights

                                    List<String> tmpSortedList = new ArrayList<String>();
                                    tmpSortedList.addAll(gv.getGraph().getTopicData().getTermSetRunW());
                                    Collections.sort(tmpSortedList);
                                    String curWord = "", word;
                                    double weightMax2 = 0.0;
                                    int wordCount = 0;
                                    for (String line : tmpSortedList) {
                                        word = line.split(";")[0];
                                        if (word.equals(curWord)) {
                                            wordCount++;
                                            weightMax2 = Math.max(weightMax2, (new Double(line.split(";")[1]).doubleValue()));
                                        } else {
                                            if (wordCount > 0) {
                                                sortedList.add(curWord + ";" + (weightMax2));
                                            }
                                            curWord = word;
                                            weightMax2 = (new Double(line.split(";")[1]).doubleValue());
                                            wordCount = 1;
                                        }
                                    }
                                    sortedList.add(curWord + ";" + (weightMax2));
                                }
                            }


                            Collections.sort(sortedList);
                            for (String term : sortedList) {
                                out.println(term.replaceAll("<>", ""));
                            }
                            out.close();
                        } catch (IOException ex) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_exportRuleTermMenuItemActionPerformed

    private void RulesFromGridMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RulesFromGridMenuItemActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            if (OpenDialog.checkCorpus(gv.getGraph(), this)) {
                RulesTopicGrid rules = new RulesTopicGrid(gv, this);
                rules.execute();
            }
        }
    }//GEN-LAST:event_RulesFromGridMenuItemActionPerformed

    private void exportScalarsOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportScalarsOptionActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            String filename = gv.getGraph().getProjectionData().getSourceFile();

            int result = SaveDialog.showSaveDialog(new SCALARFilter(), this, filename);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    filename = SaveDialog.getFilename();
                    Util.exportScalars(gv.getGraph(), filename);
                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_exportScalarsOptionActionPerformed

    private void importScalarsOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importScalarsOptionActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            int result = OpenDialog.showOpenDialog(new SCALARFilter(), this);

            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                try {
                    String filename = OpenDialog.getFilename();
                    Util.importScalars(gv.getGraph(), filename);
                    gv.updateScalars(null);
                } catch (IOException ex) {
                    Logger.getLogger(ProjectionExplorerView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_importScalarsOptionActionPerformed

    private void createScalarFromConnectionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createScalarFromConnectionMenuItemActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            Scalar s = gv.getGraph().createScalarByConnection(gv.getCurrentConnectivity());
            gv.updateScalars(s);
        }
    }//GEN-LAST:event_createScalarFromConnectionMenuItemActionPerformed

    private void slinkHCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slinkHCActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            gv.createHCScalars(HierarchicalClusteringType.SLINK);
        }
    }//GEN-LAST:event_slinkHCActionPerformed

    private void clinkHCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clinkHCActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            gv.createHCScalars(HierarchicalClusteringType.CLINK);
        }
    }//GEN-LAST:event_clinkHCActionPerformed

    private void alinkHCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alinkHCActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            gv.createHCScalars(HierarchicalClusteringType.ALINK);
        }
    }//GEN-LAST:event_alinkHCActionPerformed

    private void toolOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolOptionsActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();
        ToolConfiguration.getInstance(this).display(gv);
    }//GEN-LAST:event_toolOptionsActionPerformed

    private void stopwordsOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopwordsOptionActionPerformed
        WordsManager.getInstance(this, true).display();
    }//GEN-LAST:event_stopwordsOptionActionPerformed

    private void memoryCheckMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memoryCheckMenuItemActionPerformed
        visualizer.view.tools.MemoryCheck.showMemoryCheck();
    }//GEN-LAST:event_memoryCheckMenuItemActionPerformed

    private void delaunayOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delaunayOptionsActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            gv.getGraph().perturb();

            //Creating new Delaunay triangulation
            float[][] points = new float[gv.getGraph().getVertex().size()][];
            for (int i = 0; i < points.length; i++) {
                points[i] = new float[2];
                points[i][0] = gv.getGraph().getVertex().get(i).getX();
                points[i][1] = gv.getGraph().getVertex().get(i).getY();
            }

            Delaunay d = new Delaunay();
            Pair[][] neighborhood = d.execute(points);
            Connectivity con = new Connectivity(PExConstants.DELAUNAY);
            con.create(gv.getGraph().getVertex(), neighborhood);
            gv.getGraph().addConnectivity(con);

            //set the connectivities
            gv.updateConnectivities(con);

            gv.updateImage();
        }
}//GEN-LAST:event_delaunayOptionsActionPerformed

    private void googleSearchsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_googleSearchsMenuItemActionPerformed
        GoogleView.getInstance(this).display(null);
    }//GEN-LAST:event_googleSearchsMenuItemActionPerformed

    private void knnOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_knnOptionsActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            Connectivity con = KnnConnectivity.getInstance(this).display(gv.getGraph());
            gv.updateConnectivities(con);
        }
    }//GEN-LAST:event_knnOptionsActionPerformed

    private void editDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDeleteActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            gv.deleteSelectedVertices();
        }
    }//GEN-LAST:event_editDeleteActionPerformed

    private void editCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCleanActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            gv.cleanSelection(true);
            gv.cleanTopics();
            gv.updateImage();
        }
    }//GEN-LAST:event_editCleanActionPerformed

    private void fileImportFromProjectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileImportFromProjectionActionPerformed
        Graph g = ImportProjection.getInstance(this).display();

        if (g != null) {
            this.addGraph2D(g, true, "New Projection");
        }
    }//GEN-LAST:event_fileImportFromProjectionActionPerformed

    private void fileExportToProjectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileExportToProjectionActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            String filename = gv.getGraph().getProjectionData().getSourceFile();

            int result = SaveDialog.showSaveDialog(new PROJFilter(), this, filename);

            if (result == JFileChooser.APPROVE_OPTION) {
                String projFilename = SaveDialog.getFilename();

                try {
                    Matrix projection = Util.exportProjection(gv.getGraph(), gv.getCurrentScalar());
                    projection.save(projFilename);
                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_fileExportToProjectionActionPerformed

    private void fileExportToVtkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileExportToVtkActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            String filename = gv.getGraph().getProjectionData().getSourceFile();

            int result = SaveDialog.showSaveDialog(new VTKFilter(), this, filename);

            if (result == JFileChooser.APPROVE_OPTION) {
                filename = SaveDialog.getFilename();

                try {
                    Util.exportVTKFile(gv.getGraph(), filename, gv.getCurrentConnectivity());
                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_fileExportToVtkActionPerformed

    private void fileExportToPngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileExportToPngActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            String filename = gv.getGraph().getProjectionData().getSourceFile();

            int result = SaveDialog.showSaveDialog(new PNGFilter(), this, filename);

            if (result == JFileChooser.APPROVE_OPTION) {
                filename = SaveDialog.getFilename();

                try {
                    gv.saveToPngImageFile(filename);
                } catch (IOException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                    JOptionPane.showMessageDialog(this, e.getMessage(),
                            "Problems saving the file", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_fileExportToPngActionPerformed

    private void fileSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSaveActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null && gv.getGraph().getProjectionData() != null) {
            String filename = gv.getGraph().getProjectionData().getSourceFile();

            int result = SaveDialog.showSaveDialog(new XMLFilter(), this, filename);

            if (result == JFileChooser.APPROVE_OPTION) {
                filename = SaveDialog.getFilename();

                try {
                    XMLGraphWriter.save(gv.getGraph(), gv.getGraph().getDescription(), filename);
                    gv.setTitle(filename);

                    gv.setGraphChanged(false);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Problems with the file", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_fileSaveActionPerformed

    private void fileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileOpenActionPerformed
        int result = OpenDialog.showOpenDialog(new XMLFilter(), this);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = OpenDialog.getFilename();

            try {
                XMLGraphParser parser = new XMLGraphParser();
                Graph graph = parser.parse(filename);

                if (graph != null) {
                    this.addGraph2D(graph, false, filename);
                }
            } catch (IOException e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                JOptionPane.showMessageDialog(this, e.getMessage(),
                        "Problems with the file", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_fileOpenActionPerformed

    private void fileNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileNewActionPerformed
        Graph graph = new Graph();
        int result = ProjectionWizardView.getInstance(this).display(graph);

        if (result == ProjectionWizardView.PROJECTION_GENERATED) {
            this.addGraph2D(graph, true, "New Projection");
        }
    }//GEN-LAST:event_fileNewActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        this.fileSaveActionPerformed(evt);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        this.fileOpenActionPerformed(evt);
    }//GEN-LAST:event_openButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        this.fileNewActionPerformed(evt);
    }//GEN-LAST:event_newButtonActionPerformed

    private void fileExportToEpsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileExportToEpsActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            
            String filename = gv.getGraph().getProjectionData().getSourceFile();

            int result = SaveDialog.showSaveDialog(new EPSFilter(), this, filename);

            if (result == JFileChooser.APPROVE_OPTION) {
                filename = SaveDialog.getFilename();

                try {
                    gv.saveToEpsImageFile(filename);
                } catch (IOException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                    JOptionPane.showMessageDialog(this, e.getMessage(),
                            "Problems saving the file", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_fileExportToEpsActionPerformed

    private void importConnectivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importConnectivityActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            int result = OpenDialog.showOpenDialog(new CONFilter(), this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = OpenDialog.getFilename();

                try {
                    Connectivity con = Util.importConnectivity(gv.getGraph().getVertex(), filename);
                    gv.getGraph().addConnectivity(con);
                    gv.updateConnectivities(con);

                    gv.updateImage();

                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
}//GEN-LAST:event_importConnectivityActionPerformed

    private void showVertexLabelToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showVertexLabelToggleButtonActionPerformed
        Viewer.setType(VertexSelectionFactory.SelectionType.SHOW_VERTEX_LABEL);
    }//GEN-LAST:event_showVertexLabelToggleButtonActionPerformed

    private void mstOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mstOptionsActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            Connectivity con = MSTConnectivity.getInstance(this).display(gv.getGraph());
            gv.updateConnectivities(con);
        }
    }//GEN-LAST:event_mstOptionsActionPerformed

    private void exportConnectivityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportConnectivityActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            String filename = gv.getGraph().getProjectionData().getSourceFile();
            int result = SaveDialog.showSaveDialog(new CONFilter(), this, filename);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    filename = SaveDialog.getFilename();
                    Util.exportConnectivity(gv.getCurrentConnectivity(), filename);
                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_exportConnectivityActionPerformed

    private void importtitlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importtitlesActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            int result = OpenDialog.showOpenDialog(new TITLEFilter(), this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = OpenDialog.getFilename();

                try {
                    Util.importTitles(gv.getGraph(), filename);
                    gv.updateTitles(gv.getCurrentTitle());
                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_importtitlesActionPerformed

    private void exporttitlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exporttitlesActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            String filename = gv.getGraph().getProjectionData().getSourceFile();
            int result = SaveDialog.showSaveDialog(new TITLEFilter(), this, filename);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    filename = SaveDialog.getFilename();
                    Util.exportTitles(gv.getGraph(), filename);
                } catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_exporttitlesActionPerformed

    private void searchTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            this.goButtonActionPerformed(null);
        }
    }//GEN-LAST:event_searchTextFieldKeyPressed

    private void goButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goButtonActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            Pattern p = Pattern.compile(this.searchTextField.getText().trim().toLowerCase());

            int begin = this.pointsList.getSelectedIndex() + 1;
            int end = gv.getGraph().getVertex().size() - 1;

            boolean stop = false;
            boolean restart = true;
            while (!stop) {
                for (int i = begin; i <= end; i++) {
                    Matcher m = p.matcher(this.pointsListModel.get(i).toString().trim().toLowerCase());
                    if (m.find()) {
                        this.pointsList.setSelectedIndex(i);
                        this.pointsList.ensureIndexIsVisible(i);
                        gv.markNeighbors((Vertex) this.pointsListModel.get(i));
                        stop = true;
                        break;
                    }
                }

                if (restart) {
                    end = begin - 2;
                    begin = 0;
                    restart = false;
                } else {
                    stop = true;
                }
            }
        }
    }//GEN-LAST:event_goButtonActionPerformed

    private void pointsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pointsListMouseClicked
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            javax.swing.JList source = (javax.swing.JList) evt.getSource();
            Vertex vertex = (Vertex) source.getSelectedValue();

            if (evt.getClickCount() == 1) {
                gv.markNeighbors((Vertex) this.pointsList.getSelectedValue());
            } else if (evt.getClickCount() == 2) {
                if (OpenDialog.checkCorpus(gv.getGraph(), this)) {
                    MultipleFileView.getInstance(this).display(gv, vertex);
                }

                gv.markNeighbors((Vertex) this.pointsList.getSelectedValue());
            }
        }
    }//GEN-LAST:event_pointsListMouseClicked

    private void nearestNeighborsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nearestNeighborsListMouseClicked
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            javax.swing.JList source = (javax.swing.JList) evt.getSource();
            Vertex vertex = (Vertex) source.getSelectedValue();

            if (evt.getClickCount() == 2) {
                if (OpenDialog.checkCorpus(gv.getGraph(), this)) {
                    MultipleFileView.getInstance(this).display(gv, vertex);
                }
            }
        }
    }//GEN-LAST:event_nearestNeighborsListMouseClicked

    private void desktopComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_desktopComponentResized
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();

        if (gv != null) {
            gv.setSize(this.desktop.getSize());
        }
    }//GEN-LAST:event_desktopComponentResized

    private void directoryImportProjectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directoryImportProjectionActionPerformed
        OpenDialog.getJFileChooser().setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        int result = OpenDialog.showOpenDialog(new DIRFilter(), this);

        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            String directory = OpenDialog.getFilename();

            //get projection files
            File f = new File(directory);
            File[] files = f.listFiles();

            for (File file : files) {
                String filename = file.getAbsolutePath();
                if (filename.toLowerCase().endsWith(".prj")) {
                    try {
                        Graph g = Util.importProjection(filename);

                        if (g != null) {
                            this.addGraph2D(g, true, "New Projection");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ProjectionExplorerView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        OpenDialog.getJFileChooser().setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
    }//GEN-LAST:event_directoryImportProjectionActionPerformed

    private void createDistanceHistogramMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createDistanceHistogramMenuItemActionPerformed
        DistanceHistogramView.getInstance(this).display();
    }//GEN-LAST:event_createDistanceHistogramMenuItemActionPerformed

    private void viewSimilarityMatrixMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewSimilarityMatrixMenuItemActionPerformed
        SimilarityMatrixView.getInstance(this).display();
}//GEN-LAST:event_viewSimilarityMatrixMenuItemActionPerformed

    private void neighborHitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_neighborHitMenuItemActionPerformed
        
        NeighborhoodHitView.getInstance(this).display();
        
    }//GEN-LAST:event_neighborHitMenuItemActionPerformed

    private void neighborPreservationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_neighborPreservationMenuItemActionPerformed
        NeighborhoodPreservationView.getInstance(this).display();
    }//GEN-LAST:event_neighborPreservationMenuItemActionPerformed

private void joinScalarsOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinScalarsOptionsActionPerformed
    Viewer gv = (Viewer) this.desktop.getSelectedFrame();

    if (gv != null) {
        JoinScalars.getInstance(this).display(gv);
    }
}//GEN-LAST:event_joinScalarsOptionsActionPerformed

private void multidimensionalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multidimensionalMenuItemActionPerformed
    Viewer gv = (Viewer) this.desktop.getSelectedFrame();

    if (gv != null) {
        Scalar s = MultidimensionalClusteringView.getInstance(this).display(gv.getGraph());
        gv.updateScalars(s);
    }
}//GEN-LAST:event_multidimensionalMenuItemActionPerformed

private void stressMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stressMenuItemActionPerformed
    Viewer gv = (Viewer) this.desktop.getSelectedFrame();

    if (gv != null) {
        CalculateStressView.getInstance(this).display(gv.getGraph());
    }
}//GEN-LAST:event_stressMenuItemActionPerformed

private void FeatureSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FeatureSelectionActionPerformed
    FeatureSelectionView ThisFeatureSelection = new FeatureSelectionView();
    ThisFeatureSelection.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    ThisFeatureSelection.setVisible(true);
}//GEN-LAST:event_FeatureSelectionActionPerformed

private void NeuralNetworkClassifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NeuralNetworkClassifierActionPerformed
    NeuralNetworkClassifierView ThisClassifier = new NeuralNetworkClassifierView();
    ThisClassifier.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    ThisClassifier.setVisible(true);
}//GEN-LAST:event_NeuralNetworkClassifierActionPerformed

private void ReproduceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReproduceButtonActionPerformed
    Viewer gv = (Viewer) this.desktop.getSelectedFrame();

    if (gv != null && gv.getGraph().getProjectionData() != null) {
        try {
            XMLGraphWriter.save(gv.getGraph(), gv.getGraph().getDescription(), "tmp_reproduced.xml");
            gv.setGraphChanged(false);
            XMLGraphParser parser = new XMLGraphParser();
            Graph graph = parser.parse("tmp_reproduced.xml");

            if (graph != null) {
                this.addGraph2D(graph, false, "Reproduced " + gv.getTitle());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Problems with the file", JOptionPane.ERROR_MESSAGE);
        }
    }
    File ToDelete = new File("tmp_reproduced.xml");
    if (ToDelete.exists()) {
        ToDelete.delete();
    }
}//GEN-LAST:event_ReproduceButtonActionPerformed

private void ReloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReloadButtonActionPerformed
    Viewer gv = (Viewer) this.desktop.getSelectedFrame();
    if (gv != null) {
        if (OpenDialog.forceCheck(gv.getGraph(), gv.getProjectionExplorerView())) {
            gv.updateImage();
            gv.setShowImages(false);
        }
    }
    this.updateButtons();
}//GEN-LAST:event_ReloadButtonActionPerformed

private void pointsFileConverter_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pointsFileConverter_jMenuItemActionPerformed
// TODO add your handling code here:
    PointsFileConverterView pointsConverter = new PointsFileConverterView();
    pointsConverter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    pointsConverter.setVisible(true);
}//GEN-LAST:event_pointsFileConverter_jMenuItemActionPerformed

private void scalarConnectivityOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scalarConnectivityOptionsActionPerformed
    Viewer gv = (Viewer) this.desktop.getSelectedFrame();

    if (gv != null) {
        Connectivity con = ScalarConnectivity.getInstance(this).display(gv.getGraph(), gv.getCurrentScalar());
        gv.updateConnectivities(con);
    }
}//GEN-LAST:event_scalarConnectivityOptionsActionPerformed

private void matrixFileConverter_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matrixFileConverter_jMenuItemActionPerformed
// TODO add your handling code here:
    DistanceMatrixConverterView matrixConverter = new DistanceMatrixConverterView();//GEN-LAST:event_matrixFileConverter_jMenuItemActionPerformed
        matrixConverter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        matrixConverter.setVisible(true);
    }

    private void movePoints_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movePoints_jMenuItemActionPerformed
        // TODO add your handling code here:
        MovePointsView.getInstance(this).display();
    }//GEN-LAST:event_movePoints_jMenuItemActionPerformed

    private void weightedFeatures_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weightedFeatures_jMenuItemActionPerformed
        // TODO add your handling code here:
        FeaturesWeightView.getInstance(this).display();
    }//GEN-LAST:event_weightedFeatures_jMenuItemActionPerformed

    private void pointsToMatlab_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pointsToMatlab_jMenuItemActionPerformed
        // TODO add your handling code here:
        PointsToMatlabView pointsConverter = new PointsToMatlabView(this);
        pointsConverter.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pointsConverter.setVisible(true);
    }//GEN-LAST:event_pointsToMatlab_jMenuItemActionPerformed

    private void multiModalVolumeToPoints_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multiModalVolumeToPoints_jMenuItemActionPerformed
        // TODO add your handling code here:
        MultimodalVolumeReaderView mmVolumeReader = new MultimodalVolumeReaderView(this);
        mmVolumeReader.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mmVolumeReader.setVisible(true);
    }//GEN-LAST:event_multiModalVolumeToPoints_jMenuItemActionPerformed

    private void transformProjection(ArrayList<Retangulo> projection) {
        Graph graph = ((Viewer) this.desktop.getSelectedFrame()).getGraph();
        ArrayList<Vertex> vertices = graph.getVertex();
        for( int i = 0; i < vertices.size(); ++i ) {
            if( vertices.get(i).isDrawAsImages() ) {
                int w = vertices.get(i).getImage().getWidth(null);
                int h = vertices.get(i).getImage().getHeight(null);
                int x = ((int) vertices.get(i).getX()) - w/2;
                int y = ((int) vertices.get(i).getY()) - h/2;
                if( Vertex.isDrawClassOnImage() ) {
                    x -= 2;
                    y -= 2;
                    w += 3;
                    h += 3;
                }
                projection.add(new Retangulo(x, y, w, h));
            } else {

                int x = ((int) vertices.get(i).getX()) - vertices.get(i).getRay();
                int y = ((int) vertices.get(i).getY()) - vertices.get(i).getRay();
                int raio = vertices.get(i).getRay()*2;
                projection.add(new Retangulo(x, y, raio, raio));
            }
            projection.get(projection.size()-1).setId(i);

        }
    }
    
    
    private void removeOverlapJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeOverlapJButtonActionPerformed
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();
        if( gv != null ) {
            Graph graph = gv.getGraph();
            if( graph != null ) {       
                initialProjection = new ArrayList<>();
                transformProjection(initialProjection);
                System.out.println("SIZE: "+initialProjection.size());
                RemoveOverlapView removeOverlap = new RemoveOverlapView();
                removeOverlap.setViewer(gv);
                removeOverlap.setVisible(true);
            }            
        }
    }//GEN-LAST:event_removeOverlapJButtonActionPerformed

    private void layoutSimilarityMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_layoutSimilarityMenuItemActionPerformed
        finalProjection = new ArrayList<>();
        transformProjection(finalProjection);
        
        LayoutSimilarity ls = new LayoutSimilarity();
        double layoutSimilarity = ls.execute(initialProjection, finalProjection);
        JOptionPane.showMessageDialog(this, "Similaridade no layout: "+String.valueOf(layoutSimilarity));
        
    }//GEN-LAST:event_layoutSimilarityMenuItemActionPerformed

    private void sizeIncreaseMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sizeIncreaseMenuItemActionPerformed
        finalProjection = new ArrayList<>();
        transformProjection(finalProjection);
                
        SizeIncrease si = new SizeIncrease();
        double sizeIncrease = si.execute(initialProjection, finalProjection);
        JOptionPane.showMessageDialog(this, "Incremento de tamanho: "+sizeIncrease);
    }//GEN-LAST:event_sizeIncreaseMenuItemActionPerformed

    private void euclideanDistanceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_euclideanDistanceMenuItemActionPerformed
        finalProjection = new ArrayList<>();
        transformProjection(finalProjection);
        
        EuclideanDistance ed = new EuclideanDistance();
        double euclideanDistance = ed.execute(initialProjection, finalProjection);
        JOptionPane.showMessageDialog(this, "Dist�ncia Euclideana: "+euclideanDistance);
    }//GEN-LAST:event_euclideanDistanceMenuItemActionPerformed

    private void silhouetteCoefficientMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_silhouetteCoefficientMenuItemActionPerformed
        Viewer gv = (Viewer) desktop.getSelectedFrame();
        if( gv != null  ) {
            try {
                SilhouetteCoefficientView.getInstance(this).display(gv.getGraph(), gv.getCurrentScalar());
                gv.updateScalars(null);
            } catch( IOException ex ) {
                Logger.getLogger(ProjectionExplorerView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_silhouetteCoefficientMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        String neighbors = JOptionPane.showInputDialog("N�mero de vizinhos");
        
        NeighborhoodHit.getInstance(null).neighborhoodHit(((Viewer)desktop.getSelectedFrame()).getGraph(), 
                Integer.parseInt(neighbors));

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        
        String arquivo = "C:\\Users\\wilson\\Desktop\\Faculdade\\FAPESP\\dados\\Iris.data";
        int neigh = 10;
        NeighborhoodPreservationView.getInstance(this).neighborhoodPreservationGroup(
                ((Viewer)desktop.getSelectedFrame()).getGraph(),
                arquivo, neigh);        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Viewer gv = (Viewer) desktop.getSelectedFrame();
        if( gv != null  ) {
//            try {
//               /* Graph graph = gv.getGraph();
//                
//                Matrix matrix = Util.exportSelectedProjection(graph.getVertex(), gv.getCurrentScalar());
//                
//                float[] silhouette = SilhouetteCoefficientView.getInstance(this).getSilhouetteCoefficients(graph, matrix);
//                
//                for( float f: silhouette )
//                    System.out.println(f);
//                System.out.println("--------------------");
//                
//                DissimilarityType type = DissimilarityType.EUCLIDEAN;
//                SilhouetteCoefficientClusterData sccd = 
//                        new SilhouetteCoefficientClusterData(DissimilarityFactory.getInstance(type),
//                                                             gv.getCurrentScalar() == null ? 
//                                                             graph.getScalarByName(PExConstants.DOTS) :
//                                                             gv.getCurrentScalar());
//                float[] s2 = sccd.generate(graph.getVertex().stream().filter((Vertex v)->v.isSelected())
//                        .collect(Collectors.toCollection(ArrayList::new)));
//                
//                for( float f: s2 )
//                    System.out.println(f);
//                
//                
//                List<BoxplotDataGenerator> bdg = new ArrayList<>();
//                bdg.add(new SilhouetteCoefficientClusterData(DissimilarityFactory.getInstance(type),
//                                                             gv.getCurrentScalar() == null ? 
//                                                             graph.getScalarByName(PExConstants.DOTS) :
//                                                             gv.getCurrentScalar()));
//                
//                Image img = new BoxplotRepresentative(bdg).getRepresentative(
//                                                             
//                                                             
//                                                           graph.getVertex().stream().filter((Vertex v)->v.isSelected())
//                        .collect(Collectors.toCollection(ArrayList::new))  
//                                                             
//                                                             );
//                
//                
//                
//                JFrame frame = new JFrame("FRAME OLA");
//                JPanel panel =  new JPanel();
//                JLabel label = new JLabel("MINHA LABEL");
//                ImageIcon icon = new ImageIcon(img); 
//                label.setIcon(icon);
//                panel.add(label);
//                frame.setContentPane(panel);
//                frame.setSize(400, 400);
//                frame.setVisible(true);*/
//                
//            } catch( IOException ex ) {
//                Logger.getLogger(ProjectionExplorerView.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        Viewer gv = (Viewer) desktop.getSelectedFrame();
        if( gv != null  ) {
            try {
                final int NNEIGHBORS = 8;
                String arquivo = "E:\\gaborBrodatzBoas_NotNormalized.data";
                Graph graph = gv.getGraph();
                List<Vertex> selectedVertex = graph.getVertex().stream().filter((Vertex v)->v.isSelected())
                        .collect(Collectors.toCollection(ArrayList::new));

                DissimilarityType type = DissimilarityType.EUCLIDEAN;
                Dissimilarity diss = DissimilarityFactory.getInstance(type);
                List<BoxplotDataGenerator> bdg = new ArrayList<>();
                
                ArrayList<Float> clusters = SilhouetteCoefficient.getClusters(Util.exportSelectedProjection(selectedVertex, 
                                                                gv.getCurrentScalar() == null ? 
                                                                 graph.getScalarByName(PExConstants.DOTS) :
                                                                gv.getCurrentScalar()).getClassData());
                if( clusters.size() > 1 ) {
                
                    bdg.add(new SilhouetteCoefficientClusterData(diss)); 
                }
                bdg.add(new NeighborhoodHitClusterData(NNEIGHBORS));


                ArrayList<String> ids = new ArrayList<>();
                graph.getVertex().stream().filter((Vertex v)->v.isSelected()).forEach((Vertex v)->ids.add(v.getUrl()));

                Matrix matrix = MatrixFactory.getInstance(arquivo, ids);     
                DistanceMatrix dmat = new DistanceMatrix(matrix, diss);
                Matrix matrixProjection = MatrixFactory.getInstance(arquivo);
                DistanceMatrix dmatProjection = new DistanceMatrix(matrixProjection, diss);
                bdg.add(new NeighborhoodPreservationClusterData(NNEIGHBORS, dmat, dmatProjection));
                
                String algorithm = "KRUSKAL";
                Stress stress = null;
                if( algorithm.equals("KRUSKAL") ) {
                    stress = new KruskalStress();
                } else {
                    stress = new SammonStress();
                }
                
                bdg.add(new StressClusterData(dmatProjection, dmat, stress));
                
                BoxplotRepresentative boxplot = new BoxplotRepresentative(bdg, AnalysisType.BOTH);

                
                
                Matrix selected = Util.exportSelectedProjection(selectedVertex, 
                                                                gv.getCurrentScalar() == null ? 
                                                                 graph.getScalarByName(PExConstants.DOTS) :
                                                                gv.getCurrentScalar());
                Matrix projection = Util.exportProjection(graph, gv.getCurrentScalar());
                
                //JPanel smallMultiples  = boxplot.getSmallMultiples(selectedVertex);
                Image img = boxplot.getRepresentative(selected, projection);

                JFrame frame = new JFrame("FRAME OLA");
                JPanel panel =  new JPanel();
                JLabel label = new JLabel();
                GroupRepresentative gr = new GroupRepresentative(100, 100, img);
                gr.setBordered(true);
                graph.addRepresentative(gr);
                gv.updateImage();
                
                ImageIcon icon = new ImageIcon(img); 
                label.setIcon(icon);
                panel.add(label);
                frame.setContentPane(panel);
                frame.setSize(380, 350);
                frame.setVisible(false);
            } catch( IOException e ) {
                System.err.println(e);
            }
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    void updateButtons() {
        Viewer gv = (Viewer) this.desktop.getSelectedFrame();
        if (gv != null && gv.getGraph().isCorpus()) {
            corpusButtonsEnabled(true);
            imagesButtonsEnabled(false);
        } else {
            corpusButtonsEnabled(false);
            imagesButtonsEnabled(true);
        }
    }

    public void corpusButtonsEnabled(boolean Enabled) {
        this.createLabelToggleButton.setEnabled(Enabled);
        this.showAllLabelsToggleButton.setEnabled(Enabled);
        this.highlightLabelToggleButton.setEnabled(Enabled);
        this.createLabelsButton.setEnabled(Enabled);
        this.createFilterLabelToggleButton.setEnabled(Enabled);
        this.rulesMenu.setEnabled(Enabled);
        this.corpusMenu.setEnabled(Enabled);
        //this.distCoordinationMenuItem.setEnabled(Enabled);
        this.topicCoordinationMenuItem.setEnabled(Enabled);
        this.searchToolbarTextField.setEnabled(Enabled);
        this.goToolbarButton.setEnabled(Enabled);
    }

    public void imagesButtonsEnabled(boolean Enabled) {
        //TODO
    }

    public void refreshLists() {
        this.nearestNeighborsList.repaint();
        this.pointsList.repaint();
    }

    public void recreatingLists(ArrayList<Vertex> vertex) {
        this.nearestNeighborListModel.clear();
        this.markedPointField.setText("");

        this.fileTitleTextField.setText("");
        this.fileContentEditorPane.setText("");

        this.pointsListModel.clear();

        for (Vertex v : vertex) {
            if (v.isValid()) {
                pointsListModel.addElement(v);
            }
        }
    }

    public void setMarkedPointText(Corpus corpus, Vertex v) {
        markedPointField.setText(v.toString());
        markedPointField.setCaretPosition(0);
        this.showContent(corpus, v);
    }

    public void showContent(Corpus corpus, Vertex v) {
        if (corpus != null) {
            String filename = v.getUrl();

            if (filename.endsWith(".html") || filename.endsWith(".htm")) {
                this.fileContentEditorPane.setContentType("text/html");
            }

            this.fileTitleTextField.setText(v.toString());
            this.fileTitleTextField.setCaretPosition(0);

            try {
                this.fileContentEditorPane.setText(corpus.getViewContent(filename));
                this.fileContentEditorPane.setCaretPosition(0);
            } catch (IOException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setNearestNeighborsPoints(ArrayList<Vertex> vertex) {
        nearestNeighborListModel.clear();

        for (Vertex v : vertex) {
            if (v.isValid()) {
                nearestNeighborListModel.addElement(v);
            }
        }
    }

    public void addGraph2D(Graph graph, boolean graphChanged, String title) {
        ProjectionViewer gv = new ProjectionViewer(this);
        gv.setGraph(graph);
        gv.setTitle(title);

        this.desktop.add(gv);
        gv.setSize(this.desktop.getSize());
        gv.setVisible(true);

        //adding the coordinations
        this.coordinator.addCoordination(gv);
        gv.updateCoordinations(Mapping.OFF);

        Scalar scalar = graph.getScalarByName(PExConstants.CDATA);
        gv.updateScalars(scalar);

        Connectivity con = graph.getConnectivityByName(PExConstants.MST);
        if (con == null) {
            con = graph.getConnectivityByName(PExConstants.NJ);
        }
        gv.updateConnectivities(con);

        gv.setGraphChanged(graphChanged);
    }

    public void setFocusedJInternalFrame(JInternalFrame frame) {
        if (frame != null) {
            for (JComponent c : this.windows.keySet()) {
                if (c instanceof Viewer) {
                    Viewer gv = (Viewer) c;
                    this.windows.get(gv).setText(gv.getTitle());
                } else if (c instanceof JInternalFrame.JDesktopIcon) {
                    JInternalFrame.JDesktopIcon icon = (JInternalFrame.JDesktopIcon) c;
                    Viewer gv = (Viewer) icon.getInternalFrame();
                    this.windows.get(gv).setText(gv.getTitle());
                }
            }

            for (int i = 0; i < this.menuWindows.getItemCount(); i++) {
                JMenuItem item = this.menuWindows.getItem(i);

                if (item != null && item.getText().equals(frame.getTitle())) {
                    item.setSelected(true);

                    if (frame instanceof Viewer) {
                        this.recreatingLists(((Viewer) frame).getGraph().getVertex());
                        this.refreshLists();
                        this.updateButtons();
                    }

                    break;
                }
            }
        } else {
            this.nearestNeighborListModel.clear();
            this.markedPointField.setText("");

            this.fileTitleTextField.setText("");
            this.fileContentEditorPane.setText("");

            this.pointsListModel.clear();
        }
    }

    public JDesktopPane getDesktop() {
        return this.desktop;
    }

    public Coordinator getCoordinator() {
        return this.coordinator;
    }
    private Coordinator coordinator = new Coordinator();
    private HashMap<JComponent, JRadioButtonMenuItem> windows = new HashMap<JComponent, JRadioButtonMenuItem>();
    private DefaultListModel nearestNeighborListModel = new DefaultListModel();
    private DefaultListModel pointsListModel = new DefaultListModel();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem FeatureSelection;
    private javax.swing.JMenuItem NeuralNetworkClassifier;
    private javax.swing.JButton ReloadButton;
    private javax.swing.JButton ReproduceButton;
    private javax.swing.JMenuItem RulesFromGridMenuItem;
    private javax.swing.JMenuItem about;
    private javax.swing.JMenuItem alignVerticalMenuItem;
    private javax.swing.JMenuItem alinkHC;
    private javax.swing.JPanel allPointsPanel;
    private javax.swing.JMenu calculateHCOptions;
    private javax.swing.JButton cleanLabelsButton;
    private javax.swing.JMenuItem clinkHC;
    private javax.swing.JMenu clusteringMenu;
    private javax.swing.JMenu connectivityMenu;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JMenu converters;
    private javax.swing.JToggleButton coordinatedSelectToggleButton;
    private javax.swing.JMenu coordinationMenu;
    private javax.swing.JMenu corpusMenu;
    private javax.swing.JMenuItem createCorporaPDFOptions;
    private javax.swing.JMenuItem createDistanceHistogramMenuItem;
    private javax.swing.JToggleButton createFilterLabelToggleButton;
    private javax.swing.JToggleButton createLabelToggleButton;
    private javax.swing.JButton createLabelsButton;
    private javax.swing.JMenuItem createScalarFromConnectionMenuItem;
    private javax.swing.JButton createScalarFromDocButton;
    private javax.swing.JToggleButton cutToggleButton;
    private javax.swing.JMenu dataAnalysisMenu;
    private javax.swing.JMenu dataMiningMenu;
    private javax.swing.JPanel dataSearchPanel;
    private javax.swing.JMenuItem delaunayOptions;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JMenuItem directoryImportProjection;
    private javax.swing.JMenuItem distCoordinationMenuItem;
    private javax.swing.JMenuItem editClean;
    private javax.swing.JMenuItem editDelete;
    private javax.swing.JMenuItem euclideanDistanceMenuItem;
    private javax.swing.JMenuItem exportConnectivity;
    private javax.swing.JMenuItem exportCorporaOptions;
    private javax.swing.JMenu exportMenu;
    private javax.swing.JMenuItem exportRuleTermMenuItem;
    private javax.swing.JMenuItem exportScalarsOption;
    private javax.swing.JMenuItem exporttitles;
    private javax.swing.JEditorPane fileContentEditorPane;
    private javax.swing.JScrollPane fileContentScrollPane;
    private javax.swing.JMenuItem fileExportToEps;
    private javax.swing.JMenuItem fileExportToPng;
    private javax.swing.JMenuItem fileExportToProjection;
    private javax.swing.JMenuItem fileExportToVtk;
    private javax.swing.JMenuItem fileImportFromProjection;
    private javax.swing.JMenuItem fileNew;
    private javax.swing.JMenuItem fileOpen;
    private javax.swing.JMenuItem fileSave;
    private javax.swing.JTextField fileTitleTextField;
    private javax.swing.JToggleButton findToggleButton;
    private javax.swing.JButton goButton;
    private javax.swing.JButton goToolbarButton;
    private javax.swing.JMenuItem googleSearchsMenuItem;
    private javax.swing.JToolBar graphToolBar;
    private javax.swing.JToggleButton highlightLabelToggleButton;
    private javax.swing.JMenuItem identityCoordinationMenuItem;
    private javax.swing.JMenuItem importConnectivity;
    private javax.swing.JMenu importMenu;
    private javax.swing.JMenuItem importScalarsOption;
    private javax.swing.JMenuItem importtitles;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuItem joinScalarsOptions;
    private javax.swing.JMenuItem knnOptions;
    private javax.swing.JMenuItem layoutSimilarityMenuItem;
    private javax.swing.JMenuItem loadCorporaOptions;
    private javax.swing.JTextField markedPointField;
    private javax.swing.JPanel markedPointFields;
    private javax.swing.JPanel markedPointPanel;
    private javax.swing.JMenuItem matrixFileConverter_jMenuItem;
    private javax.swing.JMenuItem memoryCheckMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenu menuTool;
    private javax.swing.JMenu menuWindows;
    private javax.swing.JToggleButton movePointToggleButton;
    private javax.swing.JMenuItem movePoints_jMenuItem;
    private javax.swing.JMenu movements_jMenu;
    private javax.swing.JMenuItem mstOptions;
    private javax.swing.JMenuItem multiModalVolumeToPoints_jMenuItem;
    private javax.swing.JMenuItem multidimensionalMenuItem;
    private javax.swing.JScrollPane nearestNeighborScrollPanel;
    private javax.swing.JList nearestNeighborsList;
    private javax.swing.JMenuItem neighborHitMenuItem;
    private javax.swing.JMenuItem neighborPreservationMenuItem;
    private javax.swing.JButton newButton;
    private javax.swing.JButton openButton;
    private javax.swing.JTabbedPane optionTabbedPane;
    private javax.swing.JMenuItem pointsFileConverter_jMenuItem;
    private javax.swing.JList pointsList;
    private javax.swing.JPanel pointsPanel;
    private javax.swing.JMenuItem pointsToMatlab_jMenuItem;
    private javax.swing.JButton removeOverlapJButton;
    private javax.swing.JMenu rulesMenu;
    private javax.swing.JButton runForceButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem scalarConnectivityOptions;
    private javax.swing.JMenu scalarMenu;
    private javax.swing.JScrollPane scrollPanePoints;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JLabel searchToolbarLabel;
    private javax.swing.JTextField searchToolbarTextField;
    private javax.swing.JToggleButton selectToggleButton;
    private javax.swing.ButtonGroup selectionButtonGroup;
    private javax.swing.JSeparator separator1;
    private javax.swing.JSeparator separator2;
    private javax.swing.JSeparator separator3;
    private javax.swing.JSeparator separator4;
    private javax.swing.JLabel separatorLabel;
    private javax.swing.JLabel separatorLabel1;
    private javax.swing.JLabel separatorLabel2;
    private javax.swing.JLabel separatorLabel3;
    private javax.swing.JLabel separatorLabel4;
    private javax.swing.JLabel separatorLabel5;
    private javax.swing.JLabel separatorLabel6;
    private javax.swing.JSeparator separatorOptions1;
    private javax.swing.JSeparator separatorOptions2;
    private javax.swing.JToggleButton showAllLabelsToggleButton;
    private javax.swing.JToggleButton showVertexLabelToggleButton;
    private javax.swing.JMenuItem silhouetteCoefficientMenuItem;
    private javax.swing.JMenuItem sizeIncreaseMenuItem;
    private javax.swing.JMenuItem slinkHC;
    private javax.swing.JMenuItem stopwordsOption;
    private javax.swing.JMenuItem stressMenuItem;
    private javax.swing.JMenu titlesOption;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JButton toolButton;
    private javax.swing.JMenuItem toolOptions;
    private javax.swing.JMenuItem topicCoordinationMenuItem;
    private javax.swing.JToggleButton viewContentToggleButton;
    private javax.swing.JMenuItem viewSimilarityMatrixMenuItem;
    private javax.swing.JMenuItem weightedFeatures_jMenuItem;
    private javax.swing.JSplitPane windowSplitPane;
    private javax.swing.ButtonGroup windowsButtonGroup;
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton zoomOutButton;
    // End of variables declaration//GEN-END:variables
}
