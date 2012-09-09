package fr.opensagres.nosql.ide.core.extensions;

import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;

public abstract class AbstractCommandIdProvider implements ICommandIdProvider {

	public String getCommmandId(int type, Object element) {
		if (element instanceof ITreeSimpleNode) {
			return getCommmandId(type, (ITreeSimpleNode) element);
		}
		return null;
	}

	protected abstract String getCommmandId(int type, ITreeSimpleNode element);
}
