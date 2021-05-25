package fr.ul.miage.gl.restaurant.menus.roles;
 
import java.util.ArrayList;

import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.menus.roles.director.DirectorStatsMenu;
import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;
import fr.ul.miage.gl.restaurant.pojo.staff.Staff;
import fr.ul.miage.gl.restaurant.pojo.staff.enums.EnumRoles;
import fr.ul.miage.gl.restaurant.util.MenuUtil;
 
public class DirectorMenu extends Menu {
 
    public static DirectorMenu instance;
 
    public DirectorMenu() {
        super("Director");
        instance = this;
    }
 
    @Override
    public void initMenuItems() {
        itemList.add(new ItemMenu("Stocks", "See all current stocks"));
        itemList.add(new ItemMenu("Create a staff", "Add a new staff to the team"));
        itemList.add(new ItemMenu("Stats","Access to director's stats"));
    }
 
    @Override
    public void executeChoice(int choice) {
        switch (choice) {
        case 1:
            ArrayList<RawMaterial> materials = new ArrayList<RawMaterial>();
            materials.addAll(RawMaterial.find.all());
            for (RawMaterial rawMaterial : materials) {
                System.out.println("- " + rawMaterial.getName() + " [" + rawMaterial.getStock() + "]");
            }
 
            break;
        case 2:
            
            
            String name = MenuUtil.askForString("Enter the name of the staff.");
            String surname = MenuUtil.askForString("Enter the surname of the staff.");
            String login = MenuUtil.askForString("Enter the login of the staff.");
            String password = MenuUtil.askForString("Enter the password of the staff.");
            
            int compteur = 0;
            for (EnumRoles role : EnumRoles.values()) {
                System.out.println("[" + compteur + "]" + " " + role.toString());
                compteur++;
            }
            
            int roleToChoose = MenuUtil.askForPositiveInt("Which role do you want for this staff ?");
            EnumRoles role = null;
            
            if(EnumRoles.values().length <= roleToChoose || EnumRoles.values()[roleToChoose] == null) {
                System.out.println("This role doesn't exist.");
                return;
            }else {
                role = EnumRoles.values()[roleToChoose];
            }
            
            System.out.println("You are about to create a staff with: ");
            System.out.println(" > NAME: " + name);
            System.out.println(" > SURNAME: " + surname);
            System.out.println(" > LOGIN: " + login);
            System.out.println(" > PASSWORD: " + password);
            System.out.println(" > ROLE: " + role);
            
            int yesOrNo = MenuUtil.askForYesOrNo("Do you confirm the creation ?");
            
            if(yesOrNo == 1) {
                Staff staff = new Staff(name,surname,login,password,role);
                staff.save();
                System.out.println("The creation of the staff is a success.");
            }else {
                System.err.println("The creation was cancelled.");
            }
        case 3:
        	DirectorStatsMenu.getInstance().show();
        	break;
        default:
            break;
 
        }
 
    }
 
    public static DirectorMenu getInstance() {
        if (instance == null)
            instance = new DirectorMenu();
        return instance;
    }
 
    public static void setInstance(DirectorMenu instance) {
        DirectorMenu.instance = instance;
    }
 
}