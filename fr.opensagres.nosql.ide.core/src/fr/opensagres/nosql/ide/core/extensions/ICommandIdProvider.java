package fr.opensagres.nosql.ide.core.extensions;

public interface ICommandIdProvider {

	public static final int OPEN_EDITOR = 1;

	String getCommmandId(int type, Object element);

}
