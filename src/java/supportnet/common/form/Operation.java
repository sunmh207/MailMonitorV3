package supportnet.common.form;

public enum Operation {

	eq("="), ge(">="), le("<="), lt("<"), gt(">"), like("like"), startWiths(
			"like"), endsWidth("like"), in("in"), defaultOP(""),bypass("pass"),like_nocase("like");

	private String name;

	Operation(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
