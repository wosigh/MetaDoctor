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

import us.ryanhope.wizard.*;
import java.awt.event.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MetaDoctorPanel3Descriptor extends WizardPanelDescriptor implements ActionListener {

	public static final String IDENTIFIER = "CONFIGURE";

	MetaDoctorPanel3 panel3;

	public MetaDoctorPanel3Descriptor() {

		panel3 = new MetaDoctorPanel3(this);
		panel3.addActionListeners(this);

		setPanelDescriptorIdentifier(IDENTIFIER);
		setPanelComponent(panel3);

	}

	public Object getNextPanelDescriptor() {
		return TestPanel4Descriptor.IDENTIFIER;
	}

	public Object getBackPanelDescriptor() {
		return MetaDoctorPanel2Descriptor.IDENTIFIER;
	}

	public void aboutToDisplayPanel() {
	}
	
	public void actionPerformed(ActionEvent e) {
	}	

}
