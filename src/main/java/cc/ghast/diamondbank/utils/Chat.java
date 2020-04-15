package cc.ghast.diamondbank.utils;

import org.bukkit.Bukkit;

/**
 * @author Ghast
 * @since 05-Apr-20
 */
public class Chat {

    /**
     * @param s Input the message you wish to have it's color translated following the standard Bukkit color format
     * @return The string with the formatted color.
     */
    public static String translate(String s){
        return translateAlternateColorCodes('&', s);
    }

    /**
     * @param altColorChar Character you wish to replace. This means that anything which follows this specific character
     *                     (spaces count) and which is in the range of colors from Bukkit will be parsed and transformed
     *                     into Color Codes
     * @param textToTranslate This is the text you wish to transform the colors in
     * @return String which is formatted with the color
     * @apiNote This is directly from the BungeeCord source code to prevent conflicts between Bukkit and Bungee having this
     * method in a different package. All credits granted to the original Author
     */
    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();

        for(int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }

    public static void sendConsoleMessage(String message){
        Bukkit.getConsoleSender().sendMessage(Chat.translate(message));
    }
}
