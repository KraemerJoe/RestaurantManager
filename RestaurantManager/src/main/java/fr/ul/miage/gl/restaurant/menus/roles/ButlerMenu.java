package fr.ul.miage.gl.restaurant.menus.roles;
 
import java.util.ArrayList;
 
import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;
import fr.ul.miage.gl.restaurant.pojo.staff.Staff;
import fr.ul.miage.gl.restaurant.pojo.tables.TableAssignment;
import fr.ul.miage.gl.restaurant.pojo.tables.TableRestaurant;
import fr.ul.miage.gl.restaurant.util.MenuUtil;
 
public class ButlerMenu extends Menu {
 
    public static ButlerMenu instance;
 
    public ButlerMenu() {
        super("Butler");
        instance = this;
    }
 
    @Override
    public void initMenuItems() {
        itemList.add(new ItemMenu("Assign a table", "Set tables assigned to waiters"));
    }
 
    @Override
    public void executeChoice(int choice) {
        switch (choice) {
        case 1:
            ArrayList<Staff> waiters = new ArrayList<Staff>();
            waiters.addAll(Staff.find.findWaiters());
 
            int compteur = 0;
            for (Staff staff : waiters) {
                System.out.println("[" + compteur + "]" + " " + staff.getRole() + " " + staff.getSurname() + " "
                        + staff.getName());
                compteur++;
            }
 
            if (waiters.size() <= 0) {
                System.out.println("There is no staf to manage.");
                return;
            }
 
            int waiter = MenuUtil.askForPositiveInt("Which staff do you want to manage ?");
 
            if (waiters.size() <= waiter || waiters.get(waiter) == null) {
                System.out.println("This staff doesn't exist.");
                return;
            } else {
                Staff staff = waiters.get(waiter);
                ArrayList<TableAssignment> assignements = new ArrayList<TableAssignment>();
                assignements.addAll(TableAssignment.find.assignmentByStaffId(staff.getStaff_id()));
 
                System.out.println("WAITER #" + staff.getStaff_id() + " (" + staff.getLogin() + ") is assigned to :");
                for (TableAssignment assignments : assignements) {
                    System.out.println(" > TABLE #" + assignments.getTable().getTable_id() + " | Floor: "
                            + assignments.getTable().getFloor());
                }
 
                int yesOrNoAssign = MenuUtil.askForYesOrNo("Do you want to assign a table to the waiter ?");
                if (yesOrNoAssign == 1) {
                    ArrayList<TableRestaurant> tables = new ArrayList<TableRestaurant>();
                    tables.addAll(TableRestaurant.find.all());
                    
                    int c2 = 0;
                    for (TableRestaurant tableRestaurant : tables) {
                        System.out.println("[" + c2 + "]" + "> TABLE #" + tableRestaurant.getTable_id() + " | Floor: "
                                + tableRestaurant.getFloor());
                        c2++;
                    }
                    
                    if (tables.size() <= 0) {
                        System.err.println("There is no table to assign.");
                        return;
                    }
                    
                    int table = MenuUtil.askForPositiveInt("Which table do you want to add ?");
                    
                    if (tables.size() <= table || tables.get(table) == null) {
                        System.err.println("This table doesn't exist.");
                        return;
                    } else {
                        if(TableAssignment.find.isAssigned(tables.get(table), staff)) {
                            System.err.println("This table is already assigned to the waiter.");
                        }else {
                            TableAssignment assign = new TableAssignment(staff, tables.get(table));
                            assign.save();
                            System.out.println("The table was added from the waiter !");
                        }
 
                    }
                }
 
                int yesOrNoRemove = MenuUtil.askForYesOrNo("Do you want to remove a table to the waiter ?");
                if (yesOrNoRemove == 1) {
                    int c3 = 0;
                    for (TableAssignment ta : assignements) {
                        System.out.println("[" + c3 + "]" + "> TABLE #" + ta.getTable().getTable_id() + " | Floor: "
                                + ta.getTable().getFloor());
                        c3++;
                    }
 
                    if (assignements.size() <= 0) {
                        System.err.println("There is no table to unassign.");
                        return;
                    }
 
                    int table = MenuUtil.askForPositiveInt("Which table do you want to remove ?");
 
                    if (assignements.size() <= table || assignements.get(table) == null) {
                        System.err.println("This table doesn't exist.");
                        return;
                    } else {
                        TableAssignment.find.unassign(assignements.get(table).getTable(), staff);
                        System.out.println("The table was removed from the waiter !");
                    }
                }
            }
 
            break;
        }
    }
 
    public static ButlerMenu getInstance() {
        if (instance == null)
            instance = new ButlerMenu();
        return instance;
    }
 
    public static void setInstance(ButlerMenu instance) {
        ButlerMenu.instance = instance;
    }
 
}
 