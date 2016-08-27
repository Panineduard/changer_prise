package classes.modelClass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

/**
 * Created by volkswagen1 on 07.08.2016.
 */
@XmlRootElement(name = "dealers")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dealers {
    @XmlElement(name = "dealer")
    private List<Dealer> dealerSettings=null;
    public List<Dealer> getDealerSettings() {
        return dealerSettings;
    }

    public void setDealerSettings(List<Dealer> dealerSettings) {
        this.dealerSettings = dealerSettings;
    }



}
