import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.List;

import org.jsoup.nodes.Document;

public class core_scraper {
    
    public static void main(String[] args) {
    	//Welcome interface
    	msg("-----------------------------------");
        msg("|       Welcome to Seas.com       |");
        msg("-----------------------------------");
        
        if (args.length <= 0 || args.length > 2) {
            msg("Detecting invalid input! Please see usage below.");
            usage();
            return;
        }
        // Initialization
        HTTPClient client = new HTTPClient();
        DOMParser parser = new DOMParser();
        String keyword;
        int pageNum = 1;
        
        // normalizes keyword
        try {
            args[0] = args[0].trim();
            if (args[0].isEmpty()) {
                msg("Please input a valid keyword.");
                return;
            }
            keyword = URLEncoder.encode(args[0], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            keyword = args[0];
        }
        
        // 1st query type
        if (args.length == 1) {
            String totalNum;
            try {
                Document doc = client.getDOMTree(keyword, pageNum);
                parser.parse(doc);
                totalNum = parser.getItemsTotalNum();
            }
            catch (IOException e) {
                msg("Connection failed!");
                return;
            }
            // Result display
	        if (totalNum.isEmpty())
	            msg("\nNot found.");
	        else
	            msg("\nTotal number of '" + args[0] + "': " + totalNum);
        }
        
        // 2nd query type
        else if (args.length == 2) {
            try {
                pageNum = Integer.parseInt(args[1]);
                if (pageNum <= 0) {
                    msg("Invalid page number. Please see usage below");
                    usage();
                    return;
                }
            }
            catch (NumberFormatException e) {
                msg("Invalid page number. Please see usage below");
                usage();
                return;
            }
            String totalNum;
            List<Item> itemsList = null;
            try {
                Document doc = client.getDOMTree(keyword, pageNum);
                parser.parse(doc);
                totalNum = parser.getItemsTotalNum();
                itemsList = parser.getAllItems();
            }
            catch (SocketTimeoutException e) {
                msg("Connection Failed.");
                return;
            }
            catch (IOException e) {
                msg("Connection Failed.");
                return;
            }
         
            //Result display
            if (itemsList == null || itemsList.isEmpty()) {
                msg("\nNot found");
            }
            else {
            	int viewItemsNum = HTTPClient.VALUE_VIEW_ITEMS;
            	if(pageNum==1){
            		String span = "Items 0"  + "-" + viewItemsNum +
 	                       " of " + totalNum + " for '" + args[0] + "'";
 	                msg("\n" + span);
 	                msg("----------------------------------------------------------------------------------------------");
 	                for (int i = 0; i < viewItemsNum; ++i) {
 	                    Item item = itemsList.get(i);
 	                    msg("- " + ((pageNum - 1) * viewItemsNum + i + 1) + " -");
 	                    msg(" [NAME]   " + item.getName());
 	                    msg(" [PRICE]  " + item.getPrice());
 	                    msg(" [VENDOR] " + item.getVendor());
 	                    msg("");
 	                }
 	                msg("----------------------------------------------------------------------------------------------");
 	                msg(span);
            	}
            	else{
	                String span = "Items " + 
	                       ((pageNum - 1) * viewItemsNum + 1) + "-" + 
	                       ((pageNum - 1) * viewItemsNum + itemsList.size()) + 
	                       " of " + totalNum + " for '" + args[0] + "'";
	                msg("\n" + span);
	                msg("----------------------------------------------------------------------------------------------");
	                for (int i = 0; i < itemsList.size(); ++i) {
	                    Item item = itemsList.get(i);
	                    msg("- " + ((pageNum - 1) * viewItemsNum + i + 1) + " -");
	                    msg(" [NAME]   " + item.getName());
	                    msg(" [PRICE]  " + item.getPrice());
	                    msg(" [VENDOR] " + item.getVendor());
	                    msg("");
	                }
	                msg("----------------------------------------------------------------------------------------------");
	                msg(span);
            	}
            }
        }
    }
    

    
    private static void usage() {
        msg("");
        msg("    Usage : core_scraper <keyword> [<pageNum>]");
        msg("<keyword> : Product to be searched on 'Sears.com'.");
        msg("<pageNum> : (Optional) page number to be displayer");
    }
    
    /**
     * Prints a message to console, with a newline
     * char appended.
     * @param msg Message to be displayed.
     */
    private static void msg(Object msg) {
        msg(msg, true);
    }
    
    /**
     * Prints a message to console.
     * @param msg Message to be displayed.
     * @param nl True if a newline char is appended
     * to this message; false otherwise.
     */
    private static void msg(Object msg, boolean nl) {
        System.out.print(msg);
        if (nl) System.out.print("\n");
    }
}