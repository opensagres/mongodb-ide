package fr.opensagres.nosql.ide.core.extensions;

public interface ICommandIdProviderRegistry extends IRegistry {

	String getCommandId(IServerType serverType, int type, Object element);

}
