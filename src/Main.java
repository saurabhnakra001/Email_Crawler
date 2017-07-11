import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Main {

    static Logger logger = Logger.getLogger("Main.class");
    static Helper helper = new Helper();

    // This function returns the list of elements corresponding to unit storage
    List<String> elements_of_unit_storage(Document doc){
        Elements info = doc.getElementsByClass("date");
        List<String> unit_macro_list = new ArrayList<>();
        String month_year = "";
        for (Element element : info) {
            month_year = element.ownText();
            unit_macro_list.add(month_year);
        }
        return unit_macro_list;
    }

    // This function returns URL corresponding to the unit level element
    // for e.g. here unit level element refers to month of a specific year

    String make_link(String element,Mailing_List_URL mailing_list_url){
        // here element is of the format "Mmm YYYY" like "Dec 2012".

        String middle = "";
        String[] splitted = element.split(" ");
        middle = splitted[1];
        middle += helper.month_num(splitted[0]);

        // dynamic_URL = base_URL
        mailing_list_url.make_dynamic_url_equal_to_base_url();

        // adding unit level part specific tomonth and year to link
        mailing_list_url.add_To_dynamic_url(middle+".mbox");
        return mailing_list_url.dynamic_URL;
    }

    // This function checks if folder mentioned in the argument exists in the filesystem'
    // if exists it return true
    // else it tries to create it, if done successfully it returns true else false

    boolean make_folder(String name){
        name = "data/"+name;
        File dir = new File(name);
        if(dir.exists()){
            return true;
        }

        try{
            dir.mkdirs();
        }
        catch(SecurityException se){
            logger.severe("could not make directory : "+name);
            return false;
        }
        return true;
    }

    // This function extracts the name of the file from the url of unit storage
    // This is specific to the way mail archive follows

    String get_file_name_to_store_mails_from_url(String url){
        String filename = null;
        if(url.length()<=12) {
            logger.severe("incorrect url is formed : " + url);
            return filename;
        }
        filename = url.substring(url.length()-11);
        return filename;
    }

    // This function focuses on downloading the file of unit storage to local system
    void download_file(String url){
        URL link = null;
        try {
            link = new URL(url);
        } catch (MalformedURLException e) {
            logger.severe("incorrect url : "+link);
            return;
        }

        String file_name = get_file_name_to_store_mails_from_url(url);
        String year_name= file_name.substring(0,4);
        String month_index = file_name.substring(4,6);

        ReadableByteChannel rbc = null;
        try {
            rbc = Channels.newChannel(link.openStream());
        } catch (IOException e) {
            logger.severe("not able to read from this link : "+link);
            return;
        }

        FileOutputStream fos = null;

        try {
            boolean folder_made = make_folder(year_name);
            if(folder_made){
                String month_name = helper.month_mapping(month_index);
                boolean subfolder_made = make_folder(year_name+"/"+month_name);
                if(subfolder_made){
                    File temp = new File("data/"+year_name+"/"+month_name+"/"+file_name);
                    if(temp.exists() && !helper.is_current_month(month_name,year_name)){
                        logger.info(temp.getName()+" already exists");
                        return;
                    }
                    fos = new FileOutputStream("data/"+year_name+"/"+month_name+"/"+file_name);
                }
            }
        } catch (FileNotFoundException e) {
            logger.severe("problem in creating file named : "+file_name);
            return;
        }
        try {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            logger.info(file_name+" file downloaded ");
        } catch (IOException e) {
            logger.severe("cannot transfer to file named : "+file_name);
            return;
        }
    }

    public static void main(String[] args) {

        // inputs
        String mailing_list = "abdera-commits";
        String protocol = "http";
        String host_name = "mail-archives.apache.org";
        String [] list_to_add = {"mod_mbox",mailing_list};

        // object of Main class
        Main object = new Main();

        // initialization of log file corresponding to Main class.
        boolean append = false;
        try {
            FileHandler handler = new FileHandler("main_file.log", append);
            logger.addHandler(handler);
        }
        catch(Exception e){
        }

        // calling constructor of Mailing_list_URL class, thus initializing base_URL and dynamic_URL as well
        Mailing_List_URL mailing_list_url = new Mailing_List_URL(protocol,host_name);

        // adding to base_URL (this is specific to the archive)
        mailing_list_url.add_list_To_base__url(list_to_add);

        // connecting to page from where different unit level storage pages are linked
        Document doc = null;
        try {
            doc = Jsoup.connect(mailing_list_url.base_URL).get();
        }
        catch(Exception e){
            logger.severe("Not able to fetch mails from provided mailing list url : "+mailing_list_url.base_URL);
            System.exit(0);
        }

        // get list of unit level storage names of mails
        // for e.g. in this case mails are stored month wise for each year

        List<String> ls = object.elements_of_unit_storage(doc);

        // iterating each element of list and
        // making appropriate link to get mails
        // corresponding to each of element in separate folder

        for (String month_year : ls) {
            String link = object.make_link(month_year,mailing_list_url);
            object.download_file(link);
        }

        logger.info("all emails downloaded");
    }
}

