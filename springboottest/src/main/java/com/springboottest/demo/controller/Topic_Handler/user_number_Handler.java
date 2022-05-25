package com.springboottest.demo.controller.Topic_Handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.springboottest.demo.controller.User_Account_Handler;
import com.springboottest.demo.entity.Browse_History;
import com.springboottest.demo.entity.Topic_Info.user_num;
import com.springboottest.demo.repository.Browse_History_Repo;
import com.springboottest.demo.repository.Topic_Repo.user_num_repo;
import com.springboottest.demo.repository.User_Account_Repo;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user_num")
@CrossOrigin
public class user_number_Handler {
    @Autowired
    private user_num_repo user_num_repo;

    @Autowired
    private User_Account_Repo user_account_repo;

    @Autowired
    private Browse_History_Repo browse_history_repo;

    private final String topic_name = "Get all user amount";

    @GetMapping("getall")
    public List<user_num> getall() {
        user_account_repo.update_search_cnt(User_Account_Handler.Current_User_Account.getUser_name());
        browse_history_repo.save(new Browse_History(
                User_Account_Handler.Current_User_Account.getUser_name(),
                topic_name,
                Browse_History.df.format(System.currentTimeMillis())));

        return user_num_repo.findAll();
    }

    @PostMapping("/temp")
    public String temp () {
        String res = "11 3817 23657 32649 41749 49758 59666 71791 84234 97082 113763 129847 145867 163715 179252 196307 218169 238415 257634 279174 301400 325151 348716 373995 400082 427589 453885 483052 514526 548878 581854 623662 668546 716063 761908 804975 851102 899820 949870 998608 1049151 1101587 1150254 1208977 1266120 1333366 1394162 1454323 1524376 1593938 1664119 1739625 1815371 1893296 1965358 2056433 2155586 2264894 2369254 2468020 2564294 2664468 2757143 2855267 2961453 3075556 3163316 3272683 3390346 3502468 3606261 3709414 3806959 3905374 4002919 4105092 4207078 4315365 4410107 4514671 4623933 4739146 4855017 4959242 5067049 5175629 5280786 5386749 5500977 5614213 5722157 5845186 5984803 6120090 6255808 6386043 6512470 6635644 6753463 6871773 7061453 7196579 7332423 7451881 7595814 7754378 7904338 8046108 8192254 8342807 8490372 8632754 8800882 8962401 9113852 9230431 9370735 9513009 9660760 9808576 9951512 10090854 10231894 10366367 10505255 10650055 10789518 10907208 11049963 11202608 11355738 11493218 11637744 11776776 11914799 12048658 12197621 12349100 12508234 12679179 12861653 13030526 13255645 13496519 13698640 13883620 14059586 14225922 14394828 14577903 14764544 14933177 15132651 15332467 15547637 15827366 16111328 16369388 16574838 16801778 17048542 17291425 17550448 17795950 18065251 18333904 18633036 18991261";
        String[] num = res.split(" ");
        for (int i = 0; i < num.length; i++) {
            user_num t = new user_num();
            t.setId(i+1);
            t.setNumber(Integer.parseInt(num[i]));
            user_num_repo.save(t);
        }
        return "success";
    }

    @PostMapping("/count_user")
    public String crawl() throws IOException {
        user_num_repo.deleteAll();
        int date = 1217548800;
        int end = 1653436800;
        int id = 0;
        user_num temp = new user_num();
        temp.setId(id);
        temp.setNumber(0);
        user_num_repo.save(temp);
        String base1 = "https://api.stackexchange.com/2.3/users?page=1&pagesize=10&fromdate=";
        String base2 = "&todate=1653436800&order=asc&sort=creation&site=stackoverflow";
        int pre_num = 0;
        int number;
        int number2 = 0;
        List<user_num> list = new ArrayList<>();
        while (date < end) {
            Document doc;
            while (true) {
                try {
                    doc = Jsoup.connect(base1 + date + base2).ignoreContentType(true).get();
                    break;
                }catch (HttpStatusException e){
                    System.out.println("Failure with " + e.getStatusCode());
                }
            }
            JsonObject jo = JsonParser.parseString(doc.text()).getAsJsonObject();
            JsonArray ja = jo.get("items").getAsJsonArray();
            number = ja.get(0).getAsJsonObject().get("user_id").getAsInt();
            if (pre_num>number){
                list.get(list.size()-1).setNumber(number2);
            }
            number2 = ja.get(1).getAsJsonObject().get("user_id").getAsInt();
            temp = new user_num();
            temp.setId(++id);
            temp.setNumber(number);
            list.add(temp);
            pre_num = number;
            date += 2597000;
        }
        user_num_repo.saveAll(list);
        return "success";
    }
}
