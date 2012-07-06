package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongod;

import java.net.UnknownHostException;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.RuntimeProcess;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import fr.opensagres.mongodb.ide.core.Platform;
import fr.opensagres.mongodb.ide.core.utils.MongoHelper;

public class MongodProcess extends RuntimeProcess {

	public MongodProcess(ILaunch launch, Process process, String name,
			Map attributes) {
		super(launch, process, name, attributes);
	}

	@Override
	public void terminate() throws DebugException {
		String host = "localhost";
		Integer port = null;
		MongoHelper.stopMongoServerWithoutError(host, port);
		super.terminate();
	}

}
