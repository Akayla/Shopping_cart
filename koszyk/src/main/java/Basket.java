import java.util.HashMap;

public class Basket {

    private static HashMap<Integer, Integer> contents = new HashMap<Integer, Integer>();

    public static void setContents(Integer id, Integer amount) {
        contents.put(id, amount);
    }

    public static HashMap<Integer, Integer> getContents() {
        return contents;
    }

    public static void deleteItem(Integer id){
        if(id != -1) {
            if(!contents.isEmpty()){
                contents.remove(id);
            }
        }
    }

    public static void addToBasket(Integer id, Integer amount){
        contents.replace(id, amount);
    }
}
