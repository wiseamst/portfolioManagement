package series;

public class Performance {

	private String date;
	private float valeur;
	
	public Performance(String date, float valeur) {
		super();
		this.date = date;
		this.valeur = valeur;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getValeur() {
		return valeur;
	}

	public void setValeur(float valeur) {
		this.valeur = valeur;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + Float.floatToIntBits(valeur);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Performance other = (Performance) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Float.floatToIntBits(valeur) != Float.floatToIntBits(other.valeur))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Performance [date=" + date + ", valeur=" + valeur + "]";
	}
	
	
}
