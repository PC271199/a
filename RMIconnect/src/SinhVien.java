import java.io.Serializable;


public class SinhVien implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ma;
	private String name;
	private String lop;
	private float diem;
	public SinhVien() {
	}
	public SinhVien(int ma, String name, String lop, float diem) {
		this.ma = ma;
		this.name = name;
		this.lop = lop;
		this.diem = diem;
	}

	public int getMa() {
		return ma;
	}

	public void setMa(int ma) {
		this.ma = ma;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLop() {
		return lop;
	}

	public void setLop(String lop) {
		this.lop = lop;
	}

	public float getDiem() {
		return diem;
	}

	public void setDiem(float diem) {
		this.diem = diem;
	}
	
	public String toString() {
        return ma + " " + name + " " + lop + " " +diem;
    }
}
