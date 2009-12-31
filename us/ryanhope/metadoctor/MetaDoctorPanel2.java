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
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

public class MetaDoctorPanel2 extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private boolean firstRunDone = false;

	enum DCV {
		NONE,
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
	
	private JLabel deviceLabel;
	private JLabel carrierLabel;
	private JLabel versionLabel;
	
	private JLabel jarLabel;

	final DefaultListModel deviceModel = new DefaultListModel();
	final DefaultListModel carrierModel = new DefaultListModel();
	final DefaultListModel versionModel = new DefaultListModel();

	private HashMap<String, HashMap<String, TreeMap<String, String>>> jarURLs;
	
	final JFileChooser fc = new JFileChooser();
	
	MetaDoctorPanel2Descriptor desc;

	public MetaDoctorPanel2(MetaDoctorPanel2Descriptor desc) {

		super();
		
		this.desc = desc;
		
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "*.jar";
			}
			public boolean accept(File f) {
				return f.getName().endsWith(".jar");
			}
		});

		jarURLs = new HashMap<String, HashMap<String, TreeMap<String, String>>>();

		PropertyResourceBundle urls = (PropertyResourceBundle)
		ResourceBundle.getBundle("us.ryanhope.metadoctor.jar-urls");
		Enumeration<String> e = urls.getKeys();
		while (e.hasMoreElements()){
			String key = e.nextElement();
			String[] components = key.split("-");
			HashMap<String, TreeMap<String, String>> carrier;
			TreeMap<String, String> versions;
			if (!jarURLs.containsKey(components[0])) {
				carrier = new HashMap<String, TreeMap<String, String>>();
				versions = new TreeMap<String, String>();
				versions.put(components[2], urls.getString(key));
				carrier.put(components[1], versions);
				jarURLs.put(components[0], carrier);
			} else {
				carrier = jarURLs.get(components[0]);
				if (!carrier.containsKey(components[1])) {
					versions = new TreeMap<String, String>();
					versions.put(components[2], urls.getString(key));
					carrier.put(components[1], versions);		
				} else {
					versions = carrier.get(components[1]);
					versions.put(components[2], urls.getString(key));
				}
			}
		}
		
		jarLabel = new JLabel(" ");
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

		Dimension ld = new Dimension(180,100);
		
		deviceList = new JList(deviceModel);
		deviceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		devicePanel = new JScrollPane(deviceList);
		devicePanel.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);
		devicePanel.setMinimumSize(ld);
		devicePanel.setPreferredSize(ld);

		carrierList = new JList(carrierModel);
		carrierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		carrierList.setLayoutOrientation(JList.VERTICAL);
		carrierList.setVisibleRowCount(jarURLs.keySet().size());
		carrierPanel = new JScrollPane(carrierList);
		carrierPanel.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);
		carrierPanel.setMinimumSize(ld);
		carrierPanel.setPreferredSize(ld);
		
		versionList = new JList(versionModel);
		versionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		versionPanel = new JScrollPane(versionList);
		versionPanel.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);
		versionPanel.setMinimumSize(ld);
		versionPanel.setPreferredSize(ld);

		cdvPanel = new JPanel(new BorderLayout());
		JPanel cdvPanelNorth = new JPanel(new GridLayout(1,3));
		JPanel cdvPanelSouth = new JPanel(new GridLayout(1,3));
		
		deviceLabel = new JLabel("Device", JLabel.CENTER);
		carrierLabel = new JLabel("Carrier", JLabel.CENTER);
		versionLabel = new JLabel("Version", JLabel.CENTER);
		
		cdvPanelNorth.add(deviceLabel);
		cdvPanelNorth.add(carrierLabel);
		cdvPanelNorth.add(versionLabel);
		cdvPanelSouth.add(devicePanel);
		cdvPanelSouth.add(carrierPanel);
		cdvPanelSouth.add(versionPanel);
		
		cdvPanel.add(cdvPanelNorth, BorderLayout.NORTH);
		cdvPanel.add(cdvPanelSouth, BorderLayout.SOUTH);

		newJarPanel.add(cdvPanel);

		jPanel1.add(newJarPanel);

		existingJarRadioButton.setText("Use existing Webos Doctor jar");
		connectorGroup.add(existingJarRadioButton);
		existingJarPanel.add(existingJarRadioButton, BorderLayout.NORTH);
		existingJarPanel.add(Box.createHorizontalStrut(30), BorderLayout.WEST);

		filePicker = new JButton("Choose file");
		filePicker.addActionListener(this);
		existingJarPanel.add(filePicker);

		jPanel1.add(existingJarPanel);
		
		jPanel1.add(Box.createVerticalStrut(30));
		
		JPanel filePanel = new JPanel(new BorderLayout());
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder title = BorderFactory.createTitledBorder(loweredetched, "Jar Location");
		title.setTitleJustification(TitledBorder.LEFT);
		filePanel.setBorder(title);
		//jarLabel.setPreferredSize(new Dimension(200,20));
		filePanel.add(jarLabel);
		jPanel1.add(filePanel, BorderLayout.SOUTH);
		
		contentPanel1.add(jPanel1, BorderLayout.CENTER);

		return contentPanel1;
	}

	private ImageIcon getImageIcon() {

		//  Icon to be placed in the upper right corner.

		return null;
	}

	public void updateLists(DCV dcv, ListSelectionEvent e) {

		if (dcv==DCV.NONE) {
			if (firstRunDone) return;
			for (String device : jarURLs.keySet())
				deviceModel.addElement(device);
			//deviceList.setSelectedIndex(0);
			firstRunDone = true;
		} else if (dcv==DCV.DEVICE) {
			if (!e.getValueIsAdjusting()) {
				carrierModel.removeAllElements();
				versionModel.removeAllElements();
				HashMap<String, TreeMap<String, String>> carriers = jarURLs.get(deviceList.getSelectedValue());
				for (String carrier : carriers.keySet())
					carrierModel.addElement(carrier);
				//carrierList.setSelectedIndex(0);
			}
		} else if (dcv==DCV.CARRIER) {
			if (!e.getValueIsAdjusting()) {
				versionModel.removeAllElements();
				if (!carrierModel.isEmpty()) {
					if (carrierList.isSelectionEmpty())
						carrierList.setSelectedIndex(0);
					if (versionModel.isEmpty()) {
						for (String version : jarURLs.get(deviceList.getSelectedValue()).get(carrierList.getSelectedValue()).keySet())
							versionModel.addElement(version);
						//versionList.setSelectedIndex(0);
					}
				}
			}
		} else if (dcv==DCV.VERSION) {
			if (versionList.isSelectionEmpty()) {
				jarLabel.setText(" ");
				desc.getWizard().setNextFinishButtonEnabled(false);
			} else if (!e.getValueIsAdjusting()) {
				jarLabel.setText(jarURLs.get(deviceList.getSelectedValue()).get(carrierList.getSelectedValue()).get(versionList.getSelectedValue()));
				desc.getWizard().setNextFinishButtonEnabled(true);
			}
		}

	}

	public void enabledisable() {
		if (newJarRadioButton.isSelected()) {
			deviceList.setEnabled(true);
			carrierList.setEnabled(true);
			versionList.setEnabled(true);
			deviceLabel.setEnabled(true);
			carrierLabel.setEnabled(true);
			versionLabel.setEnabled(true);
			filePicker.setEnabled(false);
		} else if (existingJarRadioButton.isSelected()) {
			deviceList.setEnabled(false);
			carrierList.setEnabled(false);
			versionList.setEnabled(false);
			deviceLabel.setEnabled(false);
			carrierLabel.setEnabled(false);
			versionLabel.setEnabled(false);
			filePicker.setEnabled(true);
		}
		jarLabel.setText(" ");
		desc.getWizard().setNextFinishButtonEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {
		int returnVal = fc.showOpenDialog(contentPanel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            jarLabel.setText(file.getAbsolutePath());
            desc.getWizard().setNextFinishButtonEnabled(true);  
		}
	}

}
