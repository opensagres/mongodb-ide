package fr.opensagres.mongodb.ide.ui.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import fr.opensagres.mongodb.ide.ui.internal.Activator;

public class EditorPagesRegistry extends AbstractRegistry {

	private static final String PAGE_ELT = "page";
	private static final String EDITOR_ID_ATTR = "editorId";
	private static final String TITLE_ATTR = "title";

	private static final String EDITOR_PAGES_EXTENSION_POINT = "editorPages";
	private static final EditorPagesRegistry INSTANCE = new EditorPagesRegistry();

	private Map<String, List<EditorPageDescriptor>> descriptors = new HashMap<String, List<EditorPageDescriptor>>();

	public static EditorPagesRegistry getRegistry() {
		return INSTANCE;
	}

	public List<EditorPageDescriptor> getDescriptors(String editorId) {
		if (editorId == null) {
			throw new IllegalArgumentException();
		}
		loadRegistryIfNedded();
		return descriptors.get(editorId);
	}

	@Override
	protected void handleExtensionDelta(IExtensionDelta delta) {
		if (delta.getKind() == IExtensionDelta.ADDED) {
			IConfigurationElement[] cf = delta.getExtension()
					.getConfigurationElements();
			parsePages(cf);
		} else {
			// TODO : remove references
		}

	}

	protected synchronized void loadRegistry() {
		if (isRegistryIntialized()) {
			return;
		}
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		if (registry != null) {
			IConfigurationElement[] cf = registry.getConfigurationElementsFor(
					getPluginId(), getExtensionPoint());
			parsePages(cf);
		}
	}

	private void parsePages(IConfigurationElement[] cf) {
		for (IConfigurationElement ce : cf) {
			String editorId = null;
			String pageId = null;
			String title = null;
			// String title = null;
			if (PAGE_ELT.equals(ce.getName())) {
				pageId = ce.getAttribute(ID_ATTR);
				editorId = ce.getAttribute(EDITOR_ID_ATTR);
				title = ce.getAttribute(TITLE_ATTR);
				EditorPageDescriptor descriptor = new EditorPageDescriptor(
						pageId, title, ce);
				List<EditorPageDescriptor> editorDescriptors = descriptors
						.get(editorId);
				if (editorDescriptors == null) {
					editorDescriptors = new ArrayList<EditorPageDescriptor>();
					descriptors.put(editorId, editorDescriptors);
				}
				editorDescriptors.add(descriptor);
			}
		}

	}

	@Override
	protected String getPluginId() {
		return Activator.PLUGIN_ID;
	}

	@Override
	protected String getExtensionPoint() {
		return EDITOR_PAGES_EXTENSION_POINT;
	}

}
