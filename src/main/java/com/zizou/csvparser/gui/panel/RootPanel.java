package com.zizou.csvparser.gui.panel;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * Created by zizou on 2017-09-04.
 */
@Component
public class RootPanel extends JPanel{
    private JButton selectButton = new JButton("Select");
    private JTextArea resultArea = new JTextArea();

    @PostConstruct
    private void init(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.selectButton.addActionListener(createSelectDirButtonListener());
        this.resultArea.setEditable(false);
        this.add(this.selectButton);
        this.add(this.resultArea);
    }

    private ActionListener createSelectDirButtonListener(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if(fileChooser.showOpenDialog(RootPanel.this) == 0){
                    File file = fileChooser.getSelectedFile();
                    if(RootPanel.this.fileValidateCheck(file)){
                        RootPanel.this.getFieldDataByNumber(2, file.getAbsolutePath());
                    }else{
                        JOptionPane.showMessageDialog(RootPanel.this, "File is not supported type...");
                    }

                }
            }
        };
    }

    private boolean fileValidateCheck(File file){
        if(file == null){
            return false;
        }
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);

        return FileType.isValidFileType(fileType);
    }

    private void getFieldDataByNumber(int fieldNum, String path){
        HashMap<String, Integer> domainCountMap = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))){
            String line;

            while((line = br.readLine()) != null){
                String[] field = line.split(",");
                String[] mail = field[2].substring(1, field[2].length()-1).split("@");
                if(mail.length == 2){
                    String domain = mail[1];
                    if(domainCountMap.containsKey(domain)){
                            int count = domainCountMap.get(domain);
                            count++;
                            domainCountMap.put(domain, count);
                    }else{
                        domainCountMap.put(domain, 1);
                    }
                }
            }
            System.out.println("domain : count");
            String result = "";
            for(String key : domainCountMap.keySet()){
                //System.out.println(String.format("%s : %s", key, domainCountMap.get(key)));
                result = result + String.format("%s : %s", key, domainCountMap.get(key)) + "\r\n";
            }
            this.resultArea.setText(result);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private enum FileType{
        CSV("csv");

        private String type;

        FileType(String type){
            this.type = type;
        }

        public static boolean isValidFileType(String type){
            for(FileType fileType : EnumSet.allOf(FileType.class)){
                if(fileType.type.equals(type)){
                    return true;
                }
            }
            return false;
        }
    }
}
