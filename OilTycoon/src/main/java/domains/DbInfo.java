package domains;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="dbinfo")
public class DbInfo {
    private List<Well> wellList;

    public List<Well> getWellList() {
        return wellList;
    }

    @XmlElement(name="well")
    public void setWellList(List<Well> wellList) {
        this.wellList = wellList;
    }
}
