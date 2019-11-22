package com.zlcdgroup.wgs84;

import android.app.Application;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.zlcdgroup.wgs84.db.RfidVo;

/**
 * * *                #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无BUG              #
 * #
 *
 * @ Description:                                          #
 * @ Author king
 * @ Date 2016/11/7 15:56
 * @ Version V1.0
 */

public class Wgs84App  extends Application {
  @Override public void onCreate() {
    super.onCreate();
    Configuration  cfg = new Configuration.Builder(this).addModelClass(RfidVo.class)
                         .create();
    ActiveAndroid.initialize(cfg,true);
  }

  @Override public void onTerminate() {
    super.onTerminate();
    ActiveAndroid.dispose();
  }
}
