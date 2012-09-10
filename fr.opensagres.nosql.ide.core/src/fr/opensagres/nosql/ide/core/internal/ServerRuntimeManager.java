package fr.opensagres.nosql.ide.core.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;

import fr.opensagres.nosql.ide.core.IServerRuntimeListener;
import fr.opensagres.nosql.ide.core.IServerRuntimeManager;
import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.internal.settings.RuntimesSettings;
import fr.opensagres.nosql.ide.core.model.IServerRuntime;
import fr.opensagres.nosql.ide.core.utils.StringUtils;

public class ServerRuntimeManager extends AbstractManager<IServerRuntime>
		implements IServerRuntimeManager {

	private static final IServerRuntimeManager INSTANCE = new ServerRuntimeManager();

	public static IServerRuntimeManager getInstance() {
		return INSTANCE;
	}

	private final ListenerList listeners;

	public ServerRuntimeManager() {
		super("runtmes.xml", RuntimesSettings.getInstance());
		this.listeners = new ListenerList();
	}

	public List<IServerRuntime> getRuntimes() {
		return this;
	}

	public Collection<IServerRuntime> getRuntimes(String serverTypeId) {
		IServerType serverType = Platform.getServerTypeRegistry().getType(
				serverTypeId);
		return getRuntimes(serverType);
	}

	public List<IServerRuntime> getRuntimes(IServerType serverType) {
		List<IServerRuntime> allRuntimes = getRuntimes();
		if (allRuntimes.isEmpty()) {
			return Collections.emptyList();
		}
		List<IServerRuntime> runtimes = new ArrayList<IServerRuntime>();
		for (IServerRuntime runtime : allRuntimes) {
			if (serverType.equals(runtime.getServerType())) {
				runtimes.add(runtime);
			}
		}
		return runtimes;
	}

	public void addRuntime(IServerRuntime runtime) throws Exception {
		addRuntime(runtime, true);
	}

	private void addRuntime(IServerRuntime runtime, boolean save)
			throws Exception {
		super.add(runtime);
		if (save) {
			super.save();
		}
		processListeners(runtime, true);
	}

	public void removeRuntime(IServerRuntime runtime) throws Exception {
		super.remove(runtime);
		super.save();
		processListeners(runtime, false);
		// runtime.dispose();
	}

	private void processListeners(IServerRuntime runtime, boolean start) {
		Object[] changeListeners = this.listeners.getListeners();
		if (changeListeners.length == 0)
			return;
		for (int i = 0; i < changeListeners.length; ++i) {
			final IServerRuntimeListener l = (IServerRuntimeListener) changeListeners[i];
			if (start) {
				l.runtimeAdded(runtime);
			} else {
				l.runtimeRemoved(runtime);
			}
		}
	}

	public void setRuntimes(IServerRuntime[] runtimes) throws Exception {
		clear();
		for (IServerRuntime runtime : runtimes) {
			addRuntime(runtime, false);
		}
		save();
	}

	public IServerRuntime findRuntime(String runtimeId) {
		if (StringUtils.isEmpty(runtimeId)) {
			return null;
		}
		for (IServerRuntime runtime : this) {
			if (runtimeId.equals(runtime.getId())) {
				return runtime;
			}
		}
		return null;
	}

	public void dispose() {

	}

}
