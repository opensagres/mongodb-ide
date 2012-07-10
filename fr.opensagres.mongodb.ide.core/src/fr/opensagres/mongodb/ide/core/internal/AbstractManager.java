package fr.opensagres.mongodb.ide.core.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;

import fr.opensagres.mongodb.ide.core.internal.settings.AbstractSettings;

public abstract class AbstractManager<T> extends ArrayList<T> {

	private final AbstractSettings<T> settings;
	private final File settingsFile;

	public AbstractManager(String settingsFileName, AbstractSettings<T> settings) {
		this.settingsFile = new File(Activator.getDefault().getStateLocation()
				.toFile(), settingsFileName);
		this.settingsFile.getParentFile().mkdirs();
		this.settings = settings;
	}

	public void initialize() throws Exception {
		if (settingsFile.exists()) {
			load();
		}
	}

	public void load() throws Exception {
		super.clear();
		InputStream stream = new FileInputStream(settingsFile);
		settings.load(stream, this);
	}

	public void save() throws Exception {
		Writer stream = new FileWriter(settingsFile);
		settings.save(this, stream);
	}

}
