package br.com.emersonluiz.location.model;

public enum Status {
	Online("online"), NoTransactions("noTransactions"), Offline("offline");
	
	private String status;
	
	Status(String status) {
		this.status = status;
	}

	String getStatus() {
		return this.status;
	}
}
