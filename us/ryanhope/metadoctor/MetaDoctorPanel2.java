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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionListener;

public class MetaDoctorPanel2 extends JPanel {

	private static final long serialVersionUID = 1L;

	enum DCV {
		DEVICE,
		CARRIER,
		VERSION
	}

	private ButtonGroup connectorGroup;
	private JRadioButton newJarRadioButton;
	private JRadioButton existingJarRadioButton;
	private JPanel jPanel1;
	private JLabel welcomeTitle;

	private JPanel cdvPanel;
	private JScrollPane carrierPanel;
	private JList carrierList;
	private JScrollPane devicePanel;
	private JList deviceList;
	private JScrollPane versionPanel;
	private JList versionList;

	private JButton filePicker;

	private JPanel contentPanel;
	private JLabel iconLabel;
	private JSeparator separator;
	private JLabel textLabel;
	private JPanel titlePanel;

	final DefaultListModel deviceModel = new DefaultListModel();
	final DefaultListModel carrierModel = new DefaultListModel();
	final DefaultListModel versionModel = new DefaultListModel();

	private HashMap<String, HashMap<String, TreeSet<String>>> jarURLs;

	public MetaDoctorPanel2() {

		super();

		jarURLs = new HashMap<String, HashMap<String, TreeSet<String>>>();

		PropertyResourceBundle urls = (PropertyResourceBundle)
		ResourceBundle.getBundle("us.ryanhope.metadoctor.jar-urls");
		Enumeration<String> e = urls.getKeys();
		while (e.hasMoreElements()){
			String[] components = e.nextElement().split("-");
			HashMap<String, TreeSet<String>> carrier;
			TreeSet<String> versions;
			if (!jarURLs.containsKey(components[0])) {
				carrier = new HashMap<String, TreeSet<String>>();
				versions = new TreeSet<String>();
				versions.add(components[2]);
				carrier.put(components[1], versions);
				jarURLs.put(components[0], carrier);
			} else {
				carrier = jarURLs.get(components[0]);
				if (!carrier.containsKey(components[1])) {
					versions = new TreeSet<String>();
					versions.add(components[2]);
					carrier.put(components[1], versions);		
				} else {
					versions = carrier.get(components[1]);
					versions.add(components[2]);
				}
			}
		}

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
		textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 14));
		textLabel.setText("Base Webos Doctor");
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

	public void addListSelectionListener(DCV dcv, ListSelectionListener l) {
		switch (dcv) {
		case DEVICE: deviceList.addListSelectionListener(l); break;
		case CARRIER: carrierList.addListSelectionListener(l); break;
		case VERSION: versionList.addListSelectionListener(l); break;
		}
	}

	public String getRadioButtonSelected() {
		return connectorGroup.getSelection().getActionCommand();
	}

	private JPanel getContentPanel() {     

		JPanel contentPanel1 = new JPanel();

		connectorGroup = new ButtonGroup();
		welcomeTitle = new JLabel();
		jPanel1 = new JPanel();
		newJarRadioButton = new JRadioButton();
		existingJarRadioButton = new JRadioButton();

		JPanel newJarPanel = new JPanel(new BorderLayout());
		JPanel existingJarPanel = new JPanel(new BorderLayout());

		newJarRadioButton.setActionCommand("newJar");
		existingJarRadioButton.setActionCommand("existingJar");

		newJarRadioButton.setSelected(true);	

		contentPanel1.setLayout(new BorderLayout());

		welcomeTitle.setText("Please which Webos Doctor jar you will use as a base:");
		contentPanel1.add(welcomeTitle, java.awt.BorderLayout.NORTH);

		jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.PAGE_AXIS));

		jPanel1.add(Box.createVerticalStrut(10));

		newJarRadioButton.setText("Download new Webos Doctor jar");
		connectorGroup.add(newJarRadioButton);
		newJarPanel.add(newJarRadioButton, BorderLayout.NORTH);
		newJarPanel.add(Box.createHorizontalStrut(30), BorderLayout.WEST);

		deviceList = new JList(deviceModel);
		devicePanel = new JScrollPane(deviceList);

		carrierList = new JList(carrierModel);
		carrierList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		carrierList.setLayoutOrientation(JList.VERTICAL);
		carrierList.setVisibleRowCount(jarURLs.keySet().size());
		carrierPanel = new JScrollPane(carrierList);

		versionList = new JList(versionModel);
		versionPanel = new JScrollPane(versionList);

		cdvPanel = new JPanel();
		cdvPanel.setLayout(new BoxLayout(cdvPanel, BoxLayout.LINE_AXIS));
		cdvPanel.add(devicePanel);
		cdvPanel.add(carrierPanel);
		cdvPanel.add(versionPanel);

		newJarPanel.add(cdvPanel);

		jPanel1.add(newJarPanel);

		existingJarRadioButton.setText("Use existing Webos Doctor jar");
		connectorGroup.add(existingJarRadioButton);
		existingJarPanel.add(existingJarRadioButton, BorderLayout.NORTH);
		existingJarPanel.add(Box.createHorizontalStrut(30), BorderLayout.WEST);

		filePicker = new JButton("Choose file");
		existingJarPanel.add(filePicker);

		jPanel1.add(existingJarPanel);

		//jCheckBox1.setText("I agree to laugh at people who chose options other than mine");
		//jPanel1.add(jCheckBox1);

		contentPanel1.add(jPanel1, BorderLayout.CENTER);

		contentPanel1.add(Box.createHorizontalStrut(20), BorderLayout.WEST);

		//jLabel1.setText("Note that the 'Next' button is disabled until you check the box above.");
		//contentPanel1.add(jLabel1, java.awt.BorderLayout.SOUTH);

		return contentPanel1;
	}

	private ImageIcon getImageIcon() {

		//  Icon to be placed in the upper right corner.

		return null;
	}

	public void updateLists(DCV dcv) {

		switch (dcv) {
		case DEVICE:
			if (deviceModel.isEmpty()) {
				for (String device : jarURLs.keySet())
					deviceModel.addElement(device);
				deviceList.setSelectedIndex(0);
			} else {
				versionModel.removeAllElements();
				carrierModel.removeAllElements();
				HashMap<String, TreeSet<String>> carriers = jarURLs.get(deviceList.getSelectedValue());
				for (String carrier : carriers.keySet()) {
					carrierModel.addElement(carrier);
				}
				if (carrierList.isSelectionEmpty())
					carrierList.setSelectedIndex(0);
				Iterator<String> versions = carriers.get(carrierList.getSelectedValue()).descendingIterator();
				while (versions.hasNext())
					versionModel.addElement(versions.next());
				if (versionList.isSelectionEmpty())
					versionList.setSelectedIndex(0);
			}
			break;
		case CARRIER:
			versionModel.removeAllElements();
			Iterator<String> versions = jarURLs.get(deviceList.getSelectedValue()).get(carrierList.getSelectedValue()).descendingIterator();
			while (versions.hasNext())
				versionModel.addElement(versions.next());
			if (versionList.isSelectionEmpty())
				versionList.setSelectedIndex(0);
			
			break;
		case VERSION:
			break;
		}


		/*
		HashMap<String, TreeSet<String>> carriers = jarURLs.get(deviceList.getSelectedValue());
		for (String carrier : carriers.keySet()) {
			carrierModel.addElement(carrier);
			TreeSet<String> versions = carriers.get(carrier);
			versionModel.removeAllElements();
			for (String version : versions)
				versionModel.addElement(version);
		}
		if (carrierList.isSelectionEmpty())
			System.out.println("Empty carrier list");*/
	}

	public void enabledisable() {
		if (newJarRadioButton.isSelected()) {
			carrierList.setEnabled(true);
			versionList.setEnabled(true);
			filePicker.setEnabled(false);
		} else if (existingJarRadioButton.isSelected()) {
			carrierList.setEnabled(false);
			versionList.setEnabled(false);
			filePicker.setEnabled(true);
		}
	}

}
