/*
 * StartApplication.java
 *
 * Created on December 12, 2005, 2:05 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package wavemark;

import ui.SplashWindow;

/**
 *
 * @author Chopin
 */
public class StartApplication {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread mainRunner = SplashWindow.invokeMain("ui.MainForm", args);
        SplashWindow splash = new SplashWindow(mainRunner);
        splash.setVisible(true);
    }
    
}
