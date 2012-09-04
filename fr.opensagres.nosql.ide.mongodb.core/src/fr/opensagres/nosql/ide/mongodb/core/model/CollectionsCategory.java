package fr.opensagres.nosql.ide.mongodb.core.model;

import java.util.Set;

import com.mongodb.DB;

import fr.opensagres.nosql.ide.core.model.AbstractCollectionsCategory;
import fr.opensagres.nosql.ide.mongodb.core.shell.MongoShellCommandManager;

public class CollectionsCategory extends AbstractCollectionsCategory {

	@Override
	protected void loadCollections() throws Exception {
		DB db = ((Database) getParent()).getDB();
		Set<String> names = MongoShellCommandManager.getInstance()
				.showCollections(getServer(), db);
		for (String name : names) {
			Collection collection = new Collection(name);
			collections.add(collection);
			super.addNode(collection);
		}
	}

}
