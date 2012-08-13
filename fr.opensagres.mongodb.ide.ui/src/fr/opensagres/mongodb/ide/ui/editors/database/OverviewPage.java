package fr.opensagres.mongodb.ide.ui.editors.database;

import java.util.Collection;

import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.extensions.IShellRunnerType;
import fr.opensagres.mongodb.ide.core.model.Database;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.ui.FormLayoutFactory;
import fr.opensagres.mongodb.ide.ui.dialogs.StackTraceErrorDialog;
import fr.opensagres.mongodb.ide.ui.editors.AbstractToolbarFormPage;
import fr.opensagres.mongodb.ide.ui.editors.UIFieldsFactory;
import fr.opensagres.mongodb.ide.ui.internal.ImageResources;
import fr.opensagres.mongodb.ide.ui.internal.Messages;
import fr.opensagres.mongodb.ide.ui.singlesourcing.SingleSourcingUtils;

public class OverviewPage extends AbstractToolbarFormPage implements
		IHyperlinkListener {

	private static final String STOP_SHELL_IMG = "stop_shell";
	private static final String START_SHELL_IMG = "start_shell";
	public static final String ID = "overview";
	private Label serverLabel;
	private Text nameText;

	public OverviewPage(DatabaseEditor editor) {
		super(editor, ID, Messages.OverviewPage_title);
	}

	@Override
	protected Image getFormTitleImage() {
		return ImageResources.getImage(ImageResources.IMG_DATABASE_16);
	}

	@Override
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		Composite body = managedForm.getForm().getBody();
		body.setLayout(FormLayoutFactory.createFormTableWrapLayout(true, 2));

		Composite left = toolkit.createComposite(body);
		left.setLayout(FormLayoutFactory
				.createFormPaneTableWrapLayout(false, 1));
		left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		// General info section
		createGeneralInfoSection(left, toolkit);

		// right column
		Composite right = toolkit.createComposite(body);
		right.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false,
				1));
		right.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		// Content section
		createContentSection(toolkit, right);
		createShellSection(right, toolkit);

		initialize();
	}

	protected void createGeneralInfoSection(Composite left, FormToolkit toolkit) {
		Section section = toolkit.createSection(left, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setDescription(Messages.DatabaseEditor_OverviewPage_GeneralInfo_desc);
		section.setText(Messages.DatabaseEditor_OverviewPage_GeneralInfo_title);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		// glayout.horizontalSpacing = 10;
		glayout.numColumns = 2;
		sbody.setLayout(glayout);

		// Server name
		Database database = getDatabase();
		Server server = database.getParent();
		serverLabel = UIFieldsFactory.createServerField(sbody, toolkit, server);

		// Database name
		toolkit.createLabel(sbody, Messages.database_label);
		nameText = toolkit.createText(sbody, "", SWT.SINGLE);
		GridData gd_firstNameText = new GridData(GridData.FILL_HORIZONTAL);
		gd_firstNameText.widthHint = 150;
		nameText.setLayoutData(gd_firstNameText);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);

	}

	/**
	 * Create Mongo Console Shell section;
	 * 
	 * @param parent
	 * @param toolkit
	 */
	private void createShellSection(Composite parent, FormToolkit toolkit) {
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR);
		Database database = getDatabase();
		section.setDescription(NLS.bind(
				Messages.DatabaseEditor_OverviewPage_Shell_desc,
				database.getMongoConsoleCommand(false)));
		section.setText(Messages.DatabaseEditor_OverviewPage_Shell_title);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite container = createStaticSectionClient(toolkit, section);

		// List of start/stop of shell runners
		String runnersFormtext = generateShellRunnersFormtext();
		FormText text = createClient(container, runnersFormtext.toString(),
				toolkit, this);
		text.setImage(START_SHELL_IMG,
				ImageResources.getImage(ImageResources.IMG_ELCL_START_SHELL));
		text.setImage(STOP_SHELL_IMG,
				ImageResources.getImage(ImageResources.IMG_ELCL_STOP_SHELL));
		section.setClient(container);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, container);
	}

	private String generateShellRunnersFormtext() {
		StringBuilder runtimesFormtext = new StringBuilder("<form>");
		runtimesFormtext.append("<p>Shell runners:</p>");
		Collection<IShellRunnerType> runnerTypes = Platform
				.getShellRunnerRegistry().getRunners();
		for (IShellRunnerType runnerType : runnerTypes) {
			generate(runtimesFormtext, runnerType, true);
			if (runnerType.getRunner().canSupportStop()) {
				generate(runtimesFormtext, runnerType, false);
			}
		}
		runtimesFormtext.append("</form>");
		return runtimesFormtext.toString();
	}

	private void generate(StringBuilder runnersFormtext,
			IShellRunnerType runnerType, boolean start) {
		runnersFormtext.append("<li style=\"image\" value=\"");
		runnersFormtext.append(start ? START_SHELL_IMG : STOP_SHELL_IMG);
		runnersFormtext.append("\" bindent=\"5\" >");
		runnersFormtext.append("<a href=\"runner");
		runnersFormtext.append(start ? "_start_" : "_stop_");
		runnersFormtext.append(runnerType.getId());
		runnersFormtext.append("\" >");
		runnersFormtext.append(start ? runnerType.getStartName() : runnerType
				.getStopName());
		runnersFormtext.append("</a>: ");
		runnersFormtext.append(start ? runnerType.getStartDescription()
				: runnerType.getStopDescription());
		runnersFormtext.append("</li>");
	}

	private void createContentSection(FormToolkit toolkit, Composite parent) {
		Section section = toolkit.createSection(parent, Section.TITLE_BAR);
		section.setText(Messages.DatabaseEditor_OverviewPage_DatabaseContent_title);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		Composite container = createStaticSectionClient(toolkit, section);

		FormText text = createClient(container,
				Messages.DatabaseEditor_OverviewPage_DatabaseContent_content,
				toolkit, this);
		text.setImage("stats_page",
				ImageResources.getImage(ImageResources.IMG_STATS_16));
		text.setImage("users_page",
				ImageResources.getImage(ImageResources.IMG_USERS_16));
		text.setImage("indexes_page",
				ImageResources.getImage(ImageResources.IMG_INDEX_16));
		section.setClient(container);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);
	}

	private void initialize() {

		Database database = getDatabase();
		Server server = database.getParent();
		serverLabel.setText(server.getName());
		nameText.setText(database.getName());
	}

	private Database getDatabase() {
		return ((DatabaseEditor) getEditor()).getModelObject();
	}

	public void linkActivated(HyperlinkEvent e) {
		String href = (String) e.getHref();
		if (href.startsWith("runner_start_")) {
			String runnerId = href.substring("runner_start_".length(),
					href.length());
			try {
				Platform.getShellRunnerRegistry().getRunnerType(runnerId)
						.getRunner().startShell(getDatabase());
			} catch (Exception e1) {
				StackTraceErrorDialog.openError(getSite().getShell(), "",
						e1.getMessage(), e1);

			}

		} else if (href.startsWith("runner_stop_")) {
			String runnerId = href.substring("runner_stop_".length(),
					href.length());
			try {
				Platform.getShellRunnerRegistry().getRunnerType(runnerId)
						.getRunner().stopShell(getDatabase());
			} catch (Exception e1) {
				StackTraceErrorDialog.openError(getSite().getShell(), "",
						e1.getMessage(), e1);
			}
		} else {
			getEditor().setActivePage(href);
		}
	}

	public void linkEntered(HyperlinkEvent e) {
		// Do nothing
	}

	public void linkExited(HyperlinkEvent e) {
		// Do nothing
	}
}
