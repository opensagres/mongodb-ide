package fr.opensagres.nosql.ide.core;

public interface ISettingsManager {

	void initialize() throws Exception;

	void dispose();

	void load() throws Exception;

	public void save() throws Exception;
}
