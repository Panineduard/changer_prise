//package classes.setting;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//import java.io.File;
//
///**
// * Created by volkswagen1 on 06.08.2016.
// */
//public class Setting {
//    private Setting() {
//    }
//
//    private String path = this.getClass().getClassLoader().getResource("setting.xml").getPath();
//    private static Javax settingJavax = null;
//
//    static {
//        File file = new File(new Setting().path);
//        JAXBContext jaxbContext = null;
//        Unmarshaller jaxbUnmarshaller = null;
//        try {
//            jaxbContext = JAXBContext.newInstance(Javax.class);
//            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//            settingJavax = (Javax) jaxbUnmarshaller.unmarshal(file);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getPriseBut() {
//        return settingJavax.getPrButton();
//    }
//
//    public static String getFile() {
//        return settingJavax.getFile();
//    }
//    public static String getLoadText(){
//        return settingJavax.load();
//    }
//    public static String getUserFolder(){
//        File folder = new File("userFolder");
//        if(!folder.exists()) {
//            folder.mkdirs();
//        }
//        return folder.getAbsolutePath();
//    }
//    public static String getDNameText(){return settingJavax.dealer();}
//    public static String getPNumText(){return settingJavax.PNumberRow();}
//    public static String getCountPText(){return settingJavax.countRow();}
//    public static String getPrText(){return settingJavax.priseRow();}
//
//}
