package fr.opensagres.mongodb.ide.launching.internal.preferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.launching.internal.Activator;
import fr.opensagres.mongodb.ide.launching.internal.Messages;
import fr.opensagres.mongodb.ide.launching.internal.dialogs.AddRuntimeDialog;

public class InstalledRuntimesBlock extends AbstractTableBlock implements
		ISelectionProvider {
	private Composite fControl;
	private final List<MongoRuntime> runtimes = new ArrayList<MongoRuntime>();
	private CheckboxTableViewer tableViewer;
	private Button fAddButton;
	private Button fRemoveButton;
	private Button fEditButton;
	private final ListenerList fSelectionListeners = new ListenerList();
	private ISelection fPrevSelection = new StructuredSelection();

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

		Label tableLabel = new Label(parent, SWT.NONE);
		tableLabel.setText(Messages.InstalledRuntimesBlock_0);
		data = new GridData();
		data.horizontalSpan = 2;
		tableLabel.setLayoutData(data);
		tableLabel.setFont(font);

		Table fTable = new Table(parent, SWT.CHECK | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL);

		data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 450;
		fTable.setLayoutData(data);
		fTable.setFont(font);

		fTable.setHeaderVisible(true);
		fTable.setLinesVisible(true);

		TableColumn column1 = new TableColumn(fTable, SWT.NONE);
		column1.setWidth(180);
		column1.setResizable(true);
		column1.setText(Messages.InstalledRuntimesBlock_1);
		column1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sortByName();
			}
		});

		TableColumn column2 = new TableColumn(fTable, SWT.NONE);
		column2.setWidth(180);
		column2.setResizable(true);
		column2.setText(Messages.InstalledRuntimesBlock_2);
		column2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sortBySource();
			}
		});

		// TableColumn column4 = new TableColumn(fTable, SWT.NONE);
		// column4.setWidth(180);
		// column4.setResizable(true);
		// column4.setText(Messages.InstalledRuntimesBlock_4);
		// column4.addSelectionListener(new SelectionAdapter()
		// {
		// @Override
		// public void widgetSelected(SelectionEvent e)
		// {
		// sortByVersion();
		// }
		// });

		tableViewer = new CheckboxTableViewer(fTable);
		tableViewer.setLabelProvider(new VMLabelProvider());
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
					setCheckedInstall((MongoRuntime) event.getElement());
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

		fAddButton = createPushButton(buttons,
				Messages.InstalledRuntimesBlock_addButton);
		fAddButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				addRuntime();
			}
		});
		fAddButton.setEnabled(true);

		fEditButton = createPushButton(buttons,
				Messages.InstalledRuntimesBlock_editButton);
		fEditButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				editRuntime();
			}
		});

		fRemoveButton = createPushButton(buttons,
				Messages.InstalledRuntimesBlock_removeButton);
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
		setRuntimes(Platform.getMongoRuntimeManager().getRuntimes());
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
	// MongoRuntime left = (MongoRuntime) e1;
	// MongoRuntime right = (MongoRuntime) e2;
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

	private void sortBySource() {
		tableViewer.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				MongoRuntime left = (MongoRuntime) e1;
				MongoRuntime right = (MongoRuntime) e2;
				return left.getSource().compareToIgnoreCase(right.getSource());
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
				if ((e1 instanceof MongoRuntime)
						&& (e2 instanceof MongoRuntime)) {
					MongoRuntime left = (MongoRuntime) e1;
					MongoRuntime right = (MongoRuntime) e2;
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
		if (selectionCount > 0
				&& selectionCount < tableViewer.getTable().getItemCount()) {
			Iterator<?> iterator = selection.iterator();
			while (iterator.hasNext()) {
				MongoRuntime install = (MongoRuntime) iterator.next();
				// if (install.isContributed()) {
				fRemoveButton.setEnabled(false);
				return;
				// }
			}
			fRemoveButton.setEnabled(true);
		} else {
			fRemoveButton.setEnabled(false);
		}
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

	protected void setRuntimes(List<MongoRuntime> vms) {
		runtimes.clear();
		for (MongoRuntime element : vms) {
			runtimes.add(element);
		}
		tableViewer.setInput(runtimes);
		// tableViewer.refresh();
	}

	public MongoRuntime[] getRuntimes() {
		return runtimes.toArray(new MongoRuntime[runtimes.size()]);
	}

	private void addRuntime() {
		AddRuntimeDialog dialog = new AddRuntimeDialog(fAddButton.getShell());
		// dialog.setTitle(Messages.AddRuntimeDialog_Add_Title);
		if (dialog.open() != Window.OK) {
			// return;
		}
	}

	public void runtimeAdded(MongoRuntime install) {
		runtimes.add(install);
		tableViewer.add(install);
		tableViewer.setSelection(new StructuredSelection(install), true);
	}

	public boolean isDuplicateName(String name) {
		for (int i = 0; i < runtimes.size(); i++) {
			MongoRuntime install = runtimes.get(i);
			if (install.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private void editRuntime() {
		IStructuredSelection selection = (IStructuredSelection) tableViewer
				.getSelection();
		MongoRuntime install = (MongoRuntime) selection.getFirstElement();
		if (install == null) {
			return;
		}
		// if (!install.isContributed())
		// {
		// // RuntimeDetailsDialog dialog = new
		// RuntimeDetailsDialog(getShell(), install);
		// // dialog.open();
		// // }
		// // else
		// // {
		// AddRuntimeDialog dialog = new AddRuntimeDialog(this, getShell(),
		// JAXPRuntime.getRuntimeTypesExclJREDefault(), install);
		// dialog.setTitle(Messages.AddRuntimeDialog_Edit_Title);
		// if (dialog.open() != Window.OK)
		// {
		// return;
		// }
		// // fillWithWorkspaceRuntimes();
		// tableViewer.refresh();
		// }
	}

	private void removeRuntimes() {
		IStructuredSelection selection = (IStructuredSelection) tableViewer
				.getSelection();
		MongoRuntime[] vms = new MongoRuntime[selection.size()];
		Iterator<?> iter = selection.iterator();
		int i = 0;
		while (iter.hasNext()) {
			vms[i] = (MongoRuntime) iter.next();
			i++;
		}
		removeRuntimes(vms);
	}

	public void removeRuntimes(MongoRuntime[] theInstalls) {
		IStructuredSelection prev = (IStructuredSelection) getSelection();
		for (MongoRuntime element : theInstalls) {
			runtimes.remove(element);
		}
		tableViewer.refresh();
		IStructuredSelection curr = (IStructuredSelection) getSelection();
		if (!curr.equals(prev)) {
			MongoRuntime[] installs = getRuntimes();
			if (curr.size() == 0 && installs.length == 1) {
				// pick a default install automatically
				setSelection(new StructuredSelection(installs[0]));
			} else {
				fireSelectionChanged();
			}
		}
	}

	public void setCheckedInstall(MongoRuntime install) {
		if (install == null) {
			setSelection(new StructuredSelection());
		} else {
			setSelection(new StructuredSelection(install));
		}
	}

	public MongoRuntime getCheckedInstall() {
		Object[] objects = tableViewer.getCheckedElements();
		if (objects.length == 0) {
			return null;
		}
		return (MongoRuntime) objects[0];
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

	private static class VMLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof MongoRuntime) {
				MongoRuntime install = (MongoRuntime) element;
				switch (columnIndex) {
				case 0:
					return install.getName();
				case 1:
					return install.getSource();
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
