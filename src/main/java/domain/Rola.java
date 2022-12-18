package domain;

public class Rola {

	int kodea;

	String mota;

	public Rola() {
	};

	public Rola(int kodea, String mota) {

		this.kodea = kodea;

		this.mota = mota;
	}

	public int getKodea() {
		return kodea;
	}

	public void setKodea(int kodea) {
		this.kodea = kodea;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public String toString() {
		return this.mota;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + kodea;
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
		Rola other = (Rola) obj;
		if (kodea != other.kodea)
			return false;
		return true;
	}

}
