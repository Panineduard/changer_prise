package classes;

import classes.modelClass.Dealer;
import classes.modelClass.SparePart;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by volkswagen1 on 20.08.2016.
 */
public class Frames {
    private JFrame dealerFrame = new JFrame();
    private JFrame mainFrame = new JFrame();
    private Service service = new Service();
    private File inputFile;
    private Dealer dealer = new Dealer();
    private String test;
    private JPanel dealerPanel = (JPanel) dealerFrame.getContentPane();
    JPanel mainPanel = (JPanel) mainFrame.getContentPane();
    private JLabel filesName = null;

    private JComponent positionComponent(JComponent component, int positionX, int positionY) {
        Dimension size1 = component.getPreferredSize();
        component.setBounds(positionX, positionY, size1.width, size1.height);
        return component;
    }

    private boolean integerValidator(String text) {
        Pattern pattern = Pattern.compile("[1-9]");
        return pattern.matcher(text).matches();
    }

    private class ListenerFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser();
            int ret = fileOpen.showDialog(null, "Выбрать фыйл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                inputFile = fileOpen.getSelectedFile();
                if (filesName != null) {
                    dealerPanel.remove(filesName);
                }
                filesName = new JLabel(inputFile.getName());
                dealerPanel.add(positionComponent(filesName, 180, 70));
                dealerPanel.repaint();
            }
        }
    }

    public void showSparePartsInfo(List<SparePart> spareParts) {
        if (spareParts.size() > 0) {
            JFrame sparePartsInfo = new JFrame();
            sparePartsInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            sparePartsInfo.setLayout(null);
            JPanel infoPanel = (JPanel) sparePartsInfo.getContentPane();
            infoPanel.add(positionComponent(new JLabel("Номер"), 20, 20));
            infoPanel.add(positionComponent(new JLabel("Название"), 120, 20));
            infoPanel.add(positionComponent(new JLabel("Остаток"), 400, 20));
            infoPanel.add(positionComponent(new JLabel("Цена"), 500, 20));
            infoPanel.add(positionComponent(new JLabel("Диллер"), 600, 20));
            if (spareParts != null) {
                final int[] posit = {50};
                spareParts.forEach(p -> {
                    if(p!=null) {
                        JLabel label1 = new JLabel(p.getNumber());
                        infoPanel.add(positionComponent(label1, 20, posit[0]));
                        JLabel label2 = new JLabel(p.getName());
                        JLabel label3 = new JLabel(p.getCount());
                        JLabel label4 = new JLabel(p.getPrise());
                        JLabel label5 = new JLabel(p.getDealerName());

                        infoPanel.add(positionComponent(label2, 120, posit[0]));
                        infoPanel.add(positionComponent(label3, 400, posit[0]));
                        infoPanel.add(positionComponent(label4, 500, posit[0]));
                        infoPanel.add(positionComponent(label5, 600, posit[0]));
                        posit[0] = posit[0] + 40;
                    }
                });

            }
            sparePartsInfo.setSize(750, 900);

            sparePartsInfo.setLocation(600, 200);
            sparePartsInfo.setVisible(true);
        } else JOptionPane.showMessageDialog(new JFrame(), "Поиск не дал результатов!");
    }

    public void showDealersInfo() {
        JFrame dealerInfo = new JFrame();
        dealerInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dealerInfo.setLayout(null);
        JPanel infoPanel = (JPanel) dealerInfo.getContentPane();
        List<Dealer> dealerList = service.getDealers();
        if (dealerList != null) {
            final int[] posit = {20};
            dealerList.forEach(d -> {
                JLabel label = new JLabel(d.getName());
                JLabel label1 = new JLabel(d.getDate());
                JButton change = new JButton("Изменить");
                change.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Dealer dealer = new Dealer(d);
                        new Frames().showDealerFrame(dealer);
                        dealerInfo.dispatchEvent(new WindowEvent(dealerInfo, WindowEvent.WINDOW_CLOSING));
                    }
                });
                infoPanel.add(positionComponent(label, 10, posit[0] + 4));
                infoPanel.add(positionComponent(label1, 100, posit[0] + 4));
                infoPanel.add(positionComponent(change, 250, posit[0]));
                posit[0] = posit[0] + 40;
            });
            JButton deleteAll = new JButton("Удалить всех");
            deleteAll.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    service.deleteAllDealerData();
                    dealerInfo.dispatchEvent(new WindowEvent(dealerInfo, WindowEvent.WINDOW_CLOSING));
                }
            });
            infoPanel.add(positionComponent(deleteAll, 130, 420));
            dealerInfo.setSize(400, 500);
        } else {
            JLabel label = new JLabel("Нет данных!");
            infoPanel.add(positionComponent(label, 20, 10));
            dealerInfo.setSize(400, 100);
        }

        dealerInfo.setLocation(600, 400);
        dealerInfo.setVisible(true);
    }


    public void showMainFrame() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(null);


        JLabel question1 = new JLabel("Найти по номеру :");
        JTextField partsNumber = new JTextField("", 20);
        JButton searchAction = new JButton("Найти");
        searchAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!partsNumber.getText().isEmpty()) {
                    new Frames().showSparePartsInfo(new Service().searchSparePartByNumber(partsNumber.getText()));
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Введите номер детали!");
                }
            }
        });
        JButton newDealer = new JButton("Новый диллер");
        JButton dealerInfo = new JButton("Информация");
        dealerInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDealersInfo();
            }
        });
        newDealer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Frames().showDealerFrame(null);
            }
        });
        mainPanel.add(positionComponent(question1, 100, 150));
        mainPanel.add(positionComponent(partsNumber, 250, 150));
        mainPanel.add(positionComponent(searchAction, 250, 200));
        mainPanel.add(positionComponent(newDealer, 450, 10));
        mainPanel.add(positionComponent(dealerInfo, 310, 10));
        mainFrame.setSize(600, 400);
        mainFrame.setLocation(600, 400);
        mainFrame.setVisible(true);
    }

    public void showDealerFrame(Dealer dealer) {
        dealerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dealerPanel.setLayout(null);
        String priseText = "";
        String nameText = "";
        String countText = "";
        String partNumberText = "";
        String partNameText = "";
        String dataText = LocalDate.now().toString();
        if (dealer != null) {
            priseText = String.valueOf(dealer.getPartPriseRow()+1);
            nameText = dealer.getName();
            countText = String.valueOf(dealer.getPartCountRow()+1);
            partNumberText = String.valueOf(dealer.getPartNumberRow()+1);
            partNameText = String.valueOf(dealer.getPartNameRow()+1);
            dataText = dealer.getDate();
            File file = new File(dealer.getPrisePath());
            if (file.exists()) {
                inputFile = file;
                if (filesName != null) {
                    dealerPanel.remove(filesName);
                }
                filesName = new JLabel(inputFile.getName());
                dealerPanel.add(positionComponent(filesName, 180, 70));
                dealerPanel.repaint();

            }


        }
        JTextField prise = new JTextField(priseText, 2);
        JTextField name = new JTextField(nameText, 20);
        JTextField partsCount = new JTextField(countText, 2);
        JTextField partsNumber = new JTextField(partNumberText, 2);
        JTextField partsName = new JTextField(partNameText, 2);
        JTextField date = new JTextField(dataText, 7);
        JLabel question1 = new JLabel("Имя диллера :");
        JLabel question2 = new JLabel("Цена :");
        JLabel question3 = new JLabel("Количество :");
        JLabel question4 = new JLabel("Номер запчасти :");
        JLabel question5 = new JLabel("Дата  :");
        JLabel question6 = new JLabel("Наименование  :");
        JButton getFile = new JButton("Загрузить прайс");
        getFile.addActionListener(new ListenerFile());
        JButton load = new JButton("Загрузить");

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**Here we check validation on a data and create new Dealer object*/
                if (integerValidator(prise.getText()) && integerValidator(partsCount.getText()) && name.getText() != null
                        && !name.getText().isEmpty() && integerValidator(partsNumber.getText()) && inputFile != null
                        && !date.getText().isEmpty() && integerValidator(partsName.getText())) {
                    if (dealer != null) {
                        service.deleteDealerToXMLSetting(dealer);
//                        inputFile=new File(dealer.getPrisePath()).getAbsoluteFile();
                    }
                    Dealer dealer1 = new Dealer();
                    dealer1.setName(name.getText());
                    dealer1.setPartPriseRow((new Integer(prise.getText())-1));
                    dealer1.setPartCountRow((new Integer(partsCount.getText())-1));
                    dealer1.setPartNumberRow((new Integer(partsNumber.getText())-1));
                    dealer1.setPartNameRow((new Integer(partsName.getText())-1));
                    dealer1.setDate(date.getText());
                    String path = service.addDealer(inputFile, dealer1);
                    if (dealer != null && !dealer.getPrisePath().equals(path)) {
                        new File(dealer.getPrisePath()).delete();
                    }
                    dealerFrame.dispatchEvent(new WindowEvent(dealerFrame, WindowEvent.WINDOW_CLOSING));
                } else JOptionPane.showMessageDialog(new JFrame(), "Неправильные данные!");
            }
        });
        dealerPanel.add(positionComponent(question1, 24, 10));
        dealerPanel.add(positionComponent(question2, 72, 40));
        dealerPanel.add(positionComponent(question3, 33, 70));
        dealerPanel.add(positionComponent(question4, 10, 100));
        dealerPanel.add(positionComponent(question5, 72, 160));
        dealerPanel.add(positionComponent(question6, 13, 130));
        dealerPanel.add(positionComponent(name, 130, 10));
        dealerPanel.add(positionComponent(prise, 130, 40));
        dealerPanel.add(positionComponent(partsCount, 130, 70));
        dealerPanel.add(positionComponent(partsNumber, 130, 100));
        dealerPanel.add(positionComponent(getFile, 200, 90));
        dealerPanel.add(positionComponent(load, 150, 200));
        dealerPanel.add(positionComponent(date, 130, 160));
        dealerPanel.add(positionComponent(partsName, 130, 130));
        dealerFrame.setSize(400, 300);
        dealerFrame.setLocation(600, 400);
        dealerFrame.setVisible(true);
    }
}
