package fr.opensagres.nosql.ide.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.opensagres.nosql.ide.core.model.IServerRuntime;
import fr.opensagres.nosql.ide.core.utils.StringUtils;
import fr.opensagres.nosql.ide.ui.internal.Messages;

/**
 * Dialog used to create and edit {@link IServerRuntime}.
 * 
 */
public abstract class AbstractRuntimeDialog<E> extends TitleAreaDialog {

	public static final String DIALOG_TYPE = "runtimeDialog";

	protected Text name;
	protected Text installDir;
	protected IServerRuntime runtime;

	public AbstractRuntimeDialog(Shell parentShell) {
		super(parentShell);
		this.runtime = null;
	}

	public void setRuntime(IServerRuntime runtime) {
		this.runtime = runtime;
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		String title = runtime == null ? Messages.AddRuntimeDialog_title
				: Messages.EditRuntimeDialog_title;
		getShell().setText(title);
		setTitle(title);
		setMessage(Messages.AddRuntimeDialog_desc);

		Composite comp = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Installation directory field.
		Label label = new Label(comp, SWT.NONE);
		label.setText(Messages.AddRuntimeDialog_installDir);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		installDir = new Text(comp, SWT.BORDER);
		if (runtime != null) {
			installDir.setText(runtime.getInstallDir());
		}
		data = new GridData(GridData.FILL_HORIZONTAL);
		installDir.setLayoutData(data);
		installDir.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				E files = validate();
				if (files != null) {
					if (StringUtils.isEmpty(name.getText())) {
						try {
							String generatedName = getGeneratedName(files);
							if (generatedName != null) {
								name.setText(generatedName);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		Button browse = new Button(comp, SWT.NONE);
		browse.setText(Messages.browseButton);
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				DirectoryDialog dialog = new DirectoryDialog(
						AbstractRuntimeDialog.this.getShell());
				dialog.setMessage(Messages.AddRuntimeDialog_selectInstallDir);
				dialog.setFilterPath(installDir.getText());
				String selectedDirectory = dialog.open();
				if (selectedDirectory != null)
					installDir.setText(selectedDirectory);
			}
		});

		// Name field
		label = new Label(comp, SWT.NONE);
		label.setText(Messages.AddRuntimeDialog_runtimeName);
		data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		name = new Text(comp, SWT.BORDER);
		if (runtime != null) {
			name.setText(runtime.getName());
		}
		data = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(data);
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		Dialog.applyDialogFont(comp);
		return comp;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		validate();
		return contents;
	}

	protected E validate() {
		E files = validateInstallDir();
		if (files == null) {
			super.getButton(IDialogConstants.OK_ID).setEnabled(false);
			return null;
		}
		// 2) validate name
		if (StringUtils.isEmpty(name.getText())) {
			super.getButton(IDialogConstants.OK_ID).setEnabled(false);
			setErrorMessage(Messages.errorRuntimeNameRequired);
		} else {
			super.getButton(IDialogConstants.OK_ID).setEnabled(true);
			setErrorMessage(null);
		}
		return files;

	}

	@Override
	protected void okPressed() {
		if (runtime == null) {
			try {
				runtime = createRuntime(name.getText(), installDir.getText());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else {
			runtime.setName(name.getText());
			try {
				runtime.setInstallDir(installDir.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.okPressed();
	}

	public IServerRuntime getRuntime() {
		return runtime;
	}

	protected abstract E validateInstallDir();

	protected abstract IServerRuntime createRuntime(String name,
			String installDir) throws Exception;

	protected abstract String getGeneratedName(E files) throws Exception;
}
