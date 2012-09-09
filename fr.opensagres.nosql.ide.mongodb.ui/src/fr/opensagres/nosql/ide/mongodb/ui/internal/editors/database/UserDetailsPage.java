package fr.opensagres.nosql.ide.mongodb.ui.internal.editors.database;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.mongodb.ui.singlesourcing.SingleSourcingUtils;
import fr.opensagres.nosql.ide.ui.editors.AbstractDetailsPage;

public class UserDetailsPage extends AbstractDetailsPage {

	private Text userLabelText;

	public void createContents(Composite parent) {
	
		TableWrapLayout layout = new TableWrapLayout();
		layout.topMargin = 5;
		layout.leftMargin = 5;
		layout.rightMargin = 2;
		layout.bottomMargin = 2;
		parent.setLayout(layout);

		FormToolkit toolkit = getManagedForm().getToolkit();

		Section userDetailSection = toolkit.createSection(parent,
				Section.DESCRIPTION | Section.TITLE_BAR);
		userDetailSection.marginWidth = 10;
		userDetailSection
				.setText(Messages.UserEditor_UsersPage_UserDetailsPage_title); //$NON-NLS-1$
		userDetailSection
				.setDescription(Messages.UserEditor_UsersPage_UserDetailsPage_desc); //$NON-NLS-1$

		TableWrapData td = new TableWrapData(TableWrapData.FILL,
				TableWrapData.TOP);
		td.grabHorizontal = true;
		userDetailSection.setLayoutData(td);

		Composite client = toolkit.createComposite(userDetailSection);
		userDetailSection.setClient(client);

		// Create generic content
		createBodyContent(toolkit, client);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, client);
	}

	private void createBodyContent(FormToolkit toolkit, Composite parent) {
		GridLayout glayout = new GridLayout();
		glayout.numColumns = 2;
		parent.setLayout(glayout);

		// User label
		toolkit.createLabel(
				parent,
				Messages.UserEditor_UsersPage_UserDetailsPage_userLabel_label);
		userLabelText = toolkit.createText(parent, "", SWT.SINGLE);
		userLabelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

	}

	@Override
	protected void refresh(Object modelObject) {
		
	}
}
