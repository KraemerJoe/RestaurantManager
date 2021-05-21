package fr.ul.miage.gl.restaurant.menus.roles;
 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
 
import fr.ul.miage.gl.restaurant.menus.ItemMenu;
import fr.ul.miage.gl.restaurant.menus.Menu;
import fr.ul.miage.gl.restaurant.pojo.dishes.Category;
import fr.ul.miage.gl.restaurant.pojo.dishes.CompositionDish;
import fr.ul.miage.gl.restaurant.pojo.dishes.Dish;
import fr.ul.miage.gl.restaurant.pojo.dishes.PreparationTimeDish;
import fr.ul.miage.gl.restaurant.pojo.dishes.RawMaterial;
import fr.ul.miage.gl.restaurant.pojo.orders.SessionOrder;
import fr.ul.miage.gl.restaurant.util.MenuUtil;
 
public class CookerMenu extends Menu {
 
    public static CookerMenu instance;
 
    public CookerMenu() {
        super("Cooker");
        instance = this;
    }
 
    @Override
    public void initMenuItems() {
        itemList.add(new ItemMenu("Incoming orders", "See incoming orders"));
        itemList.add(new ItemMenu("Ready to be served", "Set a dish to ready to be served"));
        itemList.add(new ItemMenu("Create a new category", "Add a new category of dish to the menu"));
        itemList.add(new ItemMenu("Create a new dish", "Use raw materials to create a new dish"));
        itemList.add(new ItemMenu("Average preparation time", "Consult the average preparation time of a dish"));
    }
 
    @Override
    public void executeChoice(int choice) {
        switch (choice) {
        case 1:
            List<SessionOrder> orders = new ArrayList<SessionOrder>();
            orders = SessionOrder.find.pendingOrdersWithChildFirst();
 
            System.out.println("List of pending orders: ");
            for (SessionOrder sessionOrder : orders) {
                System.out.println("- " + (sessionOrder.isChild() ? "[CHILD] " : "") + "[TABLE #"
                        + sessionOrder.getOrder().getSessionClient().getTable_id().getTable_id() + "] "
                        + sessionOrder.getDish().getName() + " | " + sessionOrder.getDate_creation());
            }
            break;
        case 2:
            List<SessionOrder> ordersPending = new ArrayList<SessionOrder>();
            ordersPending = SessionOrder.find.pendingOrdersWithChildFirst();
            int compteur = 0;
            for (SessionOrder o : ordersPending) {
                System.out.println("[" + compteur + "] " + "[TABLE #"
                        + o.getOrder().getSessionClient().getTable_id().getTable_id() + "] " + o.getDish().getName());
                compteur++;
            }
 
            if (ordersPending.size() <= 0) {
                System.out.println("There is no dish.");
                return;
            }
 
            int dishToServe = MenuUtil.askForPositiveInt("Which dish is ready to be served ?");
 
            if (ordersPending.size() <= dishToServe || ordersPending.get(dishToServe) == null) {
                System.out.println("This session of dish doesn't exist.");
                return;
            } else {
                SessionOrder order = ordersPending.get(dishToServe);
                order.setReadyToServe();
                order.save();
                System.out.println("Waiters has been informed the dish is ready to be served !");
            }
            break;
        case 3:
            String category = MenuUtil.askForOneWordString("Enter the new category name, one word only, no space.")
                    .toUpperCase();
 
            int yesOrNo = MenuUtil.askForYesOrNo("Do you confirm the category: " + category + " ?");
 
            if (yesOrNo == 1) {
                Category cat = new Category(category);
                cat.save();
            } else {
                System.err.println("The creation of the category was cancelled.");
            }
            break;
        case 4:
            System.out.println("To create a new dish, choose the category of the dish.");
            Category cat = Category.askForACategory();
 
            if (cat == null) {
                System.err.println("This category doesn't exist.");
                return;
            }
 
            String dishName = MenuUtil.askForString("What is the name of the dish ?");
            double price = MenuUtil.askForPositiveDouble("What is the price of the dish ?");
            
            boolean finish = false;
            boolean error = false;
 
            ArrayList<CompositionDish> compositions = new ArrayList<CompositionDish>();
            
            Dish dishToCreate = new Dish(cat, dishName,price);
            
            while (!finish) {
                ArrayList<RawMaterial> materials = new ArrayList<RawMaterial>();
                materials.addAll(RawMaterial.find.all());
 
                int c = 0;
                for (RawMaterial raw : materials) {
                    System.out.println("[" + c + "] " + raw.getName());
                    c++;
                }
 
                if (materials.size() <= 0) {
                    System.err.println("No raw materials to create a dish.");
                    finish = true;
                    error = true;
                }
                int rawId = MenuUtil.askForPositiveInt("Which raw material do you want to use ?");
 
                if (materials.size() <= rawId || materials.get(rawId) == null) {
                    System.err.println("This raw materials doesn't exist.");
                    finish = true;
                    error = true;
                } else {
                    RawMaterial rawChoosed = materials.get(rawId);
                    int quantity = MenuUtil.askForPositiveInt("Enter the quantity of " + rawChoosed.getName() + " needed in the dish.");
                    compositions.add(new CompositionDish(dishToCreate, rawChoosed, quantity));
                    
                    int yesOrNoDish = MenuUtil.askForYesOrNo("Is the composition of the dish over ?");
                    if (yesOrNoDish == 1) finish = true;
 
                }
 
            }
 
            if(!error) {
                System.out.println("Summary:");
                System.out.println(" > NAME: " + dishName);
                System.out.println(" > CATEGORY: " + cat.getName());
                System.out.println("Composition:");
                for (CompositionDish compositionDish : compositions) {
                    System.out.println(" > " + compositionDish.getRawMaterial().getName() + " | x" + compositionDish.getQuantity());
                }
            int yesOrNoDish = MenuUtil.askForYesOrNo("Do you confirm the creation of the dish ?");
 
            if (yesOrNoDish == 1) {
                dishToCreate.save();
                for (CompositionDish compositionDish : compositions) {
                    compositionDish.save();
                }
                System.out.println("The creation of the dish is a success !");
            } else {
                System.err.println("The creation of the dish was cancelled.");
            }
            }else {
                System.err.println("The creation of the dish was cancelled.");
            }
            break;
        case 5:
            ArrayList<PreparationTimeDish> preparations = new ArrayList<PreparationTimeDish>();
            
            for (SessionOrder sessionOrder : SessionOrder.find.allWhereTime()) {
                long time = sessionOrder.getDate_completion().getTime()-sessionOrder.getDate_creation().getTime();
                int idx = preparations.indexOf(new PreparationTimeDish(sessionOrder.getDish()));
                if(idx == -1) {
                    ArrayList<Long> times = new ArrayList<Long>();
                    times.add(time);
                    preparations.add(new PreparationTimeDish(sessionOrder.getDish(),times));
                }else {
                    preparations.get(idx).getTimeDish().add(time);
                }
            }
            
            for (PreparationTimeDish preparationTimeDish : preparations) {
                long total = 0L;
                for (Long l : preparationTimeDish.getTimeDish()) {
                    total = total +l;
                }
                long divided = total/preparationTimeDish.getTimeDish().size();
                
                String timed = String.format("%dmn %ds", 
                        TimeUnit.MILLISECONDS.toMinutes(divided),
                        TimeUnit.MILLISECONDS.toSeconds(divided) - 
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(divided))
                    );
                System.out.println(" > " + preparationTimeDish.getDish().getName() + ": " + timed);
            }
            
            break;
        default:
            break;
        }
    }
 
    public static CookerMenu getInstance() {
        if (instance == null)
            instance = new CookerMenu();
        return instance;
    }
 
    public static void setInstance(CookerMenu instance) {
        CookerMenu.instance = instance;
    }
 
}