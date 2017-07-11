/**
 * Created by saurabhnakra on 07/07/17.
 */
public class Mailing_List_URL {
    String protocol = "";
    String host_name = "";
    String dynamic_URL = "";
    String base_URL = "";

    Mailing_List_URL(String protocol,String host_name){
        this.protocol = protocol;
        this.host_name = host_name;
        this.base_URL = protocol+"://"+host_name;
        this.dynamic_URL = base_URL;
    }
    Mailing_List_URL(String baseurl){
        this.base_URL = baseurl;
        this.dynamic_URL = base_URL;
    }

    void add_To_base_url(String add_it){
        base_URL += "/"+add_it;
        dynamic_URL = base_URL;
    }

    void add_list_To_base__url(String[] list_to_add){
        for(String s:list_to_add){
            base_URL += "/"+s;
        }
        dynamic_URL = base_URL;
    }

    void make_dynamic_url_equal_to_base_url(){
        dynamic_URL = base_URL;
    }
    void add_To_dynamic_url(String add_it){
        dynamic_URL += "/"+add_it;
    }

    void add_list_To_dynamic__url(String[] list_to_add){
        for(String s:list_to_add){
            dynamic_URL += "/"+s;
        }
    }
}
