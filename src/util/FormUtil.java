/*
 * FormUtil.java
 *
 * Created on December 4, 2005, 12:05 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package util;

import java.awt.Component;
import javax.swing.JCheckBox;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import wavemark.*;

/**
 *
 * @author Administrator
 */
public class FormUtil {
    
    /** Creates a new instance of FormUtil */
    public FormUtil() {
    }
    
    private static void clearField(JTextField textField){
    	textField.setText("");
    }
        
       	private static void clearField(JTextArea textArea){
            textArea.setText("");
        }
        
        private static void clearField(JComboBox combobox){
            combobox.setSelectedIndex(-1);
        }
        
        private static void clearField(JPasswordField pwdField){
            pwdField.setText("");
        }
        
         private static void clearField(JRadioButton radiobutton){
            radiobutton.setSelected(false);
        }
         
          private static void clearField(JCheckBox checkbox){
            checkbox.setSelected(false);
        }

        
        public static void setEnable(JPanel parent, boolean enable, boolean clear){
            Component component[] = parent.getComponents();     
            for (int i=0;i<=component.length-1;i++){
                Component currentComponent = component[i];
                if (currentComponent.getClass().getName().equals("javax.swing.JTextField")){
                    currentComponent.setEnabled(enable);
                    if (clear)
                        clearField((JTextField) currentComponent);
                }
                else if (currentComponent.getClass().getName().equals("javax.swing.JButton")){
                    currentComponent.setEnabled(enable);
                }
                else if (currentComponent.getClass().getName().equals("javax.swing.JRadioButton")){
                    currentComponent.setEnabled(enable);
                    if (clear)
                        clearField((JRadioButton) currentComponent);
                }
                else if (currentComponent.getClass().getName().equals("javax.swing.JCheckBox")){
                    currentComponent.setEnabled(enable);
                    if (clear)
                        clearField((JCheckBox) currentComponent);
                }
                else if (currentComponent.getClass().getName().equals("javax.swing.JTextArea")){
                    currentComponent.setEnabled(enable);
                    if (clear)
                        clearField((JTextArea) currentComponent);
                }
                else if (currentComponent.getClass().getName().equals("javax.swing.JComboBox")){
                    currentComponent.setEnabled(enable);
                    if (clear)
                        clearField((JComboBox) currentComponent);
                }
                else if (currentComponent.getClass().getName().equals("javax.swing.JPasswordField")){
                    currentComponent.setEnabled(enable);
                    if (clear)
                        clearField((JPasswordField) currentComponent);
                }
                else if (currentComponent.getClass().getName().equals("javax.swing.JScrollPane")){
                    currentComponent.setEnabled(enable);
                    scrollPaneHandlerState((JScrollPane) currentComponent, enable, clear);
                }
            }
        }
        
        private static void scrollPaneHandlerState(JScrollPane scrollPane, boolean enable, boolean clear){
        	Component currentComponent = scrollPane.getViewport().getView();
        	
                if (currentComponent.getClass().getName().equals("javax.swing.JTextField")){
                    currentComponent.setEnabled(enable);
                    if (clear)
                        clearField((JTextField) currentComponent);
                }
                else if (currentComponent.getClass().getName().equals("javax.swing.JButton")){
                    currentComponent.setEnabled(enable);
                }
                else if (currentComponent.getClass().getName().equals("javax.swing.JTextArea")){
                    currentComponent.setEnabled(enable);
                    if (clear)
                        clearField((JTextArea) currentComponent);
                }
                else if (currentComponent.getClass().getName().equals("javax.swing.JComboBox")){
                    currentComponent.setEnabled(enable);
                    if (clear)
                        clearField((JComboBox) currentComponent);
                }
                else if (currentComponent.getClass().getName().equals("javax.swing.JPasswordField")){
                    currentComponent.setEnabled(enable);
                    if (clear)
                        clearField((JPasswordField) currentComponent);
                }
                
                
            }
        
       
    
}
