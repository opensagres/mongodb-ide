package fr.opensagres.mongodb.ide.core.internal;

import java.util.List;

import org.eclipse.core.runtime.ListenerList;

import fr.opensagres.mongodb.ide.core.IMongoRuntimeManager;
import fr.opensagres.mongodb.ide.core.IRuntimeListener;
import fr.opensagres.mongodb.ide.core.internal.settings.RuntimesSettings;
import fr.opensagres.mongodb.ide.core.model.MongoRuntime;
import fr.opensagres.mongodb.ide.core.model.Server;
import fr.opensagres.mongodb.ide.core.utils.StringUtils;

public class MongoRuntimeManager extends AbstractManager<MongoRuntime>
		implements IMongoRuntimeManager {

	private final ListenerList listeners;

	public MongoRuntimeManager() {
		super("runtmes.xml", RuntimesSettings.getInstance());
		this.listeners = new ListenerList();
	}

	public List<MongoRuntime> getRuntimes() {
		return this;
	}

	public void addRuntime(MongoRuntime runtime) throws Exception {
		addRuntime(runtime, true);
	}

	private void addRuntime(MongoRuntime runtime, boolean save)
			throws Exception {
		super.add(runtime);
		if (save) {
			super.save();
		}
		processListeners(runtime, true);
	}

	public void removeRuntime(MongoRuntime runtime) throws Exception {
		super.remove(runtime);
		super.save();
		processListeners(runtime, false);
		// runtime.dispose();
	}

	private void processListeners(MongoRuntime runtime, boolean start) {
		Object[] changeListeners = this.listeners.getListeners();
		if (changeListeners.length == 0)
			return;
		for (int i = 0; i < changeListeners.length; ++i) {
			final IRuntimeListener l = (IRuntimeListener) changeListeners[i];
			if (start) {
				l.runtimeAdded(runtime);
			} else {
				l.runtimeRemoved(runtime);
			}
		}
	}

	public void setRuntimes(MongoRuntime[] runtimes) throws Exception {
		clear();
		for (MongoRuntime runtime : runtimes) {
			addRuntime(runtime, false);
		}
		save();
	}

	public MongoRuntime findRuntime(String runtimeId) {
		if (StringUtils.isEmpty(runtimeId)) {
			return null;
		}
		for (MongoRuntime runtime : this) {
			if (runtimeId.equals(runtime.getId())) {
				return runtime;
			}
		}
		return null;
	}
}
