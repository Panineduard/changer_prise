package classes.modelClass;

/**
 * Created by volkswagen1 on 24.08.2016.
 */
public class SparePart {
    String name="";
    String number="";
    String dealerName="";
    String count="";
    String prise="";

    public SparePart() {
    }

    public SparePart(String name, String number, String dealerName, String count, String prise) {
        this.name = name;
        this.number = number;
        this.dealerName = dealerName;
        this.count = count;
        this.prise = prise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrise() {
        return prise;
    }

    public void setPrise(String prise) {
        this.prise = prise;
    }
    @Override
    public String toString(){
        return "number - "+number+"\n name - "+name+"\n prise - "+prise;
    }
}
