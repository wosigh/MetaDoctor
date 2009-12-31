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
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;

public class MetaDoctorPanel1 extends JPanel {
 
	private static final long serialVersionUID = 1L;
	
	private JLabel blankSpace;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;

    private JLabel welcomeTitle;
    private JPanel contentPanel;
    
    private JLabel iconLabel;
    private ImageIcon icon;
        
    public MetaDoctorPanel1() {
    	   
        iconLabel = new JLabel();
        contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

        icon = getImageIcon();

        setLayout(new java.awt.BorderLayout());

        if (icon != null)
            iconLabel.setIcon(icon);
        
        iconLabel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        
        add(iconLabel, BorderLayout.WEST);
        
        JPanel secondaryPanel = new JPanel();
        secondaryPanel.add(contentPanel, BorderLayout.NORTH);
        add(secondaryPanel, BorderLayout.CENTER);
        
    }
    
    
    private JPanel getContentPanel() {
        
        JPanel contentPanel1 = new JPanel();
        JPanel jPanel1 = new JPanel();
        
        welcomeTitle = new JLabel();
        blankSpace = new JLabel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();

        contentPanel1.setLayout(new java.awt.BorderLayout());
      
        welcomeTitle.setText("The Webos Internals MetaDoctor!");
        welcomeTitle.setFont(welcomeTitle.getFont().deriveFont(Font.BOLD, 18));
        contentPanel1.add(welcomeTitle, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        jPanel1.add(blankSpace);
        jLabel1.setText("The Webos Internals MetaDoctor is a tool which ");
        jPanel1.add(jLabel1);
        jLabel2.setText("modify a copy of the Palm Webos Doctor to bypass ");
        jPanel1.add(jLabel2);
        jLabel3.setText("the \"First Use\" application and allow the phone ");
        jPanel1.add(jLabel3);
        jLabel4.setText("to run without having been activated on a cell network. ");
        jPanel1.add(jLabel4);
        jPanel1.add(jLabel5);
        jLabel6.setText("Press the 'Next' button to continue....");
        jPanel1.add(jLabel6);

        contentPanel1.add(jPanel1, java.awt.BorderLayout.CENTER);

        return contentPanel1;
        
    }

    private ImageIcon getImageIcon() {
        //return new ImageIcon((URL)getResource("clouds.jpg"));
    	return new ImageIcon((URL)getResource("webos-internals-logo.png"));
    }
    
    private Object getResource(String key) {

        URL url = null;
        String name = key;

        if (name != null) {

            try {
                Class<?> c = Class.forName("us.ryanhope.metadoctor.MetaDoctor");
                url = c.getResource(name);
            } catch (ClassNotFoundException cnfe) {
                System.err.println("Unable to find Main class");
            }
            return url;
        } else
            return null;

    }
 
}
