/*
 * SplashWindow.java
 *
 * Created on December 12, 2005, 1:50 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import wavemark.*;

/**
 *
 * @author Chopin
 */
public class SplashWindow extends javax.swing.JWindow {
    
    /** Creates a new instance of SplashWindow */
    public SplashWindow(Thread mainThread) {
        super();
        this.mainthread = mainThread;
        initComponents();
    }
    
    private void initComponents() {
//-------------------------------------------------
        jPanelMain = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        getContentPane().setLayout(new java.awt.BorderLayout());
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                splashWindowMouseClicked(evt);
            }
        });
        
        jPanelMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jProgressBar1.setForeground(new java.awt.Color(255, 153, 51));
        jProgressBar1.setBackground(new java.awt.Color(0, 0, 0));
        jProgressBar1.setBorderPainted(false);
        jPanelMain.add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 339, 595, 5));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wavemark/images/splash.jpg")));
        jPanelMain.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 591, -1));
        
        add(jPanelMain, java.awt.BorderLayout.CENTER);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-591)/2, (screenSize.height-381)/2, 591, 381);  
    }
    
    private void splashWindowMouseClicked(java.awt.event.MouseEvent evt) {
        closeWindow();
    }
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {

        runner = new Thread(new Runnable() {
            public void run() {
                while(true){
                    try{
                        jProgressBar1.setValue(jProgressBar1.getValue() + 2);
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    
                    if(jProgressBar1.getValue() >= 100){
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    }
                }
 
                setVisible(false);
                mainthread.start();
                closeWindow();
            }
        });
        
        runner.start();
    }
    
    private void invokeMainForm(){
        mainthread.start();
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    private void closeWindow() {
        this.dispose();
    }
    
    public static Thread invokeMain(final String className, final String[] args) {
        Thread mainRunner = new Thread(new Runnable() {
            public void run(){
                try {
                    Class.forName(className).getMethod("main", new Class[] {String[].class}).invoke(null, new Object[] {args});
                } catch (Exception e) {
                    InternalError error = new InternalError("Failed invoke main form");
                    error.initCause(e);
                    throw error;
                }
            }
        }); 
        
        return mainRunner;
    }
    
    private Thread runner;
    private Thread mainthread;
    private JPanel jPanelMain;
    private JProgressBar jProgressBar1;
    private JLabel jLabel1;
   
}
