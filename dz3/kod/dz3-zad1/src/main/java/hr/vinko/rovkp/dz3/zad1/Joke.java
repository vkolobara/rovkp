package hr.vinko.rovkp.dz3.zad1;

public class Joke {

	private String id;
	private String text;

	public Joke(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return "Joke [id=" + id + ", text=" + text + "]";
	}

	
	
}
