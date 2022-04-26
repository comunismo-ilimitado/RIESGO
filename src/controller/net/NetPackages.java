package controller.net;

import java.io.*;

public class NetPackages {

    public static class Package implements Serializable {

        public byte[] getBytes(){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream out = new ObjectOutputStream(bos);
                out.writeObject(this);
                return bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bos.toByteArray();
        }

        public static Object bytesToObject(byte[] bs) throws ClassNotFoundException, IOException{
            ByteArrayInputStream bis = new ByteArrayInputStream(bs);
            ObjectInputStream oi = new ObjectInputStream(bis);
            return oi.readObject();
        }

    }

    public static class ClientInfo extends Package{
        String name = "";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
