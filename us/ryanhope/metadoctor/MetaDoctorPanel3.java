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

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class MetaDoctorPanel3 extends JPanel {

	private static final long serialVersionUID = 1L;

	private ButtonGroup connectorGroup;
	private JRadioButton newJarRadioButton;
	private JRadioButton existingJarRadioButton;
	private JPanel jPanel1;
	private JLabel welcomeTitle;

	private JPanel contentPanel;
	private JLabel iconLabel;
	private JSeparator separator;
	private JLabel textLabel;
	private JPanel titlePanel;

	private JLabel jarLabel;

	final DefaultListModel deviceModel = new DefaultListModel();
	final DefaultListModel carrierModel = new DefaultListModel();
	final DefaultListModel versionModel = new DefaultListModel();

	final JFileChooser fc = new JFileChooser();

	MetaDoctorPanel3Descriptor desc;

	public MetaDoctorPanel3(MetaDoctorPanel3Descriptor desc) {

		super();

		this.desc = desc;

		jarLabel = new JLabel();
		jarLabel.setFont(jarLabel.getFont().deriveFont(Font.PLAIN, 10));

		contentPanel = getContentPanel();
		contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		ImageIcon icon = getImageIcon();

		titlePanel = new JPanel();
		textLabel = new JLabel();
		iconLabel = new JLabel();
		separator = new JSeparator();

		setLayout(new java.awt.BorderLayout());

		titlePanel.setLayout(new java.awt.BorderLayout());
		titlePanel.setBackground(Color.gray);

		textLabel.setBackground(Color.gray);
		textLabel.setForeground(Color.white);
		textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 14));
		textLabel.setText("Configure");
		textLabel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		textLabel.setOpaque(true);

		iconLabel.setBackground(Color.gray);
		if (icon != null)
			iconLabel.setIcon(icon);

		titlePanel.add(textLabel, BorderLayout.CENTER);
		titlePanel.add(iconLabel, BorderLayout.EAST);
		titlePanel.add(separator, BorderLayout.SOUTH);

		add(titlePanel, BorderLayout.NORTH);
		JPanel secondaryPanel = new JPanel();
		secondaryPanel.add(contentPanel, BorderLayout.NORTH);
		add(secondaryPanel, BorderLayout.WEST);

	}  

	public void addActionListeners(ActionListener l) {
		newJarRadioButton.addActionListener(l);
		existingJarRadioButton.addActionListener(l);
	}

	public String getRadioButtonSelected() {
		return connectorGroup.getSelection().getActionCommand();
	}

	private JPanel getContentPanel() {     

		JPanel contentPanel1 = new JPanel();

		connectorGroup = new ButtonGroup();
		welcomeTitle = new JLabel();
		JPanel jPanel01 = new JPanel(new BorderLayout());
		jPanel1 = new JPanel();
		newJarRadioButton = new JRadioButton();
		existingJarRadioButton = new JRadioButton();

		newJarRadioButton.setActionCommand("newJar");
		existingJarRadioButton.setActionCommand("existingJar");

		newJarRadioButton.setSelected(true);	

		contentPanel1.setLayout(new BorderLayout());

		welcomeTitle.setText("Please configure some basic options:");
		contentPanel1.add(welcomeTitle, java.awt.BorderLayout.NORTH);

		GridBagLayout gbl = new GridBagLayout();
		jPanel1.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;

		jPanel01.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
		
		JPanel activationOptions = new JPanel();
		activationOptions.setLayout(new BoxLayout(activationOptions, BoxLayout.PAGE_AXIS));
		TitledBorder activationBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Activation Options");
		activationOptions.setBorder(activationBorder);
		JCheckBox bypassActivationCheckBox = new JCheckBox("Bypass Activation");
		JCheckBox enablewifiCheckBox = new JCheckBox("Enable Wifi on First Use");
		JCheckBox showfirstuseCheckBox = new JCheckBox("Make First Use Visible");
		activationOptions.add(bypassActivationCheckBox);
		activationOptions.add(enablewifiCheckBox);
		activationOptions.add(showfirstuseCheckBox);
		c.gridx = 0;
		c.gridy = 0;
		jPanel1.add(activationOptions, c);
		
		JPanel devOptions = new JPanel();
		devOptions.setLayout(new BoxLayout(devOptions, BoxLayout.PAGE_AXIS));
		TitledBorder devOptsBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Developer Options");
		devOptions.setBorder(devOptsBorder);
		JCheckBox devmodeCheckBox = new JCheckBox("Enable Developer Mode");
		JCheckBox usbnetCheckBox = new JCheckBox("Enable USB Net");
		devOptions.add(devmodeCheckBox);
		devOptions.add(usbnetCheckBox);
		c.gridx = 0;
		c.gridy = 1;
		jPanel1.add(devOptions, c);

		JPanel preinstalledApps = new JPanel();
		preinstalledApps.setPreferredSize(new Dimension(180,0));
		preinstalledApps.setLayout(new BoxLayout(preinstalledApps, BoxLayout.PAGE_AXIS));
		TitledBorder preInstBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Preinstalled Applications");
		preinstalledApps.setBorder(preInstBorder);
		JCheckBox dropbearCheckBox = new JCheckBox("Dropbear");
		JCheckBox opensshCheckBox = new JCheckBox("OpenSSH");
		JCheckBox prewareCheckBox = new JCheckBox("Preware");
		preinstalledApps.add(dropbearCheckBox);
		preinstalledApps.add(opensshCheckBox);
		preinstalledApps.add(prewareCheckBox);
		c.gridx = 1;
		c.gridy = 0;
		jPanel1.add(preinstalledApps, c);
		
		JPanel sshKeys = new JPanel(new BorderLayout());
		//sshKeys.setLayout(new BoxLayout(sshKeys, BoxLayout.PAGE_AXIS));
		TitledBorder sshKeysBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Authorized SSH Keys");
		sshKeys.setBorder(sshKeysBorder);
		JList keys = new JList();
		//keys.setPreferredSize(new Dimension(200,100));
		sshKeys.add(keys);
		JPanel arPanel = new JPanel(new FlowLayout());
		JPanel ar = new JPanel(new GridLayout(5,1));
		JButton add = new JButton(MetaDoctor.EDIT_ADD_ICON);
		JButton remove = new JButton(MetaDoctor.EDIT_REMOVE_ICON);
		ar.add(Box.createHorizontalGlue());
		ar.add(add);
		ar.add(Box.createVerticalStrut(5));
		ar.add(remove);
		ar.add(Box.createHorizontalGlue());
		arPanel.add(Box.createVerticalStrut(2));
		arPanel.add(ar);
		sshKeys.add(arPanel, BorderLayout.EAST);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		jPanel1.add(sshKeys, c);

		jPanel01.add(jPanel1);
		contentPanel1.add(jPanel01, BorderLayout.CENTER);

		return contentPanel1;
	}

	private ImageIcon getImageIcon() {

		//  Icon to be placed in the upper right corner.

		return null;
	}	

}
