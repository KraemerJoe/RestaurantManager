package fr.ul.miage.gl.restaurant.menus;

public class ItemMenu {
	
	private String description;
	private String detail;
	
	public ItemMenu(String description, String detail) {
		super();
		this.description = description;
		this.detail = detail;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
}


