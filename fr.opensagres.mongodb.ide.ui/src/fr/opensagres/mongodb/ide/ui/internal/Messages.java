package fr.opensagres.mongodb.ide.ui.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "fr.opensagres.mongodb.ide.ui.internal.Messages";//$NON-NLS-1$

	// Buttons
	public static String addButton_label;
	public static String removeButton_label;
	public static String searchButton_label;

	// Select wizard
	public static String SelectServerWizardPage_title;
	public static String SelectServerWizardPage_desc;

	// ****************** New Server Wizard ******************
	public static String NewServerWizard_title;
	public static String NewServerWizardPage_title;
	public static String NewServerWizardPage_desc;
	public static String NewServerWizardPage_locationGroup_label;
	public static String NewServerWizardPage_mongoURI_label;
	public static String NewServerWizardPage_name_label;
	public static String NewServerWizardPage_host_label;
	public static String NewServerWizardPage_port_label;
	public static String NewServerWizardPage_authenticationGroup_label;
	public static String NewServerWizardPage_userName_label;
	public static String NewServerWizardPage_password_label;
	public static String NewServerWizardPage_databaseName_label;
	public static String NewServerWizardPage_mongoURI_validation_notValid;
	public static String NewServerWizardPage_name_validation_required;
	public static String NewServerWizardPage_host_validation_required;
	public static String NewServerWizardPage_port_validation_int;
	public static String NewServerWizardPage_runtime_label;

	// ****************** New Database Wizard ******************
	public static String NewDatabaseWizard_title;
	public static String NewDatabaseWizardPage_title;
	public static String NewDatabaseWizardPage_desc;
	public static String NewDatabaseWizardPage_openDatabaseEditorCheckbox_label;
	public static String NewDatabaseWizardPage_name_validation_exists;

	// ****************** New Collection Wizard ******************
	public static String NewCollectionWizard_title;
	public static String NewCollectionWizardPage_title;
	public static String NewCollectionWizardPage_desc;
	public static String NewCollectionWizardPage_openCollectionEditorCheckbox_label;

	// ****************** New Document Wizard ******************
	public static String NewDocumentWizard_title;
	public static String NewDocumentWizardPage_title;
	public static String NewDocumentWizardPage_desc;
	public static String NewDocumentWizardPage_openDocumentEditorCheckbox_label;

	// ****************** New Action ******************
	public static String NewServerAction_text;
	public static String NewServerAction_toolTipText;
	public static String NewDatabaseAction_text;
	public static String NewDatabaseAction_toolTipText;
	public static String NewCollectionAction_text;
	public static String NewCollectionAction_toolTipText;
	public static String NewDocumentAction_text;
	public static String NewDocumentAction_toolTipText;
	
	// ****************** RefreshAction ******************
	public static String RefreshAction_text;
	public static String RefreshAction_toolTipText;

	// ****************** DeleteAction ******************
	public static String DeleteAction_text;
	public static String DeleteAction_toolTipText;
	public static String DeleteAction_serverTitle;
	public static String DeleteAction_serverMessage;
	public static String DeleteAction_databaseTitle;
	public static String DeleteAction_databaseMessage;
	public static String DeleteAction_collectionTitle;
	public static String DeleteAction_collectionMessage;

	public static String actionNew;
	public static String actionStart;
	public static String actionStartToolTip;
	public static String actionStop;
	public static String actionStopToolTip;
	public static String actionRestart;
	public static String actionRestartToolTip;
	public static String actionUnlock;
	public static String actionUnlockToolTip;
	public static String actionOpen;

	// Editors
	public static String OverviewPage_title;
	public static String server_label;
	public static String database_label;
	public static String collection_label;
	public static String bucket_label;

	// Server editor
	public static String serverEditorOverviewGeneralSection;
	public static String serverEditorOverviewGeneralDescription;
	public static String serverEditorOverviewTimeoutSection;
	public static String serverEditorOverviewTimeoutDescription;
	public static String mongoURILabel;
	public static String serverNameLabel;
	public static String hostLabel;
	public static String portLabel;
	public static String databaseLabel;
	public static String document_label;

	// Editor Database
	public static String DatabaseEditor_OverviewPage_GeneralInfo_desc;
	public static String DatabaseEditor_OverviewPage_GeneralInfo_title;
	public static String DatabaseEditor_OverviewPage_Shell_desc;
	public static String DatabaseEditor_OverviewPage_Shell_title;
	public static String DatabaseEditor_OverviewPage_Stats_title;
	public static String DatabaseEditor_OverviewPage_Stats_desc;
	public static String DatabaseEditor_OverviewPage_Shell_connection;
	public static String StatsPage_title;
	public static String UsersPage_title;
	public static String IndexesPage_title;
	public static String UsersEditor_UsersPage_UsersMasterDetailsBlock_title;
	public static String UsersEditor_UsersPage_UsersMasterDetailsBlock_desc;
	public static String UserEditor_UsersPage_UserDetailsPage_title;
	public static String UserEditor_UsersPage_UserDetailsPage_desc;
	public static String UserEditor_UsersPage_UserDetailsPage_userLabel_label;
	public static String DatabaseEditor_OverviewPage_DatabaseContent_title;
	public static String DatabaseEditor_OverviewPage_DatabaseContent_content;

	// Collection Editor
	public static String CollectionEditor_OverviewPage_GeneralInfo_desc;
	public static String CollectionEditor_OverviewPage_GeneralInfo_title;
	public static String DocumentsPage_title;
	public static String CollectionEditor_OverviewPage_CollectionContent_title;
	public static String CollectionEditor_OverviewPage_CollectionContent_content;

	// Document Editor
	public static String DocumentEditor_OverviewPage_GeneralInfo_desc;
	public static String DocumentEditor_OverviewPage_GeneralInfo_title;
	public static String JSONTextPage_title;

	// GridFS Editor
	public static String GridFSEditor_OverviewPage_GeneralInfo_desc;
	public static String GridFSEditor_OverviewPage_GeneralInfo_title;
	public static String FilesPage_title;
	public static String GridFSEditor_FilesPage_Files_desc;
	public static String GridFSEditor_FilesPage_Files_title;

	// Columns
	public static String columnKey;
	public static String columnValue;
	public static String columnType;
	public static String columnUsername;
	public static String columnReadonly;

	public static String columnName;
	public static String columnSize;
	public static String columnCount;
	public static String columnStorage;
	public static String columnAvgObj;
	public static String columnPadding;
	public static String columnIndexSize;
	public static String columnFilename;
	public static String columnChunkSize;
	public static String columnUploaded;
	public static String columnContentType;
	public static String columnMD5;

	// Validation
	public static String server_validation_required;
	public static String server_validation_notConnected;
	public static String database_validation_required;
	public static String collection_validation_required;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

}
