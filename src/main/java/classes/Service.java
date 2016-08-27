package classes;

import classes.modelClass.Dealer;
import classes.modelClass.Dealers;
import classes.modelClass.SparePart;


import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by volkswagen1 on 20.08.2016.
 */
public class Service {
    private File file = new File("settingFile.xml");
    private File folder;
    {folder = new File("userFolder");
        if(!folder.exists()) {
            folder.mkdirs();
        }
    }

//    public static void main(String... arg) {
//        File file =new File("D:\\Java\\Java projects\\changer_prise\\userFolder\\Автодом.xls");
//        new Service().xlsReader(file);
//        new Service().moveAndCheckFileXLS(new File("D:\\Java\\Java projects\\changer_prise\\testFolder\\Наличие Ауди-Центр Киев.xls"),"ff");
//        System.out.println(new Service().getDealers());
//        new Service().deleteAllDealerData();

//    }

    /**
     * Method put Dealer data to xml file (settingFile.xml) and create file xls with prises
     *
     * @param prise  it is xls file with prise
     * @param dealer it is Dealer object with data without prises path
     * @see Dealer
     * @return new path of file on new place
     */
    public String addDealer(File prise, Dealer dealer) {
        if (prise.exists() && dealer != null && dealer.getName() != null) {
            String path = moveAndCheckFileXLS(prise, dealer.getName());
            if (path != null) {
                dealer.setPrisePath(path);
                addDealerToXMLSetting(dealer);
            }
            return path;
        }
        return null;
    }
    /***/
    public List<SparePart> searchSparePartByNumber(String partsNumber){
        List<SparePart>result=new ArrayList<>();
        String number=partsNumber.replace(" ","").trim().toLowerCase();
        List<Dealer>dealers=new Service().getDealers();
        dealers.forEach(dealer -> {
            File file = new File(dealer.getPrisePath());
            if (file.exists()) {
                result.add(xlsReader(file, dealer, number));
            }
        });
        return result;
    }

    private SparePart xlsReader(File file,Dealer dealer,String sparePartNumber) {
        HSSFWorkbook myExcelBook = null;
        try {
            myExcelBook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet myExcelSheet = myExcelBook.getSheet(myExcelBook.getSheetName(0));
            int from = myExcelSheet.getFirstRowNum();
            int to = myExcelSheet.getLastRowNum();
            for (int i = from; i < to; i++) {
                HSSFRow row = myExcelSheet.getRow(i);

                if (row.getCell(dealer.getPartNumberRow())!=null&&row.getCell(dealer.getPartNumberRow()).getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    String name = row.getCell(dealer.getPartNumberRow()).getStringCellValue();
                    String newName=name.replace(" ","").trim().toLowerCase();
                    if(newName.equals(sparePartNumber)){
                        SparePart sparePart=new SparePart();
                        sparePart.setNumber(name);
                        if(row.getCell(dealer.getPartNameRow()).getCellType() == HSSFCell.CELL_TYPE_STRING){
                            sparePart.setName(row.getCell(dealer.getPartNameRow()).getStringCellValue());
                        }
                        else sparePart.setName("без названия");
                        if(row.getCell(dealer.getPartCountRow()).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                            sparePart.setCount(String.valueOf(row.getCell(dealer.getPartCountRow())));
                        }
                        else sparePart.setCount("без количества");
                        if(row.getCell(dealer.getPartPriseRow())!=null&&row.getCell(dealer.getPartPriseRow()).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                            sparePart.setPrise(String.valueOf(row.getCell(dealer.getPartPriseRow())));
                        }
                        else sparePart.setPrise("без цены");
                        sparePart.setDealerName(dealer.getName());
                        return sparePart;
                    }
                }
            }
            myExcelBook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String moveAndCheckFileXLS(File file, String dealersName) {
        StringBuffer stringBuffer = new StringBuffer(file.getName());
        if (stringBuffer.substring(stringBuffer.lastIndexOf(".") + 1, stringBuffer.length()).equalsIgnoreCase("xls")) {
            FileInputStream inStream;
            FileOutputStream outStream;
            String path = null;
            try {

                File bfile = new File(folder.getAbsoluteFile() + "/" + dealersName + ".xls");
                if (!bfile.getAbsolutePath().equals(file.getAbsolutePath())){
                    inStream = new FileInputStream(file);
                    outStream = new FileOutputStream(bfile);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inStream.read(buffer)) > 0) {
                        outStream.write(buffer, 0, length);
                    }
                    inStream.close();
                    outStream.close();
                }

                return bfile.getAbsolutePath();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Введен не корректный файл!");
        }
        return null;
    }

    /**
     * @return set collection of dealer object or return null if method cant find data in xml file
     * @see Dealer
     */
    public List<Dealer> getDealers() {
        JAXBContext jaxbContext = null;
        if (!file.exists()) return null;
        try {
            jaxbContext = JAXBContext.newInstance(Dealers.class);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
        Dealers dealers = null;
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            dealers = (Dealers) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            return null;
        }
        if (dealers != null && dealers.getDealerSettings() != null) {
            return dealers.getDealerSettings();
        } else return null;
    }

    /**
     * Method delete all data from files in a workspace
     */
    public void deleteAllDealerData() {
        if (file.exists()) {
            file.delete();
        }
        File folder1 = new File(folder.getAbsolutePath());
        if (folder1.exists()) {
            try {
                FileUtils.deleteDirectory(folder1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean addDealerToXMLSetting(Dealer dealer) {
        if (dealer == null) return false;
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Dealers.class);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (JAXBException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        Dealers dealers;
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            dealers = (Dealers) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            dealers = new Dealers();
        }
        List<Dealer> dealersList;
        if (dealers.getDealerSettings() != null) {
            dealersList = dealers.getDealerSettings();
        } else dealersList = new ArrayList<Dealer>();
        if (dealersList.contains(dealer)) {
            String oldPath = dealersList.get(dealersList.indexOf(dealer)).getPrisePath();
            String newPath = dealer.getPrisePath();
            System.out.println(!oldPath.equals(newPath));
            if (!oldPath.equals(newPath)) {
                File prise = new File(oldPath);
                if (prise.exists()) prise.delete();
            }
            dealersList.remove(dealer);
        }
        dealersList.add(dealer);
        dealers.setDealerSettings(dealersList);

        Marshaller marshaller = null;
        try {
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(dealers, file);
        } catch (JAXBException e) {
            return false;
        }

        return true;
    }
/**
 * @param dealer it is a Dealer.class object for delete from xml file
 * @return true if data deleted sucesfull*/
    public boolean deleteDealerToXMLSetting(Dealer dealer) {
        if (dealer == null) return false;
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Dealers.class);
            if (!file.exists()) {
                file.createNewFile();
            }
            Dealers dealers;
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            dealers = (Dealers) unmarshaller.unmarshal(file);
            if (dealers.getDealerSettings() != null) {
                List<Dealer> dealersList;
                dealersList = dealers.getDealerSettings();
                dealersList.remove(dealer);
                dealers.setDealerSettings(dealersList);
                Marshaller marshaller = null;
                marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(dealers, file);
            }
            return true;
        }
        catch (JAXBException e) {
            return false;
        }
        catch (IOException e) {
            return false;
        }
    }
}
