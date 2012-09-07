package fr.opensagres.nosql.ide.core.extensions;

import fr.opensagres.nosql.ide.core.model.ITreeSimpleNode;

public abstract class AbstractCommandIdProvider implements ICommandIdProvider {

	public String getCommmandId(Object element) {
		if (element instanceof ITreeSimpleNode) {
			return (getCommmandId((ITreeSimpleNode) element));
		}
		return null;
	}

	protected abstract String getCommmandId(ITreeSimpleNode element);
}
