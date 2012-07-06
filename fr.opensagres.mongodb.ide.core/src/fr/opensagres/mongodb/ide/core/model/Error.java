package fr.opensagres.mongodb.ide.core.model;

public class Error extends TreeSimpleNode {

	private final Throwable exception;

	public Error(Throwable exception) {
		this.exception = exception;
	}

	@Override
	public String getLabel() {
		return exception.toString();
	}

	@Override
	public NodeType getType() {
		return NodeType.Error;
	}

}
