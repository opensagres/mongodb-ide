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

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.model.ServerState;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;

/**
 * Stop (terminate) a server.
 */
public class StopServerAction extends TreeNodeActionGroupAdapter {

	public StopServerAction(Shell shell, ISelectionProvider selectionProvider,
			List<Action> actions) {
		super(shell, selectionProvider, actions);
		setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_ELCL_STOP));
		setHoverImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_CLCL_STOP));
		setDisabledImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_DLCL_STOP));
	}

	/**
	 * Return true if this server can currently be acted on.
	 * 
	 * @return boolean
	 * @param server
	 *            a server
	 */
	@Override
	protected boolean accept(Server server) {
		for (Action action : actions) {
			if (((AbstractTreeNodeAction) action).accept(server)) {
				return true;
			}
		}
		return false;
	}
}