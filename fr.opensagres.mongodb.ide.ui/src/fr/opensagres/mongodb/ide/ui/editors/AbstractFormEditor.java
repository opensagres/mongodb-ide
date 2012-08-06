/*******************************************************************************
 * Copyright (C) 2012 Angelo Zerr <angelo.zerr@gmail.com>, Pascal Leclercq <pascal.leclercq@gmail.com>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR - initial API and implementation
 *     Pascal Leclercq - initial API and implementation
 *******************************************************************************/
package fr.opensagres.mongodb.ide.ui.editors;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import fr.opensagres.mongodb.ide.core.utils.StringUtils;
import fr.opensagres.mongodb.ide.ui.internal.EditorPageDescriptor;
import fr.opensagres.mongodb.ide.ui.internal.EditorPagesRegistry;
import fr.opensagres.mongodb.ide.ui.internal.Trace;

/**
 * Abstract Form Editor.
 * 
 */
public abstract class AbstractFormEditor<EditorInput extends IEditorInput>
		extends FormEditor {

	@Override
	protected final void addPages() {
		try {
			onBeforeAddPages();
			doAddPages();
			try {
				doAddExtensionPages();
			} catch (CoreException e) {
				Trace.trace(Trace.STRING_SEVERE,
						"Error while adding pextension pages in the editor ", e);
			}
			onAfterAddPages();
		} catch (PartInitException e) {
			Trace.trace(Trace.STRING_SEVERE,
					"Error while adding page in the editor ", e);
		}
	}

	/**
	 * Methods called before addPages.
	 */
	protected void onBeforeAddPages() {
		// Do nothing
	}

	/**
	 * Methods called after addPages.
	 */
	protected void onAfterAddPages() {
		// Do nothing
	}

	@Override
	public void doSaveAs() {
		// Do nothing
	}

	public void doSave(IProgressMonitor monitor) {
		onBeforeSave(monitor);
		onSave(monitor);
		onAfterSave(monitor);
	}

	/**
	 * Method called on before save.
	 * 
	 * @param monitor
	 */
	protected void onBeforeSave(IProgressMonitor monitor) {
		// commit pages
		commitPages(true);
	}

	/**
	 * Method called on after save.
	 * 
	 * @param monitor
	 */
	protected void onAfterSave(IProgressMonitor monitor) {
		// modify the dirty state.
		editorDirtyStateChanged();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EditorInput getEditorInput() {
		return (EditorInput) super.getEditorInput();
	}

	private void doAddExtensionPages() throws CoreException, PartInitException {
		String editorId = getEditorId();
		if (editorId != null) {
			List<EditorPageDescriptor> editorDescriptor = EditorPagesRegistry
					.getRegistry().getDescriptors(editorId);
			if (editorDescriptor != null) {
				int pageIndex = 0;
				String pageText = null;
				for (EditorPageDescriptor descriptor : editorDescriptor) {
					IEditorPart page = descriptor.createPage(this);
					pageIndex = this.addPage(page, super.getEditorInput());
					pageText = descriptor.getTitle();
					if (pageText != null) {
						this.setPageText(pageIndex, pageText);
					}

				}
			}
		}
	}

	@Override
	protected void createPages() {
		// creates pages
		super.createPages();
		// modify the title of the editor if needed.
		String partName = getOverridePartName();
		if (StringUtils.isNotEmpty(partName)) {
			super.setPartName(partName);
		}
		// select a page if needed on page loaded.
		String pageId = getActivePageIdOnLoad();
		if (StringUtils.isNotEmpty(pageId)) {
			super.setActivePage(pageId);
		}
	}

	protected String getOverridePartName() {
		return null;
	}

	protected String getActivePageIdOnLoad() {
		return null;
	}

	public void contributeToToolbar(IToolBarManager manager) {

	}

	protected String getEditorId() {
		return null;
	}

	/**
	 * Subclass should implement this method to add pages to the editor using
	 * 'addPage(IFormPage)' method.
	 * 
	 * @throws PartInitException
	 */
	protected abstract void doAddPages() throws PartInitException;

	/**
	 * Saves the contents of this editor.
	 * <p>
	 * Subclasses must override this method to implement the open-save-close
	 * lifecycle for an editor. For greater details, see
	 * <code>IEditorPart</code>
	 * </p>
	 * 
	 * @see IEditorPart
	 */
	protected abstract void onSave(IProgressMonitor monitor);
}
