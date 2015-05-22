
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriel
 */
public class LoadFromFile {
    File src;
    private int rows = -1;
    private int cols = -1;
    void setFilePath(String path){
        src = new File(path);
    }

    public int getDimension(){
        return cols * rows;
    }
    
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
    
    List<double[]> load(){
        List<double[]> instances = null;
        double[] instance;
        double[] output;
        int nInstances = 0;
        int outputPositions = 0;
        int index = 0;
        try {
            FileReader fr = new FileReader(src);
            BufferedReader in = new BufferedReader(fr);
            cols = Integer.parseInt(in.readLine());
            rows = Integer.parseInt(in.readLine());
            char l;
            nInstances = Integer.parseInt(in.readLine());
            outputPositions = nInstances;
            instances = new ArrayList<double[]>();
            in.readLine();
            while(nInstances-- > 0){
                instance = new double[cols*rows];
                index = 0;
                for (int i = 0; i < rows; i++) {
                    for(char c:in.readLine().replace('.', '0').replace('#', '1')
                            .toCharArray()){
                        instance[index++] = Character.getNumericValue(c);
                    }
                }
                l = in.readLine().toCharArray()[0];
                output = new double[outputPositions];
                
                for (int i = 0; i < output.length; i++) {
                    output[i] = 0;
                    if(i == (int)l - 65){
                        output[i] = 1;
                    }
                }
                
                instances.add(instance);
                instances.add(output);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instances;
    }
    
    public List<Instance> getInstances(){
        List<double[]> data = load();
        List<Instance> instances = new ArrayList<Instance>();
        Instance instance = null;
        int lineLin = 0;
        boolean flag = true;
        
        for (double[] in : data) {
            if (flag) {
                instance = new Instance();
                lineLin = 0;
                instance.featureValues = in;
                for (double j : in) {
                    if (lineLin++ == 4) {
                        lineLin = 0;
                    }
                }
                flag = !flag;
            }else{
                instance.output = in;
                flag = !flag;
                instances.add(instance);
            }
        }
        return instances;
    }
}
