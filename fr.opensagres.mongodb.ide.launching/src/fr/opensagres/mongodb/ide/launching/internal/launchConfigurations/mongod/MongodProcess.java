package fr.opensagres.mongodb.ide.launching.internal.launchConfigurations.mongod;

import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.RuntimeProcess;

import com.mongodb.tools.driver.MongoDriverHelper;


public class MongodProcess extends RuntimeProcess {

	public MongodProcess(ILaunch launch, Process process, String name,
			Map attributes) {
		super(launch, process, name, attributes);
	}

	@Override
	public void terminate() throws DebugException {
		String host = "localhost";
		Integer port = null;
		MongoDriverHelper.stopMongoServerWithoutError(host, port);
		super.terminate();
	}

}
