package fr.opensagres.mongodb.ide.core;

import java.util.List;

import fr.opensagres.mongodb.ide.core.model.MongoRuntime;

public interface IMongoRuntimeManager extends ISettingsManager {

	List<MongoRuntime> getRuntimes();

	void addRuntime(MongoRuntime mongoRuntime) throws Exception;

	void removeRuntime(MongoRuntime mongoRuntime) throws Exception;

	void clear();

	void setRuntimes(MongoRuntime[] runtimes) throws Exception;

	MongoRuntime findRuntime(String runtimeId);
}
