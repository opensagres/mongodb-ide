package fr.opensagres.nosql.ide.core.extensions;

public interface ICommandIdProvider {

	public static final int OPEN_EDITOR = 1;
	public static final int OPEN_NEW_WIZARD = 2;

	String getCommmandId(int type, Object element);

}
