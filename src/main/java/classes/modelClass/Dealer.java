package classes.modelClass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by volkswagen1 on 21.08.2016.
 */
@XmlRootElement(name = "dealer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dealer {
    private String name;
    private int partNumberRow;
    private int partCountRow;
    private int partPriseRow;
    private int partNameRow;
    private String prisePath;
    private String date;

    public Dealer() {
    }
    public Dealer(Dealer dealer) {
        name=dealer.getName();
        partNumberRow=dealer.getPartNumberRow();
        partCountRow=dealer.getPartCountRow();
        partPriseRow=dealer.getPartPriseRow();
        prisePath=dealer.getPrisePath();
        date=dealer.getDate();
        partNameRow=dealer.getPartNameRow();
    }

    public int getPartNameRow() {
        return partNameRow;
    }

    public void setPartNameRow(int partName) {
        this.partNameRow = partName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrisePath() {
        return prisePath;
    }

    public void setPrisePath(String prisePath) {
        this.prisePath = prisePath;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPartNumberRow() {
        return partNumberRow;
    }
    public void setPartNumberRow(int partNumberRow) {
        this.partNumberRow = partNumberRow;
    }

    public int getPartCountRow() {
        return partCountRow;
    }
    public void setPartCountRow(int partCountRow) {
        this.partCountRow = partCountRow;
    }

    public int getPartPriseRow() {
        return partPriseRow;
    }
    public void setPartPriseRow(int partPriseRow) {
        this.partPriseRow = partPriseRow;
    }
    @Override
    public String toString(){return "name - "+name+" row - "+partCountRow;}
    @Override
    public boolean equals(Object o){
        if(getClass()!=o.getClass()){
            return false;
        }
//        .trim().replace(" ", "")
        if(name.toLowerCase().trim().replace(" ", "").equals(((Dealer) o).getName().toLowerCase().trim().replace(" ", ""))){
            return true;
        }
        return false;
    }
    @Override
    public int hashCode(){
        return name.toLowerCase().trim().replace(" ", "").hashCode();
    }
}
