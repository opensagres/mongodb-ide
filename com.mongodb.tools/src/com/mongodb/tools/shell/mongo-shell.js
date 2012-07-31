importClass(com.mongodb.tools.shell.ShellCommandManager)

connect = function(url) {
	manager.connect(url, null, shellContext);
}

disconnect = function() {
	manager.disconnect(shellContext);
}

showDbs = function() {
	manager.showDbs(shellContext);
}

use = function(dabaseName) {
	manager.use(dabaseName, shellContext);
}

showCollections = function() {
	manager.showCollections(shellContext);
}
