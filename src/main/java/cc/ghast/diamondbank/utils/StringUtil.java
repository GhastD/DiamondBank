package cc.ghast.diamondbank.utils;

/**
 * @author Ghast
 * @since 15-Mar-20
 */
public class StringUtil {
    public static String[] displaceByOne(String[] array){
        String[] ne = new String[array.length - 1];
        System.arraycopy(array, 1, ne, 0, array.length - 1);
        return ne;
    }
}
