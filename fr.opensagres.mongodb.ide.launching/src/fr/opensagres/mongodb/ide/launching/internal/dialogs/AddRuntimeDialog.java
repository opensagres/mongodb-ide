package fr.opensagres.mongodb.ide.launching.internal.dialogs;

import java.io.IOException;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.osgi.util.NLS;
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

import com.mongodb.tools.process.InvalidMongoHomeDirException;
import com.mongodb.tools.process.MongoDBTools;
import com.mongodb.tools.process.MongoProcessFiles;
import com.mongodb.tools.process.mongod.MongodTools;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;
import fr.opensagres.mongodb.ide.launching.internal.Messages;

/**
 * Dialog used to create and edit Mongo {@link MongoRuntime}.
 * 
 */
public class AddRuntimeDialog extends TitleAreaDialog {

	private Text name;
	private Text installDir;

	private MongoRuntime runtime;

	public AddRuntimeDialog(Shell parentShell) {
		this(parentShell, null);
	}

	public AddRuntimeDialog(Shell parentShell, MongoRuntime runtime) {
		super(parentShell);
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
				MongoProcessFiles files = validate();
				if (files != null) {
					if (StringUtils.isEmpty(name.getText())) {
						try {
							String version = MongodTools.getDBVersion(files
									.getMongodFile());
							if (version != null) {
								name.setText(version);
							}
						} catch (IOException e1) {
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
						AddRuntimeDialog.this.getShell());
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

	protected MongoProcessFiles validate() {
		MongoProcessFiles files = validateInstallDir();
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

	private MongoProcessFiles validateInstallDir() {
		// 1) validate install dir
		if (StringUtils.isEmpty(installDir.getText())) {
			setErrorMessage(Messages.errorInstallDirRequired);
			return null;
		}
		try {
			return MongoDBTools.validateMongoHomeDir(installDir.getText());
		} catch (InvalidMongoHomeDirException e) {
			String errorMessage = null;
			switch (e.getType()) {
			case baseDirNotExists:
				errorMessage = NLS.bind(
						Messages.errorInstallDir_baseDirNotExists, e.getFile()
								.getPath());
				break;
			case baseDirNotDir:
				errorMessage = NLS.bind(Messages.errorInstallDir_baseDirNotDir,
						e.getFile().getPath());
				break;
			case binDirNotExists:
				errorMessage = NLS.bind(
						Messages.errorInstallDir_binDirNotExists, e.getFile()
								.getPath());
				break;
			case processFileNotExists:
				errorMessage = NLS.bind(
						Messages.errorInstallDir_processFileNotExists, e
								.getFile().getPath());
				break;
			}
			setErrorMessage(errorMessage);
			return null;
		}
	}

	@Override
	protected void okPressed() {
		if (runtime == null) {
			try {
				runtime = new MongoRuntime(name.getText(), installDir.getText());
			} catch (InvalidMongoHomeDirException e) {
				e.printStackTrace();
				return;
			}
		} else {
			runtime.setName(name.getText());
			try {
				runtime.setInstallDir(installDir.getText());
			} catch (InvalidMongoHomeDirException e) {
				e.printStackTrace();
			}
		}
		super.okPressed();
	}

	public MongoRuntime getRuntime() {
		return runtime;
	}
}
