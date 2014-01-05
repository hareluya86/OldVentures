/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.oldventures;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.channels.FileLock;
import java.util.List;
import javax.swing.JFileChooser;

/**
 *
 * @author vincent.a.lee
 */
public class OldVenturesUI extends javax.swing.JFrame {

    OpenFileHandler openFileHandler;
    SequenceFormatHandler sequenceFormatHandler;
    
    /**
     * Creates new form OldVenturesUI
     */
    public OldVenturesUI() {
        initComponents();
        /**
         * Initialize helpers and other non-GUI components
         */
        openFileHandler = new OpenFileHandler();
        sequenceFormatHandler = new SequenceFormatHandler();
    }

    /**@Override
    public void dispose() {
        super.dispose();
        //openFileHandler.close();
    }
    */
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        enterFilenameLabel = new javax.swing.JLabel();
        filepathText = new javax.swing.JFormattedTextField();
        openFileButton = new javax.swing.JButton();
        numSeqLabel = new javax.swing.JLabel();
        numSeqSelector = new javax.swing.JComboBox();
        generateButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputTextArea = new javax.swing.JTextArea();
        convertToExcelButton = new javax.swing.JButton();
        convertToCSVButton = new javax.swing.JButton();
        convertToTextButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        statusBar = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        copyButton = new javax.swing.JButton();

        fileChooser.setApproveButtonText("");
        fileChooser.setApproveButtonToolTipText("");
        fileChooser.setBackground(java.awt.Color.white);
        fileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\vincent.a.lee\\Desktop"));
        fileChooser.setDialogTitle("Open File");
        fileChooser.setToolTipText("");
        fileChooser.setDragEnabled(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SECA UTILITY");

        enterFilenameLabel.setText("Filename: ");

        filepathText.setEditable(false);
        filepathText.setDisabledTextColor(new java.awt.Color(153, 153, 153));

        openFileButton.setText("Open File");
        openFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileButtonActionPerformed(evt);
            }
        });

        numSeqLabel.setText("Select Num of Sequence: ");

        numSeqSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" }));
        numSeqSelector.setSelectedIndex(5);
        numSeqSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numSeqSelectorActionPerformed(evt);
            }
        });

        generateButton.setText("Generate Sequence");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        outputTextArea.setColumns(20);
        outputTextArea.setRows(5);
        jScrollPane1.setViewportView(outputTextArea);

        convertToExcelButton.setText("Excel");
        convertToExcelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                convertToExcelButtonActionPerformed(evt);
            }
        });

        convertToCSVButton.setText("CSV");
        convertToCSVButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                convertToCSVButtonActionPerformed(evt);
            }
        });

        convertToTextButton.setText("Text");
        convertToTextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                convertToTextButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Format:");

        statusBar.setText("Ready!");

        copyButton.setText("Copy");
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(enterFilenameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(filepathText))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(openFileButton)
                                    .addComponent(generateButton)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(numSeqLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(numSeqSelector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(convertToExcelButton)
                                .addGap(2, 2, 2)
                                .addComponent(convertToCSVButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(convertToTextButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                                .addComponent(copyButton)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(filepathText)
                    .addComponent(enterFilenameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openFileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numSeqLabel)
                    .addComponent(numSeqSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(generateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(convertToExcelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(convertToCSVButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(convertToTextButton)
                        .addComponent(copyButton))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusBar))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileButtonActionPerformed
        // TODO add your handling code here:
        
        fileChooser.setApproveButtonText("Select");
        File selectedFile;
        String absolutePath;
        int returnVal = fileChooser.showOpenDialog(this);

        switch(returnVal){
            case JFileChooser.APPROVE_OPTION:   statusBar.setText("Loading file...");
                                                selectedFile = fileChooser.getSelectedFile();
                                                absolutePath = selectedFile.getAbsolutePath();
                                                filepathText.setValue(absolutePath);
                                                String filepath = filepathText.getText();
                                                openFileHandler.close();
                                                String outcome = openFileHandler.getFileLock(filepath);
                                                this.statusBar.setText(outcome);
                                                break;
            case JFileChooser.CANCEL_OPTION :   break;
            case JFileChooser.ERROR_OPTION  :   statusBar.setText("Error encountered while selecting file!");
                                                break;
            default                         :   break;
        }
        
    }//GEN-LAST:event_openFileButtonActionPerformed

    private void numSeqSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numSeqSelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numSeqSelectorActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        // TODO add your handling code here:
        generateButton.setText("Generating...");
        generateButton.setEnabled(false);
        String numOfSequenceString = this.numSeqSelector.getSelectedItem().toString();
        int numOfSequence = Integer.parseInt(numOfSequenceString);
        String result = openFileHandler.generateSequenceRAF(numOfSequence);
        statusBar.setText(result);
        
        String rawSequence = openFileHandler.getRawSequences();
        List<List<String>> baseFormatSequence = sequenceFormatHandler.linesFromText(rawSequence);
        String processedSequence = sequenceFormatHandler.changeSequencesFormat(baseFormatSequence, SequenceFormatHandler.FORMAT.TEXT);
        
        outputTextArea.setText(processedSequence);
        generateButton.setText("Generate Sequence");
        generateButton.setEnabled(true);
    }//GEN-LAST:event_generateButtonActionPerformed

    private void convertToExcelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertToExcelButtonActionPerformed
        // TODO add your handling code here:
        List<List<String>> output = sequenceFormatHandler.linesFromText(openFileHandler.getRawSequences());
        String result = sequenceFormatHandler.changeSequencesFormat(output, SequenceFormatHandler.FORMAT.EXCEL);
        this.outputTextArea.setText(result);
    }//GEN-LAST:event_convertToExcelButtonActionPerformed

    private void convertToCSVButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertToCSVButtonActionPerformed
        // TODO add your handling code here:
        List<List<String>> output = sequenceFormatHandler.linesFromText(openFileHandler.getRawSequences());
        String result = sequenceFormatHandler.changeSequencesFormat(output, SequenceFormatHandler.FORMAT.CSV);
        this.outputTextArea.setText(result);
    }//GEN-LAST:event_convertToCSVButtonActionPerformed

    private void convertToTextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertToTextButtonActionPerformed
        // TODO add your handling code here:
        List<List<String>> output = sequenceFormatHandler.linesFromText(openFileHandler.getRawSequences());
        String result = sequenceFormatHandler.changeSequencesFormat(output, SequenceFormatHandler.FORMAT.TEXT);
        this.outputTextArea.setText(result);
    }//GEN-LAST:event_convertToTextButtonActionPerformed

    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
        // TODO add your handling code here:
        outputTextArea.selectAll();
        StringSelection stringSelection = new StringSelection (outputTextArea.getSelectedText());
        Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
        clpbrd.setContents (stringSelection, null);
        statusBar.setText("Output is copied to clipboard!");
    }//GEN-LAST:event_copyButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OldVenturesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OldVenturesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OldVenturesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OldVenturesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OldVenturesUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton convertToCSVButton;
    private javax.swing.JButton convertToExcelButton;
    private javax.swing.JButton convertToTextButton;
    private javax.swing.JButton copyButton;
    private javax.swing.JLabel enterFilenameLabel;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JFormattedTextField filepathText;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel numSeqLabel;
    private javax.swing.JComboBox numSeqSelector;
    private javax.swing.JButton openFileButton;
    private javax.swing.JTextArea outputTextArea;
    private javax.swing.JLabel statusBar;
    // End of variables declaration//GEN-END:variables
}
