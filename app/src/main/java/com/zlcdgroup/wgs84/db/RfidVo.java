package com.zlcdgroup.wgs84.db;

import android.support.annotation.NonNull;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.io.Serializable;

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
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/11/7 15:12
 * @ Version V1.0
 */

@Table(name = "tb_rfid")
public class RfidVo extends Model implements Serializable {

  @Column
  public   String   rfid;

  @Column
  public   String   block0;

  @Column
  public   String   number;

  @Column
  public   String   type;

  @Column
  public   String   dn;

  @Column
  public   String   time;

  @Column
  public   double    lat;

  @Column
  public   double    lng;

  @Column
  public   double    bdlat;

  @Column
  public   double    bdlng;
}
