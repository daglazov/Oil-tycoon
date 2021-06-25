package model;

import db.DbService;
import domains.DbInfo;
import domains.Equipment;
import domains.Well;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class WellManager {
    private DbService service;



    public WellManager(){
        this.service = DbService.getInstance();
    }


    public void exportToXml(String fileName) {
        DbInfo dbInfo = new DbInfo();
        dbInfo.setWellList(service.getAllWells());
        jaxbObjectToXML(dbInfo, fileName);
    }

    public void addEquip(String wellName, int equipAmount) {
        Well well = service.saveWell(new Well(wellName));
        for (int i=0; i<equipAmount; ++i){
            service.saveEquip(new Equipment(well.getId()));
        }
    }

    public void printEquip(String wellList) {
        for (String name: wellList.split("\\s")
             ) {
                int equipAmount = 0;
                Well well = service.findWellByName(name);
                if (well != null) {
                    equipAmount = service.findEquipAmountForWell(well.getId());
                }else {
                    well = new Well(name);
                    service.saveWell(well);
                }
                System.out.println(well.getName() + " - " + equipAmount);
        }
    }

    private static void jaxbObjectToXML(DbInfo domain, String fileName){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(DbInfo.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            OutputStream outStream = new FileOutputStream(fileName + ".xml");
            jaxbMarshaller.marshal(domain, outStream);

        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
