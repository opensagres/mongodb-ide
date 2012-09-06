package fr.opensagres.nosql.ide.mongodb.ui.dialogs;

import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;

import com.mongodb.tools.process.InvalidMongoHomeDirException;
import com.mongodb.tools.process.MongoDBTools;
import com.mongodb.tools.process.MongoProcessFiles;
import com.mongodb.tools.process.mongod.MongodTools;

import fr.opensagres.nosql.ide.core.model.IServerRuntime;
import fr.opensagres.nosql.ide.core.utils.StringUtils;
import fr.opensagres.nosql.ide.mongodb.core.model.MongoServerRuntime;
import fr.opensagres.nosql.ide.mongodb.ui.internal.Messages;
import fr.opensagres.nosql.ide.ui.dialogs.AbstractRuntimeDialog;

public class MongoRuntimeDialog extends
		AbstractRuntimeDialog<MongoProcessFiles> {

	public MongoRuntimeDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected MongoProcessFiles validateInstallDir() {
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
	protected IServerRuntime createRuntime(String name, String installDir)
			throws Exception {
		return new MongoServerRuntime(name, installDir);
	}

	@Override
	protected String getGeneratedName(MongoProcessFiles files) throws Exception {
		return MongodTools.getDBVersion(files.getMongodFile());
	}
}
