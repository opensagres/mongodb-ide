importClass(com.mongodb.rhino.BSON, com.mongodb.rhino.JSON, java.lang.System)



function initDatabase() {
	db.createCollection = function(name) {
		return new Collection(name, db.database);
	}
	
	db.dropDatabase = function() {
		db.database.dropDatabase();
		db = null;
	}
	
}

function print(s) {
	console.info(s);
}


function printjson(obj) {
	print(BSON.to(obj))
}

function connect(url) {
	manager.connect(url, null)
}

function use(databaseName) {
	databaseSwitcher.switchDatabase(databaseName);
	initDatabase();
	print("switched to db " + databaseName);
}

function DB (name) {
	this.database = connection.getDB(name);
	var colNames = this.database.getCollectionNames().iterator();
	while (colNames.hasNext()) {
		var colName = colNames.next(); 
		this[colName] = new Collection(colName, this.database);
	}
}


function Collection (name, db) {
	this.db = db;
	this.name = name;
	this.collection = db.getCollection(name);
	
	this.find = function(query, fields) {
		if (query) {
			if (fields !== undefined) {
				return new Cursor(this.collection.find(BSON.to(query), BSON.to(fields)))
			}
			else {
				return new Cursor(this.collection.find(BSON.to(query)))
			}
		}
		else {
			return new Cursor(this.collection.find())
		}
		
	}

	this.count = function(query) {
		if (query) {
			return this.collection.count(BSON.to(query))
		}
		else {
			return this.collection.count()
		}
	}
	
	this.findOne = function(query, fields) {
		if (query) {
			if (fields !== undefined) {
				return BSON.from(this.collection.findOne(BSON.to(query), BSON.to(fields)))
			}
			else {
				return BSON.from(this.collection.findOne(BSON.to(query)))
			}
		} else {
			return BSON.from(this.collection.findOne(BSON.to({})));
		}
	}
	
	
	this.save = function(doc) {
		this.collection.save(BSON.to(doc));
	}
	
	this.insert = function(doc) {
		this.collection.insert(BSON.to(doc));
	}
	
	this.update = function(query, update, multi) {
		this.collection.update(BSON.to(query), BSON.to(update), false, multi == true);
	}
	
	this.upsert = function(query, update, multi) {
		this.collection.update(BSON.to(query), BSON.to(update), true, multi == true);
	}
	
	this.remove = function(query, writeConcern) {
		if (query) {
			this.collection.remove(BSON.to(query))
		}
		else {
			this.collection.remove(BSON.to({}))
		}
	}
	
	this.drop = function() {
		this.collection.drop();
		delete this.db[this.name];
	}
	
	
	
	
	
}



function Cursor (cursor) {
	this.cursor = cursor;
	
	this.hasNext = function() {
		return this.cursor.hasNext();
	}
	
	this.next = function() {
		return BSON.from(this.cursor.next());
	}
	
	this.limit = function(n) {
		this.cursor.limit(n);
		return this;
	}

	this.skip = function(n) {
		this.cursor.skip(n);
		return this;
	}
	
	this.sort = function(orderBy) {
		this.cursor.sort(BSON.to(orderBy))
		return this;
	}
	
	this.count = function() {
		return this.cursor.count();
	}
	
	this.toArray = function() {
		var array = [];
		var index = 0;
		while (this.hasNext()) {
			var doc = this.next()
			array.push(doc)
		}
		return array
	}
	
	this.forEach = function(callback) {
		while (this.cursor.hasNext()) {
			callback(BSON.from(this.cursor.next()));
		}
	}
	
	
}

