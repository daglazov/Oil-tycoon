package domains;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlType(propOrder={"name","id", "equipmentList"})
public class Well {

    private int id;

    private String name;

    private List<Equipment> equipmentList;


    public Well(){
    }
    public Well(String name){
        this.name = name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @XmlElement(name="equipment")
    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }
}
