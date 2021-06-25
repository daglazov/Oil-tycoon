package domains;

import org.apache.commons.lang3.RandomStringUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"name","id"})
public class Equipment {
    private int id;
    private String name;
    private int wellId;

    public Equipment(){
        this.name = generateName();
    }

    public Equipment(int wellId){
        this.name = generateName();
        this.wellId = wellId;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWellId(int wellId) {
        this.wellId = wellId;
    }

    @XmlTransient
    public int getWellId() {
        return wellId;
    }

    private String generateName() {
        return RandomStringUtils.randomAlphanumeric(32);
    }
}
