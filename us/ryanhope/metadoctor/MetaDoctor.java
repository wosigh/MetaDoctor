/*=============================================================================
 Copyright (C) 2009-2010 WebOS Internals <http://www.webos-internals.org/>
 Copyright (C) 2009-2010 Ryan Hope <rmh3093@gmail.com>

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 =============================================================================*/

package us.ryanhope.metadoctor;

import javax.swing.JDialog;
import us.ryanhope.wizard.*;

public class MetaDoctor {
    
    public static void main(String[] args) {
        
        Wizard wizard = new Wizard();
        
        JDialog dialog = wizard.getDialog();
        //dialog.setSize(640,480);        
        dialog.setTitle("Webos Internals MetaDoctor");
        
        WizardPanelDescriptor descriptor1 = new MetaDoctorPanel1Descriptor();
        wizard.registerWizardPanel(MetaDoctorPanel1Descriptor.IDENTIFIER, descriptor1);
        

        WizardPanelDescriptor descriptor2 = new MetaDoctorPanel2Descriptor();
        wizard.registerWizardPanel(MetaDoctorPanel2Descriptor.IDENTIFIER, descriptor2);

        WizardPanelDescriptor descriptor3 = new TestPanel3Descriptor();
        wizard.registerWizardPanel(TestPanel3Descriptor.IDENTIFIER, descriptor3);
        
        wizard.setCurrentPanel(MetaDoctorPanel1Descriptor.IDENTIFIER);
        
        int ret = wizard.showModalDialog();
        
        System.out.println("Dialog return code is (0=Finish,1=Cancel,2=Error): " + ret);
        System.out.println("Second panel selection is: " + 
            (((MetaDoctorPanel2)descriptor2.getPanelComponent()).getRadioButtonSelected()));
        
        System.exit(0);
        
    }
    
}
