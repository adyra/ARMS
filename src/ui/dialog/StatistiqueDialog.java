/*
 * UtilDialog.java
 *
 * Created on April 5, 2007, 5:40 PM
 */

package ui.dialog;

import java.awt.Point;
import java.io.File;
import java.text.DecimalFormat;
import util.Util;
import util.WaveInfo;
import wavemark.*;

/**
 *
 * @author  ady
 */
public class StatistiqueDialog extends javax.swing.JDialog {
    private WaveInfo wave1;
    private WaveInfo wave2;
    
    /** Creates new form UtilDialog */
    public StatistiqueDialog(java.awt.Frame parent, boolean modal, Point pos,WaveInfo wave1, WaveInfo wave2) {
        super(parent, modal);
        this.wave1 = wave1;
        this.wave2 = wave2;
        initComponents();
        
        this.setLocation(pos.x + (800 - getWidth()),pos.y + (600 - getHeight()));

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanelUtil = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabelRMSE = new javax.swing.JLabel();
        jLabelSNR = new javax.swing.JLabel();
        jLStream1SRate = new javax.swing.JLabel();
        jLStream1BRate = new javax.swing.JLabel();
        jLStream1Length = new javax.swing.JLabel();
        jLStream1Channel = new javax.swing.JLabel();
        jLStream2SRate = new javax.swing.JLabel();
        jLStream2BRate = new javax.swing.JLabel();
        jLStream2Length = new javax.swing.JLabel();
        jLStream2Channel = new javax.swing.JLabel();
        jLabelStream1Name = new javax.swing.JLabel();
        jLabelStream2Name = new javax.swing.JLabel();
        jButtonCountStat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanelUtil.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelUtil.setBackground(new java.awt.Color(255, 255, 255));
        jLabel35.setFont(new java.awt.Font("Georgia", 1, 14));
        jLabel35.setText("Error Statistique");
        jPanelUtil.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wavemark/images/stat.png")));
        jPanelUtil.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, 40));

        jPanelUtil.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 370, 10));

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel39.setText("Stream 1");
        jPanel9.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel43.setText("Root Mean Square Error (RMSE)");
        jPanel9.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel41.setText("Stream 2");
        jPanel9.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jLabel44.setText(":");
        jPanel9.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 20, -1));

        jLabel45.setText(":");
        jPanel9.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, 10, -1));

        jLabel42.setText("- Sample Rate");
        jPanel9.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel46.setText("- Bit Rate");
        jPanel9.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel48.setText("- Length");
        jPanel9.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel49.setText("- Channels");
        jPanel9.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel50.setText(":");
        jPanel9.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 10, -1));

        jLabel51.setText(":");
        jPanel9.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 10, -1));

        jLabel52.setText(":");
        jPanel9.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 10, -1));

        jLabel53.setText(":");
        jPanel9.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 10, -1));

        jLabel54.setText("- Sample Rate");
        jPanel9.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel55.setText(":");
        jPanel9.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 10, -1));

        jLabel56.setText(":");
        jPanel9.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 10, -1));

        jLabel57.setText("- Bit Rate");
        jPanel9.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jLabel58.setText("- Length");
        jPanel9.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jLabel59.setText(":");
        jPanel9.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 10, -1));

        jLabel60.setText(":");
        jPanel9.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 10, -1));

        jLabel61.setText("- Channels");
        jPanel9.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel62.setText("Signal to Noise Ratio (SNR)");
        jPanel9.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 180, -1));

        jLabel63.setText(":");
        jPanel9.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 30, -1));

        jLabel64.setText(":");
        jPanel9.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 30, -1));

        jLabelRMSE.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabelRMSE.setText(" ");
        jPanel9.add(jLabelRMSE, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 130, -1));

        jLabelSNR.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabelSNR.setText(" ");
        jPanel9.add(jLabelSNR, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 130, -1));

        jLStream1SRate.setText(" ");
        jPanel9.add(jLStream1SRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 130, -1));

        jLStream1BRate.setText(" ");
        jPanel9.add(jLStream1BRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 130, -1));

        jLStream1Length.setText(" ");
        jPanel9.add(jLStream1Length, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 130, -1));

        jLStream1Channel.setText(" ");
        jPanel9.add(jLStream1Channel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 130, -1));

        jLStream2SRate.setText(" ");
        jPanel9.add(jLStream2SRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 130, -1));

        jLStream2BRate.setText(" ");
        jPanel9.add(jLStream2BRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 130, -1));

        jLStream2Length.setText(" ");
        jPanel9.add(jLStream2Length, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 130, -1));

        jLStream2Channel.setText(" ");
        jPanel9.add(jLStream2Channel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, 130, -1));

        jLabelStream1Name.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabelStream1Name.setText(" ");
        jPanel9.add(jLabelStream1Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 130, -1));

        jLabelStream2Name.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabelStream2Name.setText(" ");
        jPanel9.add(jLabelStream2Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, 130, -1));

        jPanelUtil.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 370, 290));

        jButtonCountStat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wavemark/images/misc.png")));
        jButtonCountStat.setText(" Count");
        jButtonCountStat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCountStatActionPerformed(evt);
            }
        });

        jPanelUtil.add(jButtonCountStat, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 360, -1, -1));

        getContentPane().add(jPanelUtil, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-402)/2, (screenSize.height-429)/2, 402, 429);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCountStatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCountStatActionPerformed
        WaveInfo stream1 = this.wave1;
        WaveInfo stream2 = this.wave2;
        
        DecimalFormat formater = new DecimalFormat("###.##");
        
        jLabelStream1Name.setText(stream1.getFileName());
        jLStream1SRate.setText(stream1.getFrameRate() + " Hz");
        jLStream1BRate.setText(stream1.getBitPerSample() + " bit/sample");
        String channel = stream1.getChannels() == 1? "Mono":"Stereo";
        jLStream1Channel.setText(channel);
        jLStream1Length.setText(formater.format(stream1.getLength()) + " second(s)");
        
        jLabelStream2Name.setText(stream2.getFileName());
        jLStream2SRate.setText(stream2.getFrameRate() + " Hz");
        jLStream2BRate.setText(stream2.getBitPerSample() + " bit/sample");
        String channel2 = stream2.getChannels() == 1? "Mono":"Stereo";
        jLStream2Channel.setText(channel2);
        jLStream2Length.setText(formater.format(stream2.getLength()) + " second(s)");
        
        
        int[] sample1 = stream1.getAudioSamples();
        int[] sample2 = stream2.getAudioSamples();
        
        double rmse = Util.countRMSE(sample1,sample2);
        double snr = Util.countSNR(sample1,sample2);
        jLabelRMSE.setText(formater.format(rmse));
        jLabelSNR.setText(formater.format(snr) + " db");
    }//GEN-LAST:event_jButtonCountStatActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCountStat;
    private javax.swing.JLabel jLStream1BRate;
    private javax.swing.JLabel jLStream1Channel;
    private javax.swing.JLabel jLStream1Length;
    private javax.swing.JLabel jLStream1SRate;
    private javax.swing.JLabel jLStream2BRate;
    private javax.swing.JLabel jLStream2Channel;
    private javax.swing.JLabel jLStream2Length;
    private javax.swing.JLabel jLStream2SRate;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabelRMSE;
    private javax.swing.JLabel jLabelSNR;
    private javax.swing.JLabel jLabelStream1Name;
    private javax.swing.JLabel jLabelStream2Name;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelUtil;
    private javax.swing.JSeparator jSeparator8;
    // End of variables declaration//GEN-END:variables
    
}
