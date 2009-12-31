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

import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JDialog;
import us.ryanhope.wizard.*;

public class MetaDoctor {

	static HashMap<String, String> globalData;
	static File tempDir;
	static final int BUFFER = 4096;

	static int extractJar(String filename) {

		int ret										= 0;
		int pend									= 0;
		int count 									= 0;
		byte data[] 								= null;
		JarFile jar									= null;
		File output_file							= null;
		String path_base							= null;
		JarEntry jar_entry							= null;
		FileOutputStream fos 						= null;
		String output_filename						= null;
		BufferedInputStream is 						= null;
		BufferedOutputStream dest 					= null;
		Enumeration<? extends JarEntry> entries		= null;

		try {

			jar = new JarFile(filename);
			entries = jar.entries();
			while(entries.hasMoreElements()) {
				jar_entry = (JarEntry)entries.nextElement();
				is = new BufferedInputStream(jar.getInputStream(jar_entry));
				data = new byte[BUFFER];
				path_base = filename.substring(filename.lastIndexOf('/')+1, filename.lastIndexOf('.'));
				new File(path_base).mkdir();
				output_filename = path_base + "/" + jar_entry.getName();
				output_file = new File(output_filename);
				System.out.println(output_filename);
				if (output_file.isDirectory())
					continue;
				pend = output_filename.lastIndexOf('/');
				if (pend>0) {
					new File(output_filename.substring(0, pend)).mkdirs();
					if (output_filename.endsWith("/"))
						continue;
				}
				fos = new FileOutputStream(output_filename);
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = is.read(data, 0, BUFFER)) != -1)
					dest.write(data, 0, count);

				dest.flush();
				dest.close();
				fos.close();
				is.close();

			}

		} catch (IOException e) {

			e.printStackTrace();
			ret = -1;

		}

		return ret;

	}

	public static void main(String[] args) {

		String globalTmpDir = System.getProperty("java.io.tmpdir");
		Random r = new Random();

		do {
			tempDir = new File(globalTmpDir+"/metadoctor-"+Long.toString(Math.abs(r.nextLong()), 36));    			
		} while (tempDir.exists());
		tempDir.mkdirs();
		tempDir.deleteOnExit();

		globalData = new HashMap<String, String>();
		globalData.put("jarloc", " ");

		Wizard wizard = new Wizard();

		JDialog dialog = wizard.getDialog();
		dialog.setPreferredSize(new Dimension(640,480));        
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
