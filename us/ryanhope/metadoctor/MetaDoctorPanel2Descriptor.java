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

import us.ryanhope.metadoctor.MetaDoctorPanel2.DCV;
import us.ryanhope.wizard.*;
import java.awt.event.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MetaDoctorPanel2Descriptor extends WizardPanelDescriptor implements ActionListener {

	public static final String IDENTIFIER = "CONNECTOR_CHOOSE_PANEL";

	MetaDoctorPanel2 panel2;

	public MetaDoctorPanel2Descriptor() {

		panel2 = new MetaDoctorPanel2(this);
		panel2.addActionListeners(this);
		for (final DCV dcv : DCV.values()) {
			panel2.addListSelectionListener(dcv, new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					panel2.updateLists(dcv, e);
				}
			});
		}

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
		panel2.enabledisable();
		panel2.updateLists(DCV.NONE, null);
	}
	
	public void actionPerformed(ActionEvent e) {
		panel2.enabledisable();
	}	

}
