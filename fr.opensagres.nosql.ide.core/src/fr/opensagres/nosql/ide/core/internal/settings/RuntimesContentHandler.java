package fr.opensagres.nosql.ide.core.internal.settings;

import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import fr.opensagres.nosql.ide.core.Platform;
import fr.opensagres.nosql.ide.core.extensions.IRuntimeFactory;
import fr.opensagres.nosql.ide.core.extensions.IServerType;
import fr.opensagres.nosql.ide.core.internal.Trace;
import fr.opensagres.nosql.ide.core.model.IServerRuntime;
import fr.opensagres.nosql.ide.core.settings.RuntimesConstants;
import fr.opensagres.nosql.ide.core.settings.ServersConstants;
import fr.opensagres.nosql.ide.core.utils.StringUtils;

public class RuntimesContentHandler extends
		AbstractContentHandler<IServerRuntime> {

	public RuntimesContentHandler(Collection<IServerRuntime> list) {
		super(list);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (RuntimesConstants.RUNTIME_ELT.equals(localName)) {
			String serverTypeId = attributes
					.getValue(ServersConstants.SERVER_TYPE_ID_ATTR);
			if (!StringUtils.isEmpty(serverTypeId)) {
				IServerType serverType = Platform.getServerTypeRegistry()
						.getType(serverTypeId);
				if (serverType != null) {
					IRuntimeFactory factory = Platform
							.getRuntimeFactoryRegistry().getFactory(serverType);
					if (factory != null) {
						try {
							IServerRuntime runtime = factory.create(attributes);
							if (runtime != null) {
								super.list.add(runtime);
							}
						} catch (Exception e) {
							Trace.trace(Trace.STRING_SEVERE, e.getMessage());
						}
					}
				}
			}
		}
		super.startElement(uri, localName, qName, attributes);
	}
}
