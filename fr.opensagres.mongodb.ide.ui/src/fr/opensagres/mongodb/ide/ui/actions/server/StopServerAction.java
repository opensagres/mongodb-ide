/*******************************************************************************
 * Copyright (c) 2003, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - Initial API and implementation
 *******************************************************************************/
package fr.opensagres.mongodb.ide.ui.actions.server;

import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

/**
 * Stop (terminate) a server.
 */
public class StopServerAction extends AbstractServerAction {

	public StopServerAction(Shell shell, ISelectionProvider selectionProvider) {
		super(shell, selectionProvider, Messages.actionStop);
		setToolTipText(Messages.actionStopToolTip);
		setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_ELCL_STOP));
		setHoverImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_CLCL_STOP));
		setDisabledImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_DLCL_STOP));
		// setActionDefinitionId("org.eclipse.wst.server.stop");
		try {
			selectionChanged((IStructuredSelection) selectionProvider
					.getSelection());
		} catch (Exception e) {
			// ignore
		}
	}

	/**
	 * Return true if this server can currently be acted on.
	 * 
	 * @return boolean
	 * @param server
	 *            a server
	 */
	public boolean accept(Server server) {
		return server.canStop().isOK();
	}

	/**
	 * Perform action on this server.
	 * 
	 * @param server
	 *            a server
	 */
	public void perform(Server server) {
		stop(server, shell);
	}

	public static void stop(Server server, Shell shell) {
		// ServerUIPlugin.addTerminationWatch(shell, server,
		// ServerUIPlugin.STOP); // TODO - should redo
		//
		// IJobManager jobManager = Job.getJobManager();
		// Job[] jobs = jobManager.find(ServerUtil.SERVER_JOB_FAMILY);
		// for (Job j: jobs) {
		// if (j instanceof Server.StartJob) {
		// Server.StartJob startJob = (Server.StartJob) j;
		// if (startJob.getServer().equals(server)) {
		// startJob.cancel();
		// return;
		// }
		// }
		// }

		try {
			server.stop(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean accept(Database database) {
		return database.canStopShell();
	}

	@Override
	public void perform(Database database) {
		database.stopShell();
	}
}