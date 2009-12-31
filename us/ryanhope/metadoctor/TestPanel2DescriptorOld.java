package us.ryanhope.metadoctor;

import us.ryanhope.wizard.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TestPanel2DescriptorOld extends WizardPanelDescriptor implements ActionListener {
    
    public static final String IDENTIFIER = "CONNECTOR_CHOOSE_PANEL";
    
    TestPanel2Old panel2;
    
    public TestPanel2DescriptorOld() {
        
        panel2 = new TestPanel2Old();
        panel2.addCheckBoxActionListener(this);
        
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel2);
        
    }
    
    public Object getNextPanelDescriptor() {
        return TestPanel3Descriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
        return MetaDoctorPanel1Descriptor.IDENTIFIER;
    }
    
    
    public void aboutToDisplayPanel() {
        setNextButtonAccordingToCheckBox();
    }    

    public void actionPerformed(ActionEvent e) {
        setNextButtonAccordingToCheckBox();
    }
            
    
    private void setNextButtonAccordingToCheckBox() {
         if (panel2.isCheckBoxSelected())
            getWizard().setNextFinishButtonEnabled(true);
         else
            getWizard().setNextFinishButtonEnabled(false);           
    
    }
}
