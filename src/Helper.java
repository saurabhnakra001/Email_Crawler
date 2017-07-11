import java.util.Calendar;

/**
 * Created by saurabhnakra on 07/07/17.
 */
public class Helper {

    String month_name(String month_num){
        switch(month_num) {
            case "01" :
                return "jan";
            case "02" :
                return "feb";
            case "03" :
                return "mar";
            case "04" :
                return "apr";
            case "05" :
                return "may";
            case "06" :
                return "jun";
            case "07" :
                return "jul";
            case "08" :
                return "aug";
            case "09" :
                return "sep";
            case "10" :
                return "oct";
            case "11" :
                return "nov";
            case "12" :
                return "dec";
        }
        return "";
    }
    String month_num(String month_name){
        month_name = month_name.toLowerCase();
        month_name = month_name.substring(0,3);
        switch(month_name) {
            case "jan" :
                return "01";
            case "feb" :
                return "02";
            case "mar" :
                return "03";
            case "apr" :
                return "04";
            case "may" :
                return "05";
            case "jun" :
                return "06";
            case "jul" :
                return "07";
            case "aug" :
                return "08";
            case "sep" :
                return "09";
            case "oct" :
                return "10";
            case "nov" :
                return "11";
            case "dec" :
                return "12";
        }
        return "";
    }
    boolean is_current_month(String month_name,String year_name){
        Calendar now = Calendar.getInstance();
        String month_index = month_num(month_name);
        if(Integer.valueOf(year_name)==now.get(Calendar.YEAR)
                && Integer.valueOf(month_index)== (now.get(Calendar.MONTH) + 1)){
            return true;
        }
        return false;
    }
    String month_mapping(String month_index){
        String []months= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        int month_num = Integer.parseInt(month_index);
        return months[month_num-1];
    }

}
