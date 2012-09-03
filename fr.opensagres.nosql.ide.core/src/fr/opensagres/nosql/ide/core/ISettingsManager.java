package fr.opensagres.nosql.ide.core;


public interface ISettingsManager {

	void initialize() throws Exception;
	
	void load() throws Exception;

	public void save() throws Exception;
}
