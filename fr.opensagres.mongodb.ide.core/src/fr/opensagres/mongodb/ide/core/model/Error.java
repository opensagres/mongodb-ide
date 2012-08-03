package fr.opensagres.mongodb.ide.core.model;

public class Error extends TreeSimpleNode {

	private final Throwable exception;

	public Error(Throwable exception) {
		this.exception = exception;
	}

	public String getLabel() {
		return exception.toString();
	}

	public NodeType getType() {
		return NodeType.Error;
	}

	public String getId() {
		return null;
	}
	
	public String getName() {
		return null;
	}

}
