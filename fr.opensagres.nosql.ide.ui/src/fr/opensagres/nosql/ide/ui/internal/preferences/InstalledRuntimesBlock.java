package fr.opensagres.nosql.ide.ui.internal.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.model.IServerRuntime;
import fr.opensagres.nosql.ide.core.utils.CollectionUtils;
import fr.opensagres.nosql.ide.ui.PlatformUI;
import fr.opensagres.nosql.ide.ui.dialogs.AbstractRuntimeDialog;
import fr.opensagres.nosql.ide.ui.extensions.IDialogFactory;
import fr.opensagres.nosql.ide.ui.internal.Activator;
import fr.opensagres.nosql.ide.ui.internal.Messages;
import fr.opensagres.nosql.ide.ui.viewers.ServerTypeContentProvider;
import fr.opensagres.nosql.ide.ui.viewers.ServerTypeLabelProvider;

public class InstalledRuntimesBlock extends AbstractTableBlock implements
		ISelectionProvider {
	private Composite fControl;
	private final List<IServerRuntime> runtimes = new ArrayList<IServerRuntime>();
	private CheckboxTableViewer tableViewer;
	private Button fAddButton;
	private Button fRemoveButton;
	private Button fEditButton;
	private final ListenerList fSelectionListeners = new ListenerList();
	private ISelection fPrevSelection = new StructuredSelection();
	private ComboViewer serverTypeViewer;

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		fSelectionListeners.add(listener);
	}

	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		fSelectionListeners.remove(listener);
	}

	public ISelection getSelection() {
		return new StructuredSelection(tableViewer.getCheckedElements());
	}

	public void setSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			if (!selection.equals(fPrevSelection)) {
				fPrevSelection = selection;
				Object jre = ((IStructuredSelection) selection)
						.getFirstElement();
				if (jre == null) {
					tableViewer.setCheckedElements(new Object[0]);
				} else {
					tableViewer.setCheckedElements(new Object[] { jre });
					tableViewer.reveal(jre);
				}
				fireSelectionChanged();
			}
		}
	}

	public void createControl(Composite ancestor) {

		Composite parent = new Composite(ancestor, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		parent.setLayout(layout);
		Font font = ancestor.getFont();
		parent.setFont(font);
		fControl = parent;

		GridData data;

		// Server type combo
		Composite p = new Composite(parent, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		p.setLayout(layout);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		p.setLayoutData(data);

		Label serverTypeLabel = new Label(p, SWT.NONE);
		serverTypeLabel
				.setText(Messages.ServerRuntimePreferencePage_serverType);
		serverTypeLabel.setFont(font);

		serverTypeViewer = new ComboViewer(p, SWT.READ_ONLY);
		serverTypeViewer
				.setLabelProvider(ServerTypeLabelProvider.getInstance());
		serverTypeViewer.setContentProvider(ServerTypeContentProvider
				.getInstance());
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		serverTypeViewer.getControl().setLayoutData(data);
		Collection<IServerType> serverTypes = Platform.getServerTypeRegistry()
				.getTypes();
		serverTypeViewer.setInput(serverTypes);
		serverTypeViewer.getCombo().addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						fAddButton.setEnabled(true);
					}
				});
		IServerType firstServerType = CollectionUtils
				.getFirstElement(serverTypes);
		if (firstServerType != null) {
			serverTypeViewer.setSelection(new StructuredSelection(
					firstServerType));
		}

		// Description of the table
		Label tableLabel = new Label(parent, SWT.NONE);
		tableLabel.setText(Messages.InstalledRuntimesBlock_desc);
		data = new GridData();
		data.horizontalSpan = 2;
		tableLabel.setLayoutData(data);
		tableLabel.setFont(font);

		// List of runtimes
		Table fTable = new Table(parent, SWT.CHECK | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL);

		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 450;
		fTable.setLayoutData(data);
		fTable.setFont(font);

		fTable.setHeaderVisible(true);
		fTable.setLinesVisible(true);

		// Name column
		TableColumn column1 = new TableColumn(fTable, SWT.NONE);
		column1.setWidth(180);
		column1.setResizable(true);
		column1.setText(Messages.InstalledRuntimesBlock_nameColumn);
		column1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sortByName();
			}
		});

		// Installation directory column
		TableColumn column2 = new TableColumn(fTable, SWT.NONE);
		column2.setWidth(180);
		column2.setResizable(true);
		column2.setText(Messages.InstalledRuntimesBlock_installDirColumn);
		column2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sortByInstallDir();
			}
		});

		tableViewer = new CheckboxTableViewer(fTable);
		tableViewer.setLabelProvider(new RuntimesLabelProvider());
		tableViewer.setContentProvider(new RuntimesContentProvider());

		tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent evt) {
						enableButtons();
					}
				});

		tableViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getChecked()) {
					setCheckedInstall((IServerRuntime) event.getElement());
				} else {
					setCheckedInstall(null);
				}
			}
		});

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent e) {
				if (!tableViewer.getSelection().isEmpty()) {
					editRuntime();
				}
			}
		});
		fTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.character == SWT.DEL && event.stateMask == 0) {
					removeRuntimes();
				}
			}
		});

		Composite buttons = new Composite(parent, SWT.NULL);
		buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		buttons.setLayout(layout);
		buttons.setFont(font);

		fAddButton = createPushButton(buttons, Messages.addButton);
		fAddButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				addRuntime();
			}
		});
		fAddButton.setEnabled(false);

		fEditButton = createPushButton(buttons, Messages.editButton);
		fEditButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				editRuntime();
			}
		});

		fRemoveButton = createPushButton(buttons, Messages.removeButton);
		fRemoveButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				removeRuntimes();
			}
		});

		// copied from ListDialogField.CreateSeparator()
		Label separator = new Label(buttons, SWT.NONE);
		separator.setVisible(false);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.BEGINNING;
		gd.heightHint = 4;
		separator.setLayoutData(gd);

		fillWithWorkspaceRuntimes();
		enableButtons();

		restoreColumnSettings();
	}

	protected void fillWithWorkspaceRuntimes() {
		setRuntimes(Platform.getServerRuntimeManager().getRuntimes());
	}

	private void fireSelectionChanged() {
		SelectionChangedEvent event = new SelectionChangedEvent(this,
				getSelection());
		Object[] listeners = fSelectionListeners.getListeners();
		for (Object element : listeners) {
			ISelectionChangedListener listener = (ISelectionChangedListener) element;
			listener.selectionChanged(event);
		}
	}

	/**
	 * Sorts by type, and name within type.
	 */
	// private void sortByType() {
	// tableViewer.setSorter(new ViewerSorter() {
	// @Override
	// public int compare(Viewer viewer, Object e1, Object e2) {
	// IServerRuntime left = (IServerRuntime) e1;
	// IServerRuntime right = (IServerRuntime) e2;
	// return left
	// .getRuntimeType()
	// .getLabel()
	// .compareToIgnoreCase(
	// right.getRuntimeType().getLabel());
	// }
	//
	// @Override
	// public boolean isSorterProperty(Object element, String property) {
	// return true;
	// }
	// });
	// }

	private void sortByInstallDir() {
		tableViewer.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				IServerRuntime left = (IServerRuntime) e1;
				IServerRuntime right = (IServerRuntime) e2;
				return left.getInstallDir().compareToIgnoreCase(
						right.getInstallDir());
			}

			@Override
			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});
	}

	/**
	 * Sorts by name.
	 */
	private void sortByName() {
		tableViewer.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if ((e1 instanceof IServerRuntime)
						&& (e2 instanceof IServerRuntime)) {
					IServerRuntime left = (IServerRuntime) e1;
					IServerRuntime right = (IServerRuntime) e2;
					return left.getName().compareToIgnoreCase(right.getName());
				}
				return super.compare(viewer, e1, e2);
			}

			@Override
			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});
	}

	private void enableButtons() {
		IStructuredSelection selection = (IStructuredSelection) tableViewer
				.getSelection();
		int selectionCount = selection.size();
		fEditButton.setEnabled(selectionCount == 1);
		fRemoveButton.setEnabled(selectionCount == 1);
		fAddButton.setEnabled(!serverTypeViewer.getSelection().isEmpty());
		// if (selectionCount > 0
		// && selectionCount < tableViewer.getTable().getItemCount()) {
		// //Iterator<?> iterator = selection.iterator();
		// // while (iterator.hasNext()) {
		// // IServerRuntime install = (IServerRuntime) iterator.next();
		// // // if (install.isContributed()) {
		// // fRemoveButton.setEnabled(false);
		// // return;
		// // // }
		// // }
		// fRemoveButton.setEnabled(true);
		// } else {
		// fRemoveButton.setEnabled(false);
		// }
	}

	protected Button createPushButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.setLayoutData(GridDataFactory.fillDefaults().create());
		return button;
	}

	public Control getControl() {
		return fControl;
	}

	protected void setRuntimes(List<IServerRuntime> vms) {
		runtimes.clear();
		for (IServerRuntime element : vms) {
			runtimes.add(element);
		}
		tableViewer.setInput(runtimes);
		// tableViewer.refresh();
	}

	public IServerRuntime[] getRuntimes() {
		return runtimes.toArray(new IServerRuntime[runtimes.size()]);
	}

	private void addRuntime() {
		IServerType serverType = (IServerType) ((IStructuredSelection) serverTypeViewer
				.getSelection()).getFirstElement();
		AbstractRuntimeDialog dialog = createDialog(serverType,
				fAddButton.getShell());
		if (dialog != null && dialog.open() == Window.OK) {
			runtimeAdded(dialog.getRuntime());
		}
	}

	public void runtimeAdded(IServerRuntime install) {
		runtimes.add(install);
		tableViewer.add(install);
		tableViewer.setSelection(new StructuredSelection(install), true);
	}

	public boolean isDuplicateName(String name) {
		for (int i = 0; i < runtimes.size(); i++) {
			IServerRuntime install = runtimes.get(i);
			if (install.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private void editRuntime() {
		IStructuredSelection selection = (IStructuredSelection) tableViewer
				.getSelection();
		IServerRuntime runtime = (IServerRuntime) selection.getFirstElement();
		if (runtime == null) {
			return;
		}

		AbstractRuntimeDialog dialog = createDialog(runtime.getServerType(),
				fEditButton.getShell());
		if (dialog != null) {
			dialog.setRuntime(runtime);
			if (dialog.open() == Window.OK) {
				// refresh viewer
				tableViewer.refresh(runtime);
			}
		}
	}

	private AbstractRuntimeDialog createDialog(IServerType serverType,
			Shell parentShell) {
		IDialogFactory factory = PlatformUI.getDialogFactoryRegistry()
				.getFactory(serverType, AbstractRuntimeDialog.DIALOG_TYPE);
		if (factory != null) {
			return (AbstractRuntimeDialog) factory.create(parentShell);
		}
		return null;
	}

	private void removeRuntimes() {
		IStructuredSelection selection = (IStructuredSelection) tableViewer
				.getSelection();
		IServerRuntime[] vms = new IServerRuntime[selection.size()];
		Iterator<?> iter = selection.iterator();
		int i = 0;
		while (iter.hasNext()) {
			vms[i] = (IServerRuntime) iter.next();
			i++;
		}
		removeRuntimes(vms);
	}

	public void removeRuntimes(IServerRuntime[] theInstalls) {
		IStructuredSelection prev = (IStructuredSelection) getSelection();
		for (IServerRuntime element : theInstalls) {
			runtimes.remove(element);
		}
		tableViewer.refresh();
		IStructuredSelection curr = (IStructuredSelection) getSelection();
		if (!curr.equals(prev)) {
			IServerRuntime[] installs = getRuntimes();
			if (curr.size() == 0 && installs.length == 1) {
				// pick a default install automatically
				setSelection(new StructuredSelection(installs[0]));
			} else {
				fireSelectionChanged();
			}
		}
	}

	public void setCheckedInstall(IServerRuntime install) {
		if (install == null) {
			setSelection(new StructuredSelection());
		} else {
			setSelection(new StructuredSelection(install));
		}
	}

	public IServerRuntime getCheckedInstall() {
		Object[] objects = tableViewer.getCheckedElements();
		if (objects.length == 0) {
			return null;
		}
		return (IServerRuntime) objects[0];
	}

	@Override
	protected void setSortColumn(int column) {
		switch (column) {
		case 1:
			sortByName();
			break;
		// case 2:
		// sortByType();
		// break;
		}
		super.setSortColumn(column);
	}

	@Override
	protected Table getTable() {
		return tableViewer.getTable();
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		return Activator.getDefault().getDialogSettings();
	}

	private class RuntimesContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object input) {
			return runtimes.toArray();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}
	}

	private static class RuntimesLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof IServerRuntime) {
				IServerRuntime install = (IServerRuntime) element;
				switch (columnIndex) {
				case 0:
					return install.getName();
				case 1:
					return install.getInstallDir();
					// case 2:
					// if (install.getDebugger() != null)
					// {
					// return install.getDebugger().getName();
					// }
					// return Messages.InstalledRuntimesBlock_8;
				}
			}
			return element.toString();
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

	}

	@Override
	protected String getQualifier() {
		return "";
	}
}
