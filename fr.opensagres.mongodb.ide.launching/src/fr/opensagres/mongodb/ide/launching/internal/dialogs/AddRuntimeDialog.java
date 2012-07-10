package fr.opensagres.mongodb.ide.launching.internal.dialogs;

import org.eclipse.jface.dialogs.Dialog;
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

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.launching.internal.Messages;

public class AddRuntimeDialog extends TitleAreaDialog {

	private Text name;
	private Text installDir;
	private Label installLabel;

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

		getShell().setText(Messages.AddRuntimeDialog_title);
		setTitle(Messages.AddRuntimeDialog_title);
		setMessage(Messages.AddRuntimeDialog_desc);

		Composite comp = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Name field
		Label label = new Label(comp, SWT.NONE);
		label.setText(Messages.AddRuntimeDialog_runtimeName);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		name = new Text(comp, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(data);
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				// runtimeWC.setName(name.getText());
				validate();
			}
		});

		// Installation directory field.
		label = new Label(comp, SWT.NONE);
		label.setText(Messages.AddRuntimeDialog_installDir);
		data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		installDir = new Text(comp, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		installDir.setLayoutData(data);
		installDir.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				// runtimeWC.setLocation(new Path(installDir.getText()));
				validate();
			}
		});

		Button browse = new Button(comp, SWT.NONE);
		browse.setText(Messages.Button_browse);
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

		installLabel = new Label(comp, SWT.RIGHT);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 10;
		installLabel.setLayoutData(data);

		Dialog.applyDialogFont(comp);
		return comp;
	}

	protected void validate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void okPressed() {
		if(runtime == null) {
			runtime= new MongoRuntime();
		}
		runtime.setName(name.getText());
		runtime.setPath(installDir.getText());
		super.okPressed();
	}
	
	public MongoRuntime getRuntime() {
		return runtime;
	}
}
