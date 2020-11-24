package server.Model;

public class ElectricalItem extends Item{
	private String powerType;
	
	public ElectricalItem(int id, String type, String name, int quanitiy, double price, int supplierId, String powerType) {
		super(id, type, name, quanitiy, price, supplierId);
		this.setPowerType(powerType);
	}

	public String getPowerType() {
		return powerType;
	}

	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}
	
	
}
