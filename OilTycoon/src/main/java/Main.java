import domains.DbInfo;
import domains.Equipment;
import domains.Well;
import model.WellManager;
import org.sqlite.SQLiteDataSource;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


      public static void main(String[] args) {
//          Well well = new Well();
//          well.setName("name");
//          well.setId("id");
//          ArrayList eqL = new ArrayList();
//          eqL.add(new Equipment("1"));
//          eqL.add(new Equipment("2"));
//          well.setEquipmentList(eqL);
//
//          DbInfo db = new DbInfo();
//          ArrayList wL = new ArrayList();
//          wL.add(well);
//          wL.add(well);
//          db.setWellList(wL);
//          jaxbObjectToXML(db);
//
//          if(true) return;

        Scanner in = new Scanner(System.in);
        WellManager wellManager = new WellManager();
        int input;

        System.out.println("Welcome tycoon!!11\n");

        do {
            System.out.println("Input menu item number to proceed");
            showMenu();
            input = in.nextInt();
            switch (input){
                case 1:
                    showAddEquipMenu();
                    int equipAmount = in.nextInt();
                    String wellName = in.nextLine();
                    wellManager.addEquip(wellName, equipAmount);
                    System.out.println("Equipment added successfully\n");
                    break;
                case 2:
                    showShowEquipMenu();
                    in.useDelimiter("\\n");
                    String wellList = in.next();
                    wellManager.printEquip(wellList);
                    break;
                case 3:
                    System.out.println("Input preferred file name\n");
                    String fileName = in.next();
                    wellManager.exportToXml(fileName);
                    System.out.println("Data export was done successfully.\n");
                    break;
                default:
            }
        }while (input != 0);

        in.close();
        System.out.println("My work is finished! Goodbye...");
    }

    private static void showShowEquipMenu() {
        System.out.println("Input wells' names separating them by spaces");
    }

    private static void showAddEquipMenu() {
        System.out.println("Input amount of the equipment and well name separated by space");

    }

    private static void showMenu(){
        System.out.println("1. Add equipment to the well");
        System.out.println("2. Show amount of the equipment on the specified wells");
        System.out.println("3. Export data to xml file");
        System.out.println("0. Exit");
    }
}
