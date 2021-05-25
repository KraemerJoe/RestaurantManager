package fr.ul.miage.gl.restaurant.menus;

public interface InterfaceMenu {
	void title();

	void footer();

	void show();

	void initMenuItems();

	void executeChoice(int choice);
}
