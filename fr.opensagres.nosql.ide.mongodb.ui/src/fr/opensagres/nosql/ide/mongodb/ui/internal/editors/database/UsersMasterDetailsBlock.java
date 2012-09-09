package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.database;

import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.mongodb.DBObject;

import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.document.DBObjectKeyColumnLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.document.DBObjectValueColumnLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.internal.viewers.user.DBUserLabelProvider;
import fr.opensagres.nosql.ide.mongodb.ui.singlesourcing.SingleSourcingUtils;
import fr.opensagres.nosql.ide.ui.editors.AbstractMasterDetailsBlock;
import fr.opensagres.nosql.ide.ui.viewers.ViewerHelper;

public class UsersMasterDetailsBlock extends AbstractMasterDetailsBlock {

	private static final Integer ADD_BUTTON_INDEX = 1;
	private static final Integer REMOVE_BUTTON_INDEX = 2;

	private UserDetailsPage userDetailsPage;
	private TableViewer viewer;
	private Button removeButton;

	public UsersMasterDetailsBlock(UsersPage usersPage) {
		super(usersPage);
		this.userDetailsPage = new UserDetailsPage();
	}

	@Override
	protected void createMasterPart(final IManagedForm managedForm,
			Composite parent) {

		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setText(Messages.UsersEditor_UsersPage_UsersMasterDetailsBlock_title); //$NON-NLS-1$
		section.setDescription(Messages.UsersEditor_UsersPage_UsersMasterDetailsBlock_desc); //$NON-NLS-1$
		section.marginWidth = 10;
		section.marginHeight = 5;

		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);

		Table usersTable = toolkit.createTable(client, SWT.MULTI);
		usersTable.setHeaderVisible(true);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		usersTable.setLayoutData(gd);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, client);

		createButtons(toolkit, client);

		section.setClient(client);

		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TableViewer(usersTable);
		createColumns(viewer);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				if (selection.size() == 1) {
					managedForm.fireSelectionChanged(spart,
							event.getSelection());
				}
				removeButton.setEnabled(true);
			}
		});
//		viewer.setContentProvider(DBUserContentProvider.getInstance());
		viewer.setLabelProvider(DBUserLabelProvider.getInstance());

		init();
	}

	private void createButtons(FormToolkit toolkit, Composite parent) {
		GridData gd;
		Composite buttonsContainer = toolkit.createComposite(parent);
		gd = new GridData(GridData.FILL_VERTICAL);
		buttonsContainer.setLayoutData(gd);
		buttonsContainer.setLayout(createButtonsLayout());

		SelectionAdapter listener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.widget.getData() == ADD_BUTTON_INDEX) {
					handleAddButton();
				} else if (e.widget.getData() == REMOVE_BUTTON_INDEX) {
					handleRemoveButton();
				}
			}
		};

		Button addButton = toolkit.createButton(buttonsContainer,
				Messages.addButton_label, SWT.PUSH); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		addButton.setData(ADD_BUTTON_INDEX);
		addButton.setLayoutData(gd);
		addButton.setEnabled(true);
		addButton.addSelectionListener(listener);

		removeButton = toolkit.createButton(buttonsContainer,
				Messages.removeButton_label, SWT.PUSH); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		removeButton.setData(REMOVE_BUTTON_INDEX);
		removeButton.setLayoutData(gd);
		removeButton.setEnabled(false);
		removeButton.addSelectionListener(listener);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit,
				buttonsContainer);
	}

	protected void handleAddButton() {
		// DBObject user = new DB();
		// user.setLabel("New user");
		// getUsers().add(user);
		// viewer.add(user);
		// viewer.setSelection(new StructuredSelection(user));
	}

	protected void handleRemoveButton() {
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		if (!selection.isEmpty()) {
			DBObject user = null;
			Object[] users = selection.toArray();
			for (int i = 0; i < users.length; i++) {
				user = (DBObject) users[i];
				// getUsers().remove(user);
				viewer.remove(user);
			}
			viewer.refresh();
		}
	}

	protected GridLayout createButtonsLayout() {
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		return layout;
	}

	// @Override
	// public void onBind(DataBindingContext dataBindingContext) {
	// Set<Hobby> users = getUsers();
	// viewer.setInput(users);
	// }

	private void init() {

		try {
			List<DBObject> users = ((DatabaseEditor) getEditor())
					.getModelObject().getUsers();
			viewer.setInput(users);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public IDetailsPage getPage(Object key) {
		return userDetailsPage;
	}

	public Object getPageKey(Object object) {
		return null;
	}

	private static void createColumns(final TableViewer viewer) {

		// First column is for the username
		TableViewerColumn col = ViewerHelper.createColumn(viewer,
				Messages.columnUsername, 150,
				DBObjectKeyColumnLabelProvider.getInstance());
		// col.getColumn().addSelectionListener(
		// new SortTableColumnSelectionListener("name"));

		// Second column is for the readOnly
		col = ViewerHelper.createColumn(viewer, Messages.columnReadonly, 100,
				DBObjectValueColumnLabelProvider.getInstance());
		// col.getColumn().addSelectionListener(
		// new SortTableColumnSelectionListener("address.name"));

	}

}
